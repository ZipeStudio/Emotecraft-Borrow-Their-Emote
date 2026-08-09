package me.zipestudio.ebte.yacl;

import lombok.experimental.ExtensionMethod;

import net.lopymine.mossylib.yacl.api.*;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;

import net.minecraft.client.gui.screens.Screen;

import me.zipestudio.ebte.EBTE;
import me.zipestudio.ebte.config.LeafyConfig;

@ExtensionMethod(SimpleOptionExtension.class)
public final class YACLConfigurationScreen {

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		LeafyConfig defConfig = LeafyConfig.getNewInstance();
		LeafyConfig config = LeafyConfig.getInstance();

		return SimpleYACLScreen.startBuilder(EBTE.MOD_ID, parent, config::saveAsync)
				.categories(SimpleCategory.startBuilder("general").groups(getMainGroup(defConfig, config)))
				.build();
	}

	private static SimpleGroup getMainGroup(LeafyConfig defConfig, LeafyConfig config) {
		return SimpleGroup.startBuilder("main").options(
				SimpleOption.<Boolean>startBuilder("modEnabled")
						.withBinding(defConfig.isModEnabled(), config::isModEnabled, config::setModEnabled, true)
						.withController(),
				SimpleOption.<Boolean>startBuilder("empty_hand_only")
						.withBinding(defConfig.isEmptyHandOnly(), config::isEmptyHandOnly, config::setEmptyHandOnly, true)
						.withController(),
				SimpleOption.<Boolean>startBuilder("sync_emote")
						.withBinding(defConfig.isSyncEmote(), config::isSyncEmote, config::setSyncEmote, true)
						.withController(),
				SimpleOption.<Boolean>startBuilder("borrow_sound")
						.withBinding(defConfig.isBorrowSound(), config::isBorrowSound, config::setBorrowSound, true)
						.withController(),
				SimpleOption.<Boolean>startBuilder("show_actionbar_message")
						.withBinding(defConfig.isShowNotification(), config::isShowNotification, config::setShowNotification, true)
						.withController(),
				SimpleOption.<Integer>startBuilder("borrow_duration")
						.withBinding(defConfig.getBorrowDuration(), config::getBorrowDuration, config::setBorrowDuration, true)
						.withController(1, 30, 1, true, v -> EBTE.text("modmenu.option.borrow_duration.format", v))
		);
	}
}
