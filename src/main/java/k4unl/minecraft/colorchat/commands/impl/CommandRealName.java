package k4unl.minecraft.colorchat.commands.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import k4unl.minecraft.colorchat.commands.arguments.NickArgument;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.k4lib.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class CommandRealName implements Command {
	@Override
	public void register(LiteralArgumentBuilder<CommandSource> argumentBuilder) {
		argumentBuilder.then(Commands.argument("nick", NickArgument.nick()).executes(this::getNick));
	}

	private int getNick(CommandContext<CommandSource> context) {
		User target = NickArgument.getUser(context, "nick");
		context.getSource().sendFeedback(new StringTextComponent(target.getNick() + " = " + target.getUserName()), false);
		return 0;
	}

	@Override
	public String getName() {
		return "realname";
	}

	@Override
	public boolean canUse(CommandSource commandSource) {
		return true;
	}
}
