package me.zipestudio.ebte.mixin.keybinding;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.InputConstants;

import me.zipestudio.ebte.borrow.EmoteBorrower;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;

@Mixin(KeyMapping.class)
public class KeybindingMixin {

	private KeybindingMixin() {
		throw new IllegalStateException("Mixin class");
	}

	@Inject(at = @At("HEAD"), method = "click", cancellable = true)
	private static void ebte$cancelClick(InputConstants.Key key, CallbackInfo ci) {
		if (EBTEKeybindingManager.isBorrowTrigger(key, true) && EmoteBorrower.canBorrow()) {
			ci.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "set", cancellable = true)
	private static void ebte$cancelSet(InputConstants.Key key, boolean pressed, CallbackInfo ci) {
		if (EBTEKeybindingManager.isBorrowTrigger(key, pressed) && EmoteBorrower.canBorrow()) {
			ci.cancel();
		}
	}

}
