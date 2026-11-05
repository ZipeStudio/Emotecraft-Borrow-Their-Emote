package me.zipestudio.ebte.utils.modmenu;

import net.minecraft.client.gui.screens.Screen;

import me.zipestudio.ebte.yacl.YACLConfigurationScreen;

public class ModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return YACLConfigurationScreen.createScreen(parent);
	}
}
