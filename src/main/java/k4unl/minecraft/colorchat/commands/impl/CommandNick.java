package k4unl.minecraft.colorchat.commands.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.Command;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandNick implements Command {

	@Override
	public void register(LiteralArgumentBuilder<CommandSource> argumentBuilder) {
		if (!CCConfig.nickChangeOPOnly.get()) {
			argumentBuilder.executes(this::resetOwnNick);
			argumentBuilder.then(Commands.argument("nickToSet", StringArgumentType.word()).executes(this::setOwnNick));
		} else {
			argumentBuilder.then(Commands.argument("target", EntityArgument.player())
					.then(Commands.argument("nickToSet", StringArgumentType.word()).executes(this::setOtherNick))
					.executes(this::resetOtherNick));
		}
	}

	private int setOtherNick(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity targetPlayerEntity = EntityArgument.getPlayer(context, "target");
		User target = Users.getUserByName(targetPlayerEntity.getName().getUnformattedComponentText());
		String nickToSet = StringArgumentType.getString(context, "nickToSet");
		nickToSet = nickToSet.replace("[", "").replace("]", "");
		if (CCConfig.isNickBlackListed(nickToSet)) {
			Log.error(context.getSource().getName() + " tried to set a banned nick (" + nickToSet + ")");
			context.getSource().sendFeedback(new StringTextComponent(TextFormatting.RED + "This nick is banned! You have been reported!"), false);
			return 0;
		}
		if (nickToSet.length() >= CCConfig.minimumNickLength.get() && nickToSet.length() <= CCConfig.maximumNickLength.get()) {
			if (CCConfig.announceNickChanges.get()) {
				Functions.sendChatMessageServerWide(new StringTextComponent(TextFormatting.GOLD + "~" + target.getNick() +
						"(" + target.getUserName() + ") is now called " + nickToSet));
			}
			target.setNick(nickToSet);
			target.updateDisplayName();
			context.getSource().sendFeedback(new StringTextComponent("Nick is set to " + nickToSet), false);
			targetPlayerEntity.sendMessage(new StringTextComponent("Your nick has been set to " + nickToSet + " by ").appendSibling(context.getSource().getDisplayName()));
		} else {
			context.getSource().sendFeedback(new StringTextComponent("The nick should be between " + CCConfig.minimumNickLength.get() + " and " + CCConfig.maximumNickLength.get() + " characters long."), false);
		}
		return 0;
	}

	private int resetOtherNick(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity targetPlayerEntity = EntityArgument.getPlayer(context, "target");
		User target = Users.getUserByName(targetPlayerEntity.getName().getUnformattedComponentText());
		if (CCConfig.announceNickChanges.get()) {
			Functions.sendChatMessageServerWide(new StringTextComponent(TextFormatting.GOLD + target.getNick() + "(" + target.getUserName() + ") is now called " + target.getUserName()));
		}
		target.resetNick();
		target.updateDisplayName();
		targetPlayerEntity.sendMessage(new StringTextComponent("Your nick has been reset by ").appendSibling(context.getSource().getDisplayName()));
		context.getSource().sendFeedback(new StringTextComponent("Nick is reset!"), false);
		return 0;
	}

	private int setOwnNick(CommandContext<CommandSource> context) {
		User target = Users.getUserByName(context.getSource().getEntity().getName().getUnformattedComponentText());
		String nickToSet = StringArgumentType.getString(context, "nickToSet");
		nickToSet = nickToSet.replace("[", "").replace("]", "");
		if (CCConfig.isNickBlackListed(nickToSet)) {
			Log.error(target.getUserName() + " tried to set a banned nick (" + nickToSet + ")");
			context.getSource().sendFeedback(new StringTextComponent(TextFormatting.RED + "This nick is banned! You have been reported!"), false);
			return 0;
		}
		if (nickToSet.length() >= CCConfig.minimumNickLength.get() && nickToSet.length() <= CCConfig.maximumNickLength.get()) {
			if (CCConfig.announceNickChanges.get()) {
				Functions.sendChatMessageServerWide(new StringTextComponent(TextFormatting.GOLD + "~" + target.getNick() +
						"(" + target.getUserName() + ") is now called " + nickToSet));
			}
			target.setNick(nickToSet);
			target.updateDisplayName();
			context.getSource().sendFeedback(new StringTextComponent("Nick is set to " + nickToSet), false);
		} else {
			context.getSource().sendFeedback(new StringTextComponent("Your nick should be between " + CCConfig.minimumNickLength.get() + " and " + CCConfig.maximumNickLength.get() + " characters long."), false);
		}
		return 0;
	}

	private int resetOwnNick(CommandContext<CommandSource> context) {
		User target = Users.getUserByName(context.getSource().getEntity().getName().getUnformattedComponentText());
		if (CCConfig.announceNickChanges.get()) {
			Functions.sendChatMessageServerWide(new StringTextComponent(TextFormatting.GOLD + target.getNick() + "(" + target.getUserName() + ") is now called " + target.getUserName()));
		}
		target.resetNick();
		target.updateDisplayName();
		context.getSource().sendFeedback(new StringTextComponent("Nick is reset!"), false);
		return 0;
	}

	@Override
	public String getName() {
		return "nick";
	}

	@Override
	public boolean canUse(CommandSource commandSource) {
		if (CCConfig.nickChangeOPOnly.get()) {
			if (commandSource.getEntity() instanceof ServerPlayerEntity) {
				return Functions.isPlayerOpped(((ServerPlayerEntity) commandSource.getEntity()).getGameProfile());
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
