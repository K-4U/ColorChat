package k4unl.minecraft.colorchat.commands.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import k4unl.minecraft.colorchat.lib.Colours;
import k4unl.minecraft.colorchat.lib.Mode;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.Command;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ColorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandColor implements Command {

	@Override
	public void register(LiteralArgumentBuilder<CommandSource> argumentBuilder) {
		Mode mode = CCConfig.mode.get();
		if (mode == Mode.RANDOM || mode == Mode.SAVED || mode == Mode.OP) {
			argumentBuilder.then(Commands.argument("colour", ColorArgument.color()).executes(this::setOwnColor))
					.then(Commands.literal("random").executes(this::setRandomColour));
		}
		if (mode == Mode.OP) {
			argumentBuilder.then(Commands.argument("target", EntityArgument.player()).
					then(Commands.argument("colour", ColorArgument.color()).executes(this::setOPColor)));
		}
	}

	private int setOPColor(CommandContext<CommandSource> context) throws CommandSyntaxException {
		boolean isOp = false;
		if (context.getSource().getEntity() instanceof ServerPlayerEntity) {
			isOp = Functions.isPlayerOpped(((ServerPlayerEntity) context.getSource().getEntity()).getGameProfile());
		}
		if (isOp) {
			TextFormatting colour = ColorArgument.getColor(context, "colour");
			ServerPlayerEntity target = EntityArgument.getPlayer(context, "target");

			User userByName = Users.getUserByName(target.getName().getUnformattedComponentText());
			if (CCConfig.isColorBlackListed(colour.getFriendlyName())) {
				userByName.setUserColor(colour);
				context.getSource().sendFeedback(new StringTextComponent("Color for " + target.getDisplayName() + " has been set to " + colour.getFriendlyName()), false);
				if (CCConfig.changeDisplayName.get()) {
					userByName.updateDisplayName();
				}
			} else {
				context.getSource().sendFeedback(new StringTextComponent(Colours.get("red") + "This color has been blacklisted. Try another color!"), false);
			}
		}
		return 0;
	}

	private int setOwnColor(CommandContext<CommandSource> context) {
		TextFormatting colour = ColorArgument.getColor(context, "colour");
		Mode mode = CCConfig.mode.get();
		User sndr = Users.getUserByName(context.getSource().getEntity().getName().getUnformattedComponentText());

		boolean isOp = false;
		if (context.getSource().getEntity() instanceof ServerPlayerEntity) {
			isOp = Functions.isPlayerOpped(((ServerPlayerEntity) context.getSource().getEntity()).getGameProfile());
		}

		if (mode == Mode.RANDOM || mode == Mode.SAVED || (mode == Mode.OP && isOp)) {
			if (CCConfig.isColorBlackListed(colour.getFriendlyName())) {
				context.getSource().sendFeedback(new StringTextComponent(Colours.get("red") + "This color has been blacklisted. Try another color!"), false);
			} else {
				sndr.setUserColor(colour);
				context.getSource().sendFeedback(new StringTextComponent("Your color has now been set to " + sndr.getColor() + sndr.getColor().getFriendlyName()), false);
				if (CCConfig.changeDisplayName.get()) {
					sndr.updateDisplayName();
				}
			}
		}
		return 0;
	}

	private int setRandomColour(CommandContext<CommandSource> context) {
		Mode mode = CCConfig.mode.get();
		User sndr = Users.getUserByName(context.getSource().getEntity().getName().getUnformattedComponentText());

		boolean isOp = false;
		if (context.getSource().getEntity() instanceof ServerPlayerEntity) {
			isOp = Functions.isPlayerOpped(((ServerPlayerEntity) context.getSource().getEntity()).getGameProfile());
		}

		if (mode == Mode.RANDOM || mode == Mode.SAVED || (mode == Mode.OP && isOp)) {
			sndr.setUserColor(Colours.getRandomColour());
			context.getSource().sendFeedback(new StringTextComponent("Your color has now been set to " + sndr.getColor() + sndr.getColor().getFriendlyName()), false);
			if (CCConfig.changeDisplayName.get()) {
				sndr.updateDisplayName();
			}
		}
		return 0;
	}

	@Override
	public String getName() {

		return "color";
	}

	@Override
	public boolean canUse(CommandSource commandSource) {

		Mode mode = CCConfig.mode.get();
		if (commandSource.getEntity() == null) {

			return (mode == Mode.RANDOM || mode == Mode.SAVED);
		}

		boolean isOp = false;
		if (commandSource.getEntity() instanceof ServerPlayerEntity) {
			isOp = Functions.isPlayerOpped(((ServerPlayerEntity) commandSource.getEntity()).getGameProfile());
		}

		return (mode == Mode.RANDOM || mode == Mode.SAVED || (mode == Mode.OP && isOp));
	}
}
