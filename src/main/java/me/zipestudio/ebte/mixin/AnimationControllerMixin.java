package me.zipestudio.ebte.mixin;

//? if >=1.21.11 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.zigythebird.playeranimcore.animation.AnimationController;
import com.zigythebird.playeranimcore.animation.AnimationData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AnimationController.class, remap = false)
public class AnimationControllerMixin {

	@WrapMethod(method = "process(Lcom/zigythebird/playeranimcore/animation/AnimationData;)V", require = 0)
	private void ebte$guardEmoteProcess(AnimationData state, Operation<Void> original) {
		try {
			original.call(state);
		} catch (NullPointerException ignored) {
		}
	}

}
//?}