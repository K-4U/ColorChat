package k4unl.minecraft.colorchat.commands.impl;

import org.apache.commons.lang3.StringUtils;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import k4unl.minecraft.colorchat.commands.arguments.GroupArgument;
import k4unl.minecraft.colorchat.lib.Colours;
import k4unl.minecraft.colorchat.lib.Group;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.impl.CommandOpOnly;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ColorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandGroup extends CommandOpOnly {

	@Override
	public void register(LiteralArgumentBuilder<CommandSource> argumentBuilder) {
		argumentBuilder.then(Commands.literal("list").executes(this::listGroups));
		argumentBuilder.then(Commands.literal("create").then(Commands.argument("group name", StringArgumentType.word()).executes(this::createGroup)));
		argumentBuilder.then(Commands.literal("remove").then(Commands.argument("group", GroupArgument.group()).executes(this::removeGroup)));
		argumentBuilder.then(Commands.literal("addUser").then(Commands.argument("group", GroupArgument.group()).then(Commands.argument("user", EntityArgument.player()).executes(this::addToGroup))));
		argumentBuilder.then(Commands.literal("delUser").then(Commands.argument("group", GroupArgument.group()).then(Commands.argument("user", EntityArgument.player()).executes(this::removeFromGroup))));
		argumentBuilder.then(Commands.literal("color")
				.then(Commands.argument("group", GroupArgument.group())
						.then(Commands.argument("color", ColorArgument.color()).executes(this::setGroupColor))
						.then(Commands.literal("random").executes(this::setRandomColor))));
	}

	private int setRandomColor(CommandContext<CommandSource> context) {
		Group g = GroupArgument.getGroup(context, "group");
		g.setColor(Colours.getRandomColour());
		g.updateUsers();
		context.getSource().sendFeedback(new StringTextComponent("The color of group " + g.getName() + " has now been set to " + g.getColor() + g.getColor().getFriendlyName()), false);
		return 0;
	}

	private int setGroupColor(CommandContext<CommandSource> context) {
		Group g = GroupArgument.getGroup(context, "group");
		TextFormatting color = ColorArgument.getColor(context, "color");
		if (CCConfig.isColorBlackListed(color.getFriendlyName())) {
			context.getSource().sendFeedback(new StringTextComponent(Colours.get("red") + "This color has been blacklisted. Try another color!"), false);
		} else {
			g.setColor(color);
			g.updateUsers();
			context.getSource().sendFeedback(new StringTextComponent("The color of group " + g.getName() + " has now been set to " + g.getColor() + g.getColor().getFriendlyName()), false);
		}
		return 0;
	}

	private int removeFromGroup(CommandContext<CommandSource> context) throws CommandSyntaxException {
		Group g = GroupArgument.getGroup(context, "group");
		User target = Users.getUserByName(EntityArgument.getPlayer(context, "user").getName().getUnformattedComponentText());
		if (target.getGroup() != null && target.getGroup().equals(g)) {
			target.setGroup(null);
			context.getSource().sendFeedback(new StringTextComponent(target.getColor() + target.getUserName() + TextFormatting.RESET + " is removed from " + g.getColor() + g.getName()), false);
			target.updateDisplayName();
		} else {
			context.getSource().sendFeedback(new StringTextComponent(target.getColor() + target.getUserName() + TextFormatting.RESET + " is not in this group"), false);
		}
		return 0;
	}

	private int addToGroup(CommandContext<CommandSource> context) throws CommandSyntaxException {
		Group g = GroupArgument.getGroup(context, "group");
		User target = Users.getUserByName(EntityArgument.getPlayer(context, "user").getName().getUnformattedComponentText());
		if (target.getGroup() != null && target.getGroup().equals(g)) {
			context.getSource().sendFeedback(new StringTextComponent(target.getColor() + target.getUserName() + TextFormatting.RESET + " is already in this group."), false);
		} else {
			target.setGroup(g);
			target.updateDisplayName();
			context.getSource().sendFeedback(new StringTextComponent("Added " + target.getColor() + target.getUserName() + TextFormatting.RESET + " to " + g.getColor() + g.getName()), false);
		}
		return 0;
	}

	private int removeGroup(CommandContext<CommandSource> context) {
		Group group = GroupArgument.getGroup(context, "group");
		Groups.removeGroupByName(group.getName());
		context.getSource().sendFeedback(new StringTextComponent("Group " + group.getColor() + group.getName() + "" + TextFormatting.RESET + " has been removed"), false);
		return 0;
	}

	private int createGroup(CommandContext<CommandSource> context) {
		String groupName = StringArgumentType.getString(context, "group name");
		if (Groups.getGroupByName(groupName) == null) {
			Group g = Groups.createNewGroup(groupName);
			context.getSource().sendFeedback(new StringTextComponent("Group " + g.getColor() + g.getName() + "" + TextFormatting.RESET + " has been created"), false);
		} else {
			context.getSource().sendFeedback(new StringTextComponent("This group already exists"), false);
		}
		return 0;
	}

	private int listGroups(CommandContext<CommandSource> context) {
		String groupNames = StringUtils.join(Groups.getGroupNames(), ",");
		if (groupNames.isEmpty()) {
			context.getSource().sendFeedback(new StringTextComponent("No groups defined yet!"), false);
		} else {
			context.getSource().sendFeedback(new StringTextComponent(groupNames), false);
		}
		return 0;
	}

	@Override
	public String getName() {

		return "group";
	}
}
