package me.zipestudio.ebte.modmenu;

import net.lopymine.mossylib.modmenu.AbstractModMenuIntegration;
import net.minecraft.client.gui.screens.Screen;

import me.zipestudio.ebte.EBTE;
import me.zipestudio.ebte.yacl.YACLConfigurationScreen;

public class ModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return EBTE.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen parent) {
		return YACLConfigurationScreen.createScreen(parent);
	}
}
