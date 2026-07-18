package me.zipestudio.ebte.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;

import me.zipestudio.ebte.client.EBTEClient;

public class ClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EBTEClient.onInitializeClient();
	}
}

//?} elif neoforge {
/*import me.zipestudio.ebte.EBTE;

import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.modmenu.ModMenuIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = EBTE.MOD_ID, dist = Dist.CLIENT)
public class ClientEntrypoint {

	public ClientEntrypoint(ModContainer container) {
		EBTEClient.onInitializeClient();
		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(container);
	}

}
*///?}
