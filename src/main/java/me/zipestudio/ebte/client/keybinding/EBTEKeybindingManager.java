package me.zipestudio.ebte.client.keybinding;

import lombok.Getter;
import net.minecraft.client.KeyMapping;
//? if <26.3 {
/*import org.lwjgl.glfw.GLFW;
*///?}
import com.mojang.blaze3d.platform.InputConstants;
import me.zipestudio.ebte.EBTE;
import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.config.LeafyConfig;

import java.util.List;

public class EBTEKeybindingManager {

	//? if >=1.21.9 {
	public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(EBTE.id("keybinding"));
	//?} else {
	/*public static final String CATEGORY = "key.category." + EBTE.MOD_ID + ".keybinding";
	*///?}

	public static final KeybindingCombination DEFAULT_BORROW_COMBINATION = new KeybindingCombination(
			//? if >=26.3 {
			EBTEKeybinding.keysym(InputConstants.KEY_LSHIFT),
			EBTEKeybinding.mouse(InputConstants.MOUSE_BUTTON_RIGHT)
			//?} else {
			/*EBTEKeybinding.keysym(GLFW.GLFW_KEY_LEFT_SHIFT),
			EBTEKeybinding.mouse(GLFW.GLFW_MOUSE_BUTTON_2)
			*///?}
	);

	@Getter
	private static EBTEKeybinding borrowKeybinding;
	@Getter
	private static KeyMapping reverseSoundKeybinding;

	private EBTEKeybindingManager() {
		throw new IllegalStateException("Manager class");
	}

	public static void register() {
		if (borrowKeybinding != null) {
			EBTEClient.LOGGER.error("EBTEKeybindingManager.register cannot be called twice!");
			return;
		}

		LeafyConfig config = LeafyConfig.getInstance();

		borrowKeybinding = new EBTEKeybinding(
				EBTE.MOD_ID + ".keybinding.borrow",
				DEFAULT_BORROW_COMBINATION,
				config.getBorrowCombination(),
				LeafyConfig::setBorrowCombination
		);
		reverseSoundKeybinding = new KeyMapping(
				EBTE.MOD_ID + ".keybinding.reverse_sound",
				//? if >=26.3 {
				InputConstants.Type.KEYBOARD,
				InputConstants.KEY_LCONTROL,
				//?} else {
				/*InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_LEFT_CONTROL,
				*///?}
				CATEGORY
		);
	}

	public static boolean isBorrowTrigger(InputConstants.Key key, boolean pressed) {
		return borrowKeybinding != null && borrowKeybinding.matchesTrigger(key, pressed);
	}

	public static List<KeyMapping> getKeyMappings() {
		return List.of(borrowKeybinding, reverseSoundKeybinding);
	}

}
