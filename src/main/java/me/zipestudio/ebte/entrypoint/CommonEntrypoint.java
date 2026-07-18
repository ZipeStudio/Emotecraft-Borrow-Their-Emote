package me.zipestudio.ebte.entrypoint;

//? if fabric {

import me.zipestudio.ebte.EBTE;

import net.fabricmc.api.ModInitializer;

public class CommonEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		EBTE.onInitialize();
	}
}

//?} elif neoforge {
/*import me.zipestudio.ebte.EBTE;

import net.neoforged.fml.common.Mod;

@Mod(EBTE.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		EBTE.onInitialize();
	}

}
*///?} elif forge {
/*import me.zipestudio.ebte.EBTE;

import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.modmenu.ModMenuIntegration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(EBTE.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		EBTE.onInitialize();
		if (FMLEnvironment.dist == Dist.CLIENT) {
			EBTEClient.onInitializeClient();
			ModMenuIntegration integration = new ModMenuIntegration();
			integration.register(ModLoadingContext.get().getActiveContainer());
		}
	}

}
*///?}

