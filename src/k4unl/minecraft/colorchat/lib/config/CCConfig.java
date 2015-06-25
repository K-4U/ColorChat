package k4unl.minecraft.colorchat.lib.config;

import k4unl.minecraft.k4lib.lib.config.Config;
import k4unl.minecraft.k4lib.lib.config.ConfigOption;
import net.minecraftforge.common.config.Configuration;

public class CCConfig extends Config {

    public static final CCConfig INSTANCE = new CCConfig();

    private String[] blacklistedColors;
    private String[] blacklistedNicks;
    private String[] blacklistedSymbols;

    @Override
    public void init() {

        configOptions.add(new ConfigOption("minimumNickLength", 4));
        configOptions.add(new ConfigOption("announceNickChanges", true));
        configOptions.add(new ConfigOption("nickChangeOPOnly", false));
        configOptions.add(new ConfigOption("leadingSymbolOnNick", '~'));
        configOptions.add(new ConfigOption("changeDisplayName", true).setComment("Disable this if you want to use a mod that has bad coding and "
          + "depends on displayName"));
	}

    @Override
	public void loadConfigOptions(Configuration c){
        super.loadConfigOptions(c);
        blacklistedColors = c.get(c.CATEGORY_GENERAL, "blacklistColors", new String[] {"darkblue", "black"}).getStringList();
        blacklistedNicks = c.get(c.CATEGORY_GENERAL, "blacklistNicks", new String[] {"K4Unl", "Direwolf20", "Quetzi", "Krystal_Raven"}).getStringList();
	}

    public boolean isColorBlackListed(String color){
        for(String blackListed : blacklistedColors){
            if(blackListed.equals(color)){
                return true;
            }
        }
        return false;
    }

    public boolean isNickBlackListed(String nick){
        for(String blackListed : blacklistedNicks){
            if(blackListed.equalsIgnoreCase(nick)){
                return true;
            }
        }
        return false;
    }


}

