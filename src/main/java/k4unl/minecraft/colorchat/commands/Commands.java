package k4unl.minecraft.colorchat.commands;


import com.mojang.brigadier.CommandDispatcher;

import k4unl.minecraft.colorchat.commands.impl.CommandColor;
import k4unl.minecraft.colorchat.commands.impl.CommandColorChat;
import k4unl.minecraft.colorchat.commands.impl.CommandNick;
import k4unl.minecraft.k4lib.commands.CommandsRegistry;
import net.minecraft.command.CommandSource;

public class Commands extends CommandsRegistry {

	public Commands(boolean isDedicatedServer, CommandDispatcher<CommandSource> dispatcher) {
		register(dispatcher, new CommandColorChat());
		register(dispatcher, new CommandColor());
		register(dispatcher, new CommandNick());
	}
/*
	public static void init(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandNick());
		event.registerServerCommand(new CommandColor());
		event.registerServerCommand(new CommandRealName());
		event.registerServerCommand(new CommandGroup());
		event.registerServerCommand(new CommandCoords());
        event.registerServerCommand(new CommandColorChat());
	}*/
}
