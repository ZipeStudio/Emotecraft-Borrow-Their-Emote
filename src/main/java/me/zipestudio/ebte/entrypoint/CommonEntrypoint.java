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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(EBTE.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		EBTE.onInitialize();
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientEntrypoint::onInitializeClient);
	}

}

*///?}
