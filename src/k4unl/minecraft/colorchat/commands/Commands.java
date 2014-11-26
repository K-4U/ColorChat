package k4unl.minecraft.colorchat.commands;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class Commands {

	public static void init(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandNick());
		event.registerServerCommand(new CommandColor());
		event.registerServerCommand(new CommandRealName());
		event.registerServerCommand(new CommandGroup());
		event.registerServerCommand(new CommandCoords());
	}
}
