package me.zipestudio.ebte.borrow;

//? if >=1.21.11 {
import com.zigythebird.playeranimcore.animation.Animation;
import com.zigythebird.playeranimcore.animation.keyframe.event.data.SoundKeyframeData;
//?} else {
/*import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
*///?}
import io.github.kosmx.emotes.api.events.client.ClientEmoteAPI;
//? if >=1.21.4 {
import io.github.kosmx.emotes.main.emotePlay.EmotePlayer;
import io.github.kosmx.emotes.main.mixinFunctions.IPlayerEntity;
//?} else {
/*import io.github.kosmx.emotes.executor.emotePlayer.IEmotePlayerEntity;
import io.github.kosmx.emotes.executor.emotePlayer.IEmotePlayer;
*///?}

import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;
import me.zipestudio.ebte.client.keybinding.EBTEKeybinding;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import me.zipestudio.ebte.config.LeafyConfig;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

//? if fabric {
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//?} elif neoforge {
/*import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
*///?} elif forge {
/*import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
*///?}

public final class EmoteBorrower {

    private static UUID PENDING_TARGET_UUID;
    private static long PENDING_DEADLINE_MILLIS;
    //? if >=1.21.11 {
    private static Animation PENDING_ANIMATION;
    private static float PENDING_TICK;
    //?} else {
    /*private static KeyframeAnimation PENDING_ANIMATION;
    private static int PENDING_TICK;
    *///?}

    private static boolean BORROW_WAS_DOWN;

    public static void register() {
        //? if fabric {
        ClientTickEvents.END_CLIENT_TICK.register(EmoteBorrower::clientTick);
        //?} elif neoforge {
		/*NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, (event) -> clientTick(Minecraft.getInstance()));
		*///?} elif forge {
		/*MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
			if (event.phase == TickEvent.Phase.END) {
				clientTick(Minecraft.getInstance());
			}
		});
		*///?}
    }

    private static void clientTick(Minecraft client) {
        handleBorrowRequest(client);
        tickPending(client);
    }

    private static void handleBorrowRequest(Minecraft client) {
        EBTEKeybinding borrowKeybinding = EBTEKeybindingManager.getBorrowKeybinding();
        if (borrowKeybinding == null) {
            return;
        }

        boolean down = borrowKeybinding.isDown() && EBTEClient.getCurrentScreen(client) == null;
        if (down && !BORROW_WAS_DOWN) {
            Player target = getBorrowTarget();
            if (target != null && client.player != null) {
                tryQueue(client.player, target);
            }
        }
        BORROW_WAS_DOWN = down;
    }

    public static boolean canBorrow() {
        return getBorrowTarget() != null;
    }

    @Nullable
    private static Player getBorrowTarget() {

        Minecraft client = Minecraft.getInstance();
        Player self = client.player;
        if (self == null || client.level == null || self.isDeadOrDying()) {
            return null;
        }
        if (!(client.hitResult instanceof EntityHitResult hitResult)) {
            return null;
        }
        if (!(hitResult.getEntity() instanceof Player target) || target == self) {
            return null;
        }

        return target;
    }

    private static boolean tryQueue(Player self, Player target) {

        //? if >=1.21.4 {
        if (!(target instanceof IPlayerEntity holder) || !holder.isPlayingEmote()) {
        //?} else {
        /*if (!(target instanceof IEmotePlayerEntity holder) || !holder.isPlayingEmote()) {
        *///?}
            message("borrow.no_emote", target.getName());
            return false;
        }

        boolean withSound = LeafyConfig.getInstance().isBorrowSound() ^ isReverseSoundKeyDown();

        //? if >=1.21.4 {
        EmotePlayer emotePlayer = holder.emotecraft$getEmote();
        //?} elif >=1.21.1 {
        /*IEmotePlayer emotePlayer = holder.emotecraft$getEmote();
        *///?} else {
        /*IEmotePlayer emotePlayer = (IEmotePlayer) holder.getEmote();
        *///?}

        //? if >=1.21.11 {
        Animation animation = emotePlayer.getCurrentAnimationInstance();
        if (animation == null) {
            message("borrow.no_emote", target.getName());
            return false;
        }
        if (!withSound) {
            animation = muteAnimation(animation);
        }

        float tick = 0.0F;
        Animation.LoopType loopType = animation.loopType();
        boolean looping = loopType.shouldPlayAgain(null, animation) && loopType != Animation.LoopType.HOLD_ON_LAST_FRAME;
        if (looping && LeafyConfig.getInstance().isSyncEmote()) {
            tick = emotePlayer.getAnimationTicks();
            float length = animation.length();
            if (length > 0.0F) {
                tick %= length;
            }
            if (tick < 0.0F) {
                tick = 0.0F;
            }
        }
        //?} elif >=1.21.1 {
        /*KeyframeAnimation animation = emotePlayer.getData();
        if (animation == null) {
            message("borrow.no_emote", target.getName());
            return false;
        }
        if (!withSound) {
            animation = muteAnimation(animation);
        }

        int tick = 0;
        boolean looping = animation.isInfinite;
        if (looping && LeafyConfig.getInstance().isSyncEmote()) {
            tick = emotePlayer.getTick();
            int length = animation.endTick;
            if (length > 0) {
                tick %= length;
            }
            if (tick < 0) {
                tick = 0;
            }
        }
        *///?} else {
        /*KeyframeAnimation animation = emotePlayer.getData();
        if (animation == null) {
            message("borrow.no_emote", target.getName());
            return false;
        }
        if (!withSound) {
            animation = muteAnimation(animation);
        }

        int tick = 0;
        *///?}

        PENDING_ANIMATION = animation;
        PENDING_TICK = tick;
        PENDING_TARGET_UUID = target.getUUID();
        PENDING_DEADLINE_MILLIS = System.currentTimeMillis() + LeafyConfig.getInstance().getBorrowDuration() * 1000L;
        message("borrow.success", target.getName());
        return true;
    }

    private static void tickPending(Minecraft client) {
        if (PENDING_TARGET_UUID == null) {
            return;
        }
        Player self = client.player;
        if (self == null || client.level == null) {
            clearPending();
            return;
        }
        if (System.currentTimeMillis() - PENDING_DEADLINE_MILLIS >= 0L) {
            clearPending();
            return;
        }
        if (self.getPose() == Pose.CROUCHING) {
            return;
        }

        //? if >=1.21.1 {
        ClientEmoteAPI.playEmote(PENDING_ANIMATION, PENDING_TICK);
        //?} else {
        /*ClientEmoteAPI.playEmote(PENDING_ANIMATION);
        *///?}
        clearPending();
    }

    private static void clearPending() {
        PENDING_TARGET_UUID = null;
        PENDING_ANIMATION = null;
    }

    private static boolean isReverseSoundKeyDown() {
        KeyMapping keybinding = EBTEKeybindingManager.getReverseSoundKeybinding();
        return keybinding != null && keybinding.isDown();
    }

    //? if >=1.21.11 {
    private static Animation muteAnimation(Animation animation) {
        com.zigythebird.playeranimcore.animation.ExtraAnimationData data = animation.data();
        Animation.Keyframes keyFrames = animation.keyFrames();
        if (!data.has("song") && keyFrames.sounds().length == 0) {
            return animation;
        }
        java.util.Map<String, Object> strippedData = new java.util.HashMap<>(data.data());
        strippedData.remove("song");
        com.zigythebird.playeranimcore.animation.ExtraAnimationData silentData =
                new com.zigythebird.playeranimcore.animation.ExtraAnimationData(strippedData);
        Animation.Keyframes silentKeyFrames = new Animation.Keyframes(
                new SoundKeyframeData[0], keyFrames.particles(), keyFrames.customInstructions());
        return new Animation(silentData, animation.length(), animation.loopType(),
                animation.boneAnimations(), silentKeyFrames, animation.bones(), animation.parents());
    }
    //?} else {
    /*private static KeyframeAnimation muteAnimation(KeyframeAnimation animation) {
        if (!animation.extraData.containsKey("song")) {
            return animation;
        }
        KeyframeAnimation.AnimationBuilder builder = animation.mutableCopy();
        builder.song = null;
        builder.extraData.remove("song");
        return builder.build();
    }
    *///?}

    private static void message(String key, Object... args) {
        if (LeafyConfig.getInstance().isShowNotification()) {
            EBTEClient.sendTittleMessage(key, args);
        }
    }
}
