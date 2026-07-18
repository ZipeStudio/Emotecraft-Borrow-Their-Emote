package me.zipestudio.ebte.mixin.keybinding;

import net.minecraft.client.KeyMapping;
//? if >=1.21 {
import net.minecraft.client.gui.screens.options.controls.*;
//?} else {
/*import net.minecraft.client.gui.screens.controls.*;
*///?}
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.platform.InputConstants;

import me.zipestudio.ebte.client.keybinding.EBTEKeybinding;
import me.zipestudio.ebte.utils.mixin.EBTEScreenKeybinding;

import org.jetbrains.annotations.Nullable;

@Mixin(KeyBindsScreen.class)
public class KeyBindsScreenScreenMixin implements EBTEScreenKeybinding {

	@Shadow
	@Nullable
	public KeyMapping selectedKey;

	@Shadow
	private KeyBindsList keyBindsList;

	@Inject(at = @At("HEAD"), method = "keyPressed", cancellable = true)
	private void ebte$onKeyPressed(
			//? if >=1.21.9 {
			net.minecraft.client.input.KeyEvent event, CallbackInfoReturnable<Boolean> cir
			//?} else {
			/*int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir
			*///?}
	) {
		if (this.selectedKey instanceof EBTEKeybinding keybinding) {
			this.ebte$applyBindingKey(keybinding,InputConstants.getKey(
					//? if >=1.21.9 {
					event
					//?} else {
					/*keyCode, scanCode
					*///?}
			));
			cir.setReturnValue(false);
		}
	}

	@Inject(at = @At("HEAD"), method = "mouseClicked", cancellable = true)
	private void ebte$onMouseClicked(
			//? if >=1.21.9 {
			net.minecraft.client.input.MouseButtonEvent event, boolean doubled, CallbackInfoReturnable<Boolean> cir
			//?} else {
			/*double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir
			*///?}
	) {
		if (this.selectedKey instanceof EBTEKeybinding keybinding) {
			this.ebte$applyBindingKey(keybinding,InputConstants.Type.MOUSE.getOrCreate(
					//? if >=1.21.9 {
					event.button()
					//?} else {
					/*button
					*///?}
			));
			cir.setReturnValue(false);
		}
	}

	@Unique
	private void ebte$applyBindingKey(EBTEKeybinding keybinding, InputConstants.Key key) {
		if (keybinding.addBindingKey(key)) {
			keybinding.sendBindingKeys();
			this.selectedKey = null;
		}
		this.keyBindsList.refreshEntries();
	}

	@Override
	public void ebte$onKeyReleased() {
		if (this.selectedKey instanceof EBTEKeybinding keybinding && keybinding.isBinding()) {
			keybinding.sendBindingKeys();
			this.selectedKey = null;
			this.keyBindsList.refreshEntries();
		}
	}

}