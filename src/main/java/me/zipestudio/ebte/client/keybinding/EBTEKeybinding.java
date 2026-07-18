package me.zipestudio.ebte.client.keybinding;

import lombok.Getter;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import me.zipestudio.ebte.config.LeafyConfig;
import java.util.List;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public class EBTEKeybinding extends KeyMapping {

	private final KeybindingCombination defaultCombination;
	private final KeybindingCombination combination = new KeybindingCombination();
	private final BiConsumer<LeafyConfig, KeybindingCombination> saver;

	@Getter
	private boolean binding;
	@Getter
	private boolean canStartBinding = true;

	public EBTEKeybinding(String translationKey, KeybindingCombination defaultCombination, KeybindingCombination combination, BiConsumer<LeafyConfig, KeybindingCombination> saver) {
		super(translationKey, -1, EBTEKeybindingManager.CATEGORY);
		this.defaultCombination = defaultCombination;
		this.saver              = saver;
		this.combination.setAttributeKey(combination.getAttributeKey());
		this.combination.setKey(combination.getKey());
	}

	public static Key keysym(int code) {
		return Type.KEYSYM.getOrCreate(code);
	}

	public static Key mouse(int button) {
		return Type.MOUSE.getOrCreate(button);
	}

	private void startBinding() {
		this.combination.setAttributeKey(null);
		this.combination.setKey(null);
		this.binding         = true;
		this.canStartBinding = false;
	}

	public boolean addBindingKey(Key key) {
		if (this.isCanStartBinding()) {
			this.startBinding();
		}
		if (!this.isBinding()) {
			return true;
		}
		if (key.getValue() == GLFW.GLFW_KEY_ESCAPE) {
			this.combination.setKey(null);
			this.combination.setAttributeKey(null);
			return true;
		}
		if (KeybindingCombination.isAttributeKey(key.getValue())) {
			if (this.combination.isComplete()) {
				return true;
			}
			this.combination.setAttributeKey(key);
			return this.combination.isComplete();
		}
		this.combination.setKey(key);
		return true;
	}

	public void sendBindingKeys() {
		this.saveCombination();
		this.binding         = false;
		this.canStartBinding = true;
	}

	@Override
	public void setKey(Key boundKey) {
		if (boundKey.equals(this.getDefaultKey())) {
			this.combination.setAttributeKey(this.defaultCombination.getAttributeKey());
			this.combination.setKey(this.defaultCombination.getKey());
			this.saveCombination();
		}
	}

	private void saveCombination() {
		LeafyConfig config = LeafyConfig.getInstance();
		this.saver.accept(config, this.combination.copy());
		config.saveAsync();
	}

	@Override
	public boolean isDown() {
		List<Key> keys = this.combination.getKeys();
		if (keys.isEmpty()) {
			return false;
		}
		for (Key key : keys) {
			if (!isKeyDown(key)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isKeyDown(Key key) {
		if (key.getType() == Type.KEYSYM) {
			//? if <=1.21.8 {
			/*return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key.getValue());
			*///?} else {
			return InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), key.getValue());
			//?}
		}
		//? if <=1.21.8 {
		/*return GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), key.getValue()) == GLFW.GLFW_PRESS;
		*///?} else {
		return GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().handle(), key.getValue()) == GLFW.GLFW_PRESS;
		//?}
	}

	public boolean matchesTrigger(Key key, boolean pressed) {
		return pressed && key.equals(this.combination.getKey()) && this.isDown();
	}

	@Override
	public boolean isDefault() {
		return this.combination.equals(this.defaultCombination);
	}

	@Override
	public @NotNull Component getTranslatedKeyMessage() {
		return this.combination.getLabel(!this.isBinding());
	}
}
