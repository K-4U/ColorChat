package k4unl.minecraft.colorchat.commands;


import com.mojang.brigadier.CommandDispatcher;
import k4unl.minecraft.colorchat.commands.impl.*;
import k4unl.minecraft.k4lib.commands.CommandsRegistry;
import net.minecraft.command.CommandSource;

public class Commands extends CommandsRegistry {

	public Commands(boolean isDedicatedServer, CommandDispatcher<CommandSource> dispatcher) {
		register(dispatcher, new CommandColorChat());
		register(dispatcher, new CommandColor());
		register(dispatcher, new CommandNick());
		register(dispatcher, new CommandGroup());
		register(dispatcher, new CommandRealName());
	}
}
