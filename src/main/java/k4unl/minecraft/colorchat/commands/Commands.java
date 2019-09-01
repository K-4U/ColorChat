package k4unl.minecraft.colorchat.commands;


import com.mojang.brigadier.CommandDispatcher;

import k4unl.minecraft.colorchat.commands.arguments.GroupArgument;
import k4unl.minecraft.colorchat.commands.arguments.NickArgument;
import k4unl.minecraft.colorchat.commands.impl.CommandColor;
import k4unl.minecraft.colorchat.commands.impl.CommandColorChat;
import k4unl.minecraft.colorchat.commands.impl.CommandGroup;
import k4unl.minecraft.colorchat.commands.impl.CommandNick;
import k4unl.minecraft.colorchat.commands.impl.CommandRealName;
import k4unl.minecraft.k4lib.commands.CommandsRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;

public class Commands extends CommandsRegistry {

	public Commands(boolean isDedicatedServer, CommandDispatcher<CommandSource> dispatcher) {
		ArgumentTypes.register("groups", GroupArgument.class, new ArgumentSerializer<>(GroupArgument::group));
		ArgumentTypes.register("nicks", NickArgument.class, new ArgumentSerializer<>(NickArgument::nick));

		register(dispatcher, new CommandColorChat());
		register(dispatcher, new CommandColor());
		register(dispatcher, new CommandNick());
		register(dispatcher, new CommandGroup());
		register(dispatcher, new CommandRealName());
	}
}
