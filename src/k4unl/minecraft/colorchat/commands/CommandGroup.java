package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.*;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.CommandOpOnly;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;

public class CommandGroup extends CommandOpOnly {

    public CommandGroup() {

        aliases.add("gr");
    }

    @Override
    public String getCommandName() {

        return "group";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {

        return "/group list|create <name>|remove <name>|addUser <group> <name>|delUser <group> <name>|color <group> <color>|save|load";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] var2) {

        if (var2.length == 0) {
            sender.addChatMessage(new ChatComponentText("Usage: " + getCommandUsage(sender)));
        } else {
            if (var2[0].equals("create")) {
                if (var2.length == 2) {
                    if (Groups.getGroupByName(var2[1]) == null) {
                        Group g = Groups.createNewGroup(var2[1]);
                        sender.addChatMessage(new ChatComponentText("Group " + g.getColor() + var2[1] + "" + EnumChatFormatting.RESET + " has been created"));
                    } else {
                        sender.addChatMessage(new ChatComponentText("This group already exists"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("Usage: /group create <name>"));
                }
            } else if (var2[0].equals("remove")) {
                if (var2.length == 2) {
                    if (Groups.getGroupByName(var2[1]) != null) {
                        Group g = Groups.getGroupByName(var2[1]);
                        sender.addChatMessage(new ChatComponentText("Group " + g.getColor() + var2[1] + "" + EnumChatFormatting.RESET + " has been removed"));
                        g.updateUsers();
                        Groups.removeGroupByName(var2[1]);
                    } else {
                        sender.addChatMessage(new ChatComponentText("This group doesn't exists"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("Usage: /group remove <name>"));
                }
            } else if (var2[0].equals("save")) {
                Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Groups saved to file!"));
            } else if (var2[0].equals("load")) {
                Groups.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Groups loaded from file!"));
                Groups.updateAll();
            } else if (var2[0].equals("addUser")) {
                if (var2.length == 3) {
                    if (Groups.getGroupByName(var2[1]) == null) {
                        sender.addChatMessage(new ChatComponentText("This group does not exist"));
                    } else {
                        Group g = Groups.getGroupByName(var2[1]);
                        User sndr = Users.getUserByName(var2[2]);
                        if (sndr.getGroup() != null && sndr.getGroup().equals(g)) {
                            sender.addChatMessage(new ChatComponentText(sndr.getColor() + sndr.getUserName() + EnumChatFormatting.RESET + " is already in this group."));
                        } else {
                            sndr.setGroup(g);
                            sndr.updateDisplayName();
                            sender.addChatMessage(new ChatComponentText("Added " + sndr.getColor() + sndr.getUserName() + EnumChatFormatting.RESET + " to " + g.getColor() + g.getName()));
                        }
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("Usage: /group addUser <groupName> <userName>"));
                }
            } else if (var2[0].equals("delUser")) {
                if (var2.length == 3) {
                    if (Groups.getGroupByName(var2[1]) == null) {
                        sender.addChatMessage(new ChatComponentText("This group does not exist"));
                    } else {
                        Group g = Groups.getGroupByName(var2[1]);
                        User sndr = Users.getUserByName(var2[2]);
                        if (sndr.getGroup() != null && sndr.getGroup().equals(g)) {
                            sndr.setGroup(null);
                            sender.addChatMessage(new ChatComponentText(sndr.getColor() + sndr.getUserName() + EnumChatFormatting.RESET + " is removed from " + g.getColor() + g.getName()));
                            sndr.updateDisplayName();
                        } else {
                            sender.addChatMessage(new ChatComponentText(sndr.getColor() + sndr.getUserName() + EnumChatFormatting.RESET + " is not in this group"));
                        }
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("Usage: /group delUser <groupName> <userName>"));
                }
            } else if (var2[0].equals("color")) {
                if (var2.length == 3) {
                    if (Groups.getGroupByName(var2[1]) == null) {
                        sender.addChatMessage(new ChatComponentText("This group does not exist"));
                    } else {
                        String clr = var2[2].toLowerCase();
                        Group g = Groups.getGroupByName(var2[1]);
                        if (clr.equals("help")) {
                            CommandColor.printColors(sender);
                        } else if (clr.equals("random")) {
                            g.setColor(Colours.getRandomColour());
                            g.updateUsers();
                            sender.addChatMessage(new ChatComponentText("The group color has now been set to " + g.getColor()));

                        } else if (Colours.get(clr) != null) {
                            if (CCConfig.INSTANCE.isColorBlackListed(clr)) {
                                sender.addChatMessage(new ChatComponentText(Colours.get("red") + "This color has been blacklisted. Try " +
                                                                            "another color!"));
                            } else {
                                g.setColor(Colours.get(clr));
                                g.updateUsers();
                                sender.addChatMessage(new ChatComponentText("The group color has now been set to " + Colours.get(clr) + clr));
                            }
                        } else {
                            sender.addChatMessage(new ChatComponentText("Valid Colours are: " + Colours.getColourList()));
                        }
                    }
                }
            } else if (var2[0].equals("list")) {
                sender.addChatMessage(new ChatComponentText(Groups.getGroupNames()));
            }
        }
    }
}
