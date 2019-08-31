package k4unl.minecraft.colorchat.lib.config;

import k4unl.minecraft.colorchat.lib.Mode;
import k4unl.minecraft.k4lib.lib.config.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class CCConfig extends Config {

	public static ForgeConfigSpec.EnumValue<Mode> mode;
	public static ForgeConfigSpec.ConfigValue<String> playerColour;
	public static ForgeConfigSpec.ConfigValue<String> opColour;
	public static ForgeConfigSpec.ConfigValue<Integer> minimumNickLength;
	public static ForgeConfigSpec.ConfigValue<Integer> maximumNickLength;
	public static ForgeConfigSpec.ConfigValue<Boolean> announceNickChanges;
	public static ForgeConfigSpec.ConfigValue<Boolean> nickChangeOPOnly;
	public static ForgeConfigSpec.ConfigValue<String> leadingSymbolOnNick;
	public static ForgeConfigSpec.BooleanValue changeDisplayName; //For now will never be in a config
	public static ForgeConfigSpec.ConfigValue<String> blacklistedColours;
	public static ForgeConfigSpec.ConfigValue<String> blacklistedNicks;

	public static boolean isColorBlackListed(String color) {

		for (String blackListed : blacklistedColours.get().split(",")) {
			if (blackListed.equals(color)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNickBlackListed(String nick) {

		for (String blackListed : blacklistedNicks.get().split(",")) {
			if (blackListed.equalsIgnoreCase(nick)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void buildCommon(ForgeConfigSpec.Builder builder) {
		mode = builder.comment("Change the mode:\n" +
				"[RANDOM] Colours can be changed by players, random on login\n" +
				"[SAVED ] Colours can be changed by players, saved between logins\n" +
				"[OP    ] Only ops can change colours\n" +
				"[SET   ] Players, Ops have set colours\n" +
				"[GROUPS] Groups determine colours").defineEnum("mode", Mode.RANDOM);
		playerColour = builder.comment("The colour the player will have when mode 4 is selected.").define("playerColour", "cyan");
		opColour = builder.comment("The colour the op will have when mode 4 is selected.").define("opColour", "darkgreen");
		minimumNickLength = builder.comment("The minimum size a nick name has to be.").define("minimumNickLength", 4);
		maximumNickLength = builder.comment("The maximum size a nick name can be.").define("maximumNickLength", 12);
		announceNickChanges = builder.comment("If somebody changes their nick, should we tell the entire server?").define("announceNickChanges", true);
		nickChangeOPOnly = builder.comment("Are ops the only ones allowed to change their nickname?").define("nickChangeOPOnly", false);
		leadingSymbolOnNick = builder.comment("What character should we show before a nickname to indicate that it's a nickname?").define("leadingSymbolOnNick", "~");
		changeDisplayName = builder.comment("Disable this if you want to use a mod that has bad coding and depends on displayName. Note. DOES NOT WORK YET. DO NOT SET TO TRUE!").define("changeDisplayName", false);

		blacklistedColours = builder.comment("What colours aren't we allowed to use here?").define("blacklistColours", String.join(",", new String[]{"darkblue", "black"}));
		blacklistedNicks = builder.comment("What nicknames don't we allow here?").define("blacklistNicks", String.join(",", new String[]{"K4Unl", "Direwolf20", "Quetzi"}));
	}

	@Override
	protected void buildServer(ForgeConfigSpec.Builder builder) {

	}

	@Override
	protected void buildClient(ForgeConfigSpec.Builder builder) {

	}
}


