package k4unl.minecraft.colorchat.lib.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class Config {
	private static class configOption{
		private String key;
		private boolean isBool;
		private boolean val;
		private boolean def;
		private int valInt;
		private int defInt;
		
		public configOption(String _key, boolean _def){
			key = _key;
			val = _def;
			def = _def;
			isBool = true;
		}
		public configOption(String _key, int _def){
			key = _key;
			valInt = _def;
			defInt = _def;
			isBool = false;
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
		
		public void loadFromConfig(Configuration config){
			if(isBool){
				val = config.get(config.CATEGORY_GENERAL, key, def).getBoolean(def);
			}else{
				valInt = config.get(config.CATEGORY_GENERAL, key, defInt).getInt(defInt);
			}
		}
	}
	private static final List<configOption> configOptions = new ArrayList<configOption>();
	
	static {

	}
	
	public static void loadConfigOptions(Configuration c){
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
}
