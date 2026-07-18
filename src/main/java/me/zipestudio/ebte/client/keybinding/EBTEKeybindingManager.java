package me.zipestudio.ebte.client.keybinding;

import lombok.Getter;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import me.zipestudio.ebte.EBTE;
import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.config.LeafyConfig;

//? if fabric && >=26.1 {
/*import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
*///?} elif fabric {
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//?} elif neoforge {
/*import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
*///?} elif forge {
/*import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*///?}

public class EBTEKeybindingManager {

	//? if >=1.21.9 {
	public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(EBTE.id("keybinding"));
	//?} else {
	/*public static final String CATEGORY = "key.category." + EBTE.MOD_ID + ".keybinding";
	*///?}

	public static final KeybindingCombination DEFAULT_BORROW_COMBINATION = new KeybindingCombination(
			EBTEKeybinding.keysym(GLFW.GLFW_KEY_LEFT_SHIFT),
			EBTEKeybinding.mouse(GLFW.GLFW_MOUSE_BUTTON_2)
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
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_LEFT_CONTROL,
				CATEGORY
		);

		registerKeybindings(borrowKeybinding, reverseSoundKeybinding);
	}

	public static boolean isBorrowTrigger(InputConstants.Key key, boolean pressed) {
		return borrowKeybinding != null && borrowKeybinding.matchesTrigger(key, pressed);
	}

	private static void registerKeybindings(KeyMapping... keyMappings) {
		//? if fabric && >=26.1 {
		/*for (KeyMapping keyMapping : keyMappings) {
			KeyMappingHelper.registerKeyMapping(keyMapping);
		}
		*///?} elif fabric {
		for (KeyMapping keyMapping : keyMappings) {
			KeyBindingHelper.registerKeyBinding(keyMapping);
		}
		//?} elif neoforge {
		/*ModLoadingContext.get().getActiveContainer().getEventBus().addListener(RegisterKeyMappingsEvent.class, (event) -> {
			for (KeyMapping keyMapping : keyMappings) {
				event.register(keyMapping);
			}
		});
		*///?} elif forge {
		/*FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterKeyMappingsEvent event) -> {
			for (KeyMapping keyMapping : keyMappings) {
				event.register(keyMapping);
			}
		});
		*///?}
	}

}
