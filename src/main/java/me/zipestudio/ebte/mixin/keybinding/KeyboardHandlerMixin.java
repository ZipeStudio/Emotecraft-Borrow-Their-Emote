package me.zipestudio.ebte.mixin.keybinding;

import me.zipestudio.ebte.client.EBTEClient;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.zipestudio.ebte.utils.mixin.EBTEScreenKeybinding;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

	@Inject(at = @At("HEAD"), method = "keyPress")
	private void ebte$forwardKeyRelease(
			//? if >=1.21.9 {
			long window, int action, net.minecraft.client.input.KeyEvent event, CallbackInfo ci
			//?} else {
			/*long window, int key, int scancode, int action, int modifiers, CallbackInfo ci
			*///?}
	) {
		if (action != GLFW.GLFW_RELEASE) {
			return;
		}
		Screen screen = EBTEClient.getCurrentScreen(Minecraft.getInstance());
		if (screen instanceof EBTEScreenKeybinding keybindingScreen) {
			keybindingScreen.ebte$onKeyReleased();
		}
	}

}
