package me.zipestudio.ebte.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import me.zipestudio.ebte.EBTE;
import me.zipestudio.ebte.borrow.EmoteBorrower;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;

public class EBTEClient {

	public static final Logger LOGGER = LoggerFactory.getLogger(EBTE.MOD_NAME + "/Client");

	public static void onInitializeClient() {
		EBTEKeybindingManager.register();
		EmoteBorrower.register();
		LOGGER.info("{} Client Initialized", EBTE.MOD_NAME);
	}

	public static void sendTittleMessage(String key, Object... args) {
		Minecraft client = Minecraft.getInstance();
		if (client.player == null) return;
		//? if >=26.1 {
		/*client.player.sendOverlayMessage(
			EBTE.text(key, args)
		);
		*///?} else {
		client.player.displayClientMessage(
				EBTE.text(key, args), true
		);
		//?}
	}

	@Nullable
	public static Screen getCurrentScreen(Minecraft client) {
		//? if >=26.2 {
		/*return client.gui.screen();
		 *///?} else {
		return client.screen;
		//?}
	}

}
