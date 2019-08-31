package k4unl.minecraft.colorchat.commands.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.k4lib.commands.impl.CommandK4OpOnly;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class CommandColorChat extends CommandK4OpOnly {
	@Override
	public void register(LiteralArgumentBuilder<CommandSource> argumentBuilder) {
		argumentBuilder.then(Commands.literal("version").executes(this::execute));
	}

	private int execute(CommandContext<CommandSource> commandSourceCommandContext) {
		commandSourceCommandContext.getSource().sendFeedback(new StringTextComponent("Colorchat version " + ModInfo.VERSION), false);
		return 0;
	}

	@Override
	public String getName() {

		return "colorchat";
	}
}
