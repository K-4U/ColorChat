package k4unl.minecraft.colorchat.lib.config;

import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static class configOption{
        private String key;

        private boolean isBool;
        private boolean isInt;
        private boolean isString;
        private boolean isChar;

        private boolean val;
        private boolean def;

        private int valInt;
        private int defInt;

        private String valString;
        private String defString;

        private char valChar;
        private char defChar;

        private String category = Configuration.CATEGORY_GENERAL;

        public configOption(String _key, boolean _def){
            key = _key;
            val = _def;
            def = _def;
            isBool = true;
            isInt = false;
            isString = false;
        }
        public configOption(String _key, int _def){
            key = _key;
            valInt = _def;
            defInt = _def;
            isBool = false;
            isInt = true;
            isString = false;
        }

        public configOption(String _key, String _def){
            key = _key;
            valString = _def;
            defString = _def;
            isBool = false;
            isInt = false;
            isString = true;
        }

        public configOption(String _key, char _def){
            key = _key;
            valChar = _def;
            defChar = _def;
            isBool = false;
            isInt = false;
            isString = false;
            isChar = true;
        }

        public configOption setCategory(String newCat){
            category = newCat;
            return this;
        }

        public String getKey(){
            return key;
        }

        public boolean getBool(){
            return val;
        }

        public int getInt(){
            return valInt;
        }

        public String getString(){
            return valString;
        }

        public char getChar(){
            return valChar;
        }

        public void loadFromConfig(Configuration config){
            if(isBool){
                val = config.get(category, key, def).getBoolean(def);
            }else if(isInt){
                valInt = config.get(category, key, defInt).getInt(defInt);
            }else if(isString){
                valString = config.get(category, key, defString).getString();
            }else if(isChar){
                String t = config.get(category, key, defChar + "").getString();
                if(t.length() > 0){
                    valChar = t.charAt(0);
                }
            }
        }
    }

	private static final List<configOption> configOptions = new ArrayList<configOption>();
	private static String[] blacklistedColors;
    private static String[] blacklistedNicks;
    private static String[] blacklistedSymbols;

	static {
        configOptions.add(new configOption("minimumNickLength", 4));
        configOptions.add(new configOption("announceNickChanges", true));
        configOptions.add(new configOption("nickChangeOPOnly", false));
        configOptions.add(new configOption("leadingSymbolOnNick", '~'));
	}
	
	public static void loadConfigOptions(Configuration c){
        blacklistedColors = c.get(c.CATEGORY_GENERAL, "blacklistColors", new String[] {"darkblue", "black"}).getStringList();
        blacklistedNicks = c.get(c.CATEGORY_GENERAL, "blacklistNicks", new String[] {"K4Unl", "Direwolf20", "Quetzi", "Krystal_Raven"}).getStringList();

		for(configOption config : configOptions){
			config.loadFromConfig(c);
		}
	}
	public static boolean getBool(String key){
		for(configOption config : configOptions){
			if(config.getKey() == key){
				return config.getBool();
			}
		}
		return false;
	}
	
	public static int getInt(String key){
		for(configOption config : configOptions){
			if(config.getKey() == key){
				return config.getInt();
			}
		}
		return 0;
	}

    public static String getString(String key){
        for(configOption config : configOptions){
            if(config.getKey() == key){
                return config.getString();
            }
        }
        return "";
    }

    public static char getChar(String key){
        for(configOption config : configOptions){
            if(config.getKey() == key){
                return config.getChar();
            }
        }
        return 0;
    }

    public static boolean isColorBlackListed(String color){
        for(String blackListed : blacklistedColors){
            if(blackListed.equals(color)){
                return true;
            }
        }
        return false;
    }

    public static boolean isNickBlackListed(String nick){
        for(String blackListed : blacklistedNicks){
            if(blackListed.equalsIgnoreCase(nick)){
                return true;
            }
        }
        return false;
    }


}

