package k4unl.minecraft.colorchat.commands;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class Commands {

	
	public static void init(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandNick());
		event.registerServerCommand(new CommandColor());
	}
}
