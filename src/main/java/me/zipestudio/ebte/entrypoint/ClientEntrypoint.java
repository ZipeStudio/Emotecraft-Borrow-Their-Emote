package me.zipestudio.ebte.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import me.zipestudio.ebte.borrow.EmoteBorrower;
import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;
import net.minecraft.client.KeyMapping;

public class ClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EBTEClient.onInitializeClient();

		EBTEKeybindingManager.getKeyMappings().forEach(ClientEntrypoint::registerKey);
		ClientTickEvents.END_CLIENT_TICK.register(EmoteBorrower::clientTick);
	}

	private static void registerKey(KeyMapping key) {
		//? if >=26.1 {
		net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(key);
		//?} else {
		/*net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(key);
		*///?}
	}

}

//?} elif neoforge {
/*import me.zipestudio.ebte.EBTE;
import me.zipestudio.ebte.borrow.EmoteBorrower;
import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;
import me.zipestudio.ebte.utils.modmenu.ModMenuIntegration;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = EBTE.MOD_ID, dist = Dist.CLIENT)
public class ClientEntrypoint {

	public ClientEntrypoint(IEventBus modBus, ModContainer container) {
		EBTEClient.onInitializeClient();

		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(container);

		modBus.addListener(RegisterKeyMappingsEvent.class, event -> EBTEKeybindingManager.getKeyMappings().forEach(event::register));
		NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class,
				event -> EmoteBorrower.clientTick(Minecraft.getInstance()));
	}

}

*///?} elif forge {

/*import me.zipestudio.ebte.borrow.EmoteBorrower;
import me.zipestudio.ebte.client.EBTEClient;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;
import me.zipestudio.ebte.utils.modmenu.ModMenuIntegration;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientEntrypoint {

	public static void onInitializeClient() {
		EBTEClient.onInitializeClient();

		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(ModLoadingContext.get().getActiveContainer());

		FMLJavaModLoadingContext.get().getModEventBus()
				.addListener((RegisterKeyMappingsEvent event) -> EBTEKeybindingManager.getKeyMappings().forEach(event::register));
		MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> {
			if (event.phase == TickEvent.Phase.END) EmoteBorrower.clientTick(Minecraft.getInstance());
		});
	}

}

*///?}
