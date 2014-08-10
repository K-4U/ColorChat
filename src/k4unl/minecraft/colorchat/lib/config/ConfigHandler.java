package k4unl.minecraft.colorchat.lib.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	private static Configuration config;

	public static void init(File configFile){
		config = new Configuration(configFile);
		
		Config.loadConfigOptions(config);


		
		if(config.hasChanged()){
			config.save();
		}
	}
	
}
