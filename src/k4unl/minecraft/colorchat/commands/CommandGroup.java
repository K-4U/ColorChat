package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.*;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.CommandOpOnly;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.DimensionManager;

public class CommandGroup extends CommandOpOnly {

    public CommandGroup() {

        aliases.add("gr");
    }

    @Override
    public String getName() {

        return "group";
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return "/group list|create <name>|remove <name>|addUser <group> <name>|delUser <group> <name>|color <group> <color>|save|load";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("Usage: " + getUsage(sender)));
        } else {
            if (args[0].equals("create")) {
                if (args.length == 2) {
                    if (Groups.getGroupByName(args[1]) == null) {
                        Group g = Groups.createNewGroup(args[1]);
                        sender.sendMessage(new TextComponentString("Group " + g.getColor() + args[1] + "" + TextFormatting.RESET + " has been created"));
                    } else {
                        sender.sendMessage(new TextComponentString("This group already exists"));
                    }
                } else {
                    sender.sendMessage(new TextComponentString("Usage: /group create <name>"));
                }
            } else if (args[0].equals("remove")) {
                if (args.length == 2) {
                    if (Groups.getGroupByName(args[1]) != null) {
                        Group g = Groups.getGroupByName(args[1]);
                        sender.sendMessage(new TextComponentString("Group " + g.getColor() + args[1] + "" + TextFormatting.RESET + " has been removed"));
                        g.updateUsers();
                        Groups.removeGroupByName(args[1]);
                    } else {
                        sender.sendMessage(new TextComponentString("This group doesn't exists"));
                    }
                } else {
                    sender.sendMessage(new TextComponentString("Usage: /group remove <name>"));
                }
            } else if (args[0].equals("save")) {
                Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.sendMessage(new TextComponentString("Groups saved to file!"));
            } else if (args[0].equals("load")) {
                Groups.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.sendMessage(new TextComponentString("Groups loaded from file!"));
                Groups.updateAll();
            } else if (args[0].equals("addUser")) {
                if (args.length == 3) {
                    if (Groups.getGroupByName(args[1]) == null) {
                        sender.sendMessage(new TextComponentString("This group does not exist"));
                    } else {
                        Group g = Groups.getGroupByName(args[1]);
                        User sndr = Users.getUserByName(args[2]);
                        if (sndr.getGroup() != null && sndr.getGroup().equals(g)) {
                            sender.sendMessage(new TextComponentString(sndr.getColor() + sndr.getUserName() + TextFormatting.RESET + " is already in this group."));
                        } else {
                            sndr.setGroup(g);
                            sndr.updateDisplayName();
                            sender.sendMessage(new TextComponentString("Added " + sndr.getColor() + sndr.getUserName() + TextFormatting.RESET + " to " + g.getColor() + g.getName()));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("Usage: /group addUser <groupName> <userName>"));
                }
            } else if (args[0].equals("delUser")) {
                if (args.length == 3) {
                    if (Groups.getGroupByName(args[1]) == null) {
                        sender.sendMessage(new TextComponentString("This group does not exist"));
                    } else {
                        Group g = Groups.getGroupByName(args[1]);
                        User sndr = Users.getUserByName(args[2]);
                        if (sndr.getGroup() != null && sndr.getGroup().equals(g)) {
                            sndr.setGroup(null);
                            sender.sendMessage(new TextComponentString(sndr.getColor() + sndr.getUserName() + TextFormatting.RESET + " is removed from " + g.getColor() + g.getName()));
                            sndr.updateDisplayName();
                        } else {
                            sender.sendMessage(new TextComponentString(sndr.getColor() + sndr.getUserName() + TextFormatting.RESET + " is not in this group"));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("Usage: /group delUser <groupName> <userName>"));
                }
            } else if (args[0].equals("color")) {
                if (args.length == 3) {
                    if (Groups.getGroupByName(args[1]) == null) {
                        sender.sendMessage(new TextComponentString("This group does not exist"));
                    } else {
                        String clr = args[2].toLowerCase();
                        Group g = Groups.getGroupByName(args[1]);
                        if (clr.equals("help")) {
                            CommandColor.printColors(sender);
                        } else if (clr.equals("random")) {
                            g.setColor(Colours.getRandomColour());
                            g.updateUsers();
                            sender.sendMessage(new TextComponentString("The group color has now been set to " + g.getColor()));

                        } else if (Colours.get(clr) != null) {
                            if (CCConfig.INSTANCE.isColorBlackListed(clr)) {
                                sender.sendMessage(new TextComponentString(Colours.get("red") + "This color has been blacklisted. Try " +
                                        "another color!"));
                            } else {
                                g.setColor(Colours.get(clr));
                                g.updateUsers();
                                sender.sendMessage(new TextComponentString("The group color has now been set to " + Colours.get(clr) + clr));
                            }
                        } else {
                            sender.sendMessage(new TextComponentString("Valid Colours are: " + Colours.getColourList()));
                        }
                    }
                }
            } else if (args[0].equals("list")) {
                sender.sendMessage(new TextComponentString(Groups.getGroupNames()));
            }
        }
    }
}
