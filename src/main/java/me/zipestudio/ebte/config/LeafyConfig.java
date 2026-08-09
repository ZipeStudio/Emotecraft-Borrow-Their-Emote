package me.zipestudio.ebte.config;

import lombok.*;
import me.zipestudio.ebte.client.keybinding.EBTEKeybindingManager;
import me.zipestudio.ebte.client.keybinding.KeybindingCombination;
import org.slf4j.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.zipestudio.ebte.EBTE;
import net.lopymine.mossylib.loader.MossyLoader;
import net.lopymine.mossylib.utils.CodecUtils;
import net.lopymine.mossylib.utils.ConfigUtils;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import static net.lopymine.mossylib.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class LeafyConfig {

	public static final Codec<LeafyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("mod_enabled", true, Codec.BOOL, LeafyConfig::isModEnabled),
			option("empty_hand_only", true, Codec.BOOL, LeafyConfig::isEmptyHandOnly),
			option("sync_emote", true, Codec.BOOL, LeafyConfig::isSyncEmote),
			option("borrow_sound", false, Codec.BOOL, LeafyConfig::isBorrowSound),
			option("show_notification", true, Codec.BOOL, LeafyConfig::isShowNotification),
			option("borrow_duration", 3, Codec.INT, LeafyConfig::getBorrowDuration),
			option("borrow_combination", EBTEKeybindingManager.DEFAULT_BORROW_COMBINATION, KeybindingCombination.CODEC, LeafyConfig::getBorrowCombination)
	).apply(instance, LeafyConfig::new));

	private static final File CONFIG_FILE = MossyLoader.getConfigDir().resolve(EBTE.MOD_ID + ".json5").toFile();
	private static final Logger LOGGER = LoggerFactory.getLogger(EBTE.MOD_NAME + "/Config");
	private static LeafyConfig INSTANCE;

	private boolean modEnabled;
	private boolean emptyHandOnly;
	private boolean syncEmote;
	private boolean borrowSound;
	private boolean showNotification;
	private int borrowDuration;
	private KeybindingCombination borrowCombination;

	@SuppressWarnings("unused")
	private LeafyConfig() {
		throw new IllegalArgumentException();
	}

	public static LeafyConfig getInstance() {
		return INSTANCE == null ? reload() : INSTANCE;
	}

	public static LeafyConfig reload() {
		return INSTANCE = read();
	}

	public static LeafyConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	private static LeafyConfig read() {
		return ConfigUtils.readConfig(CODEC, CONFIG_FILE, LOGGER);
	}

	public void saveAsync() {
		CompletableFuture.runAsync(this::save);
	}

	public void save() {
		ConfigUtils.saveConfig(this, CODEC, CONFIG_FILE, LOGGER);
	}
}
