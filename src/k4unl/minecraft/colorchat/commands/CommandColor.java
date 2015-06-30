package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Colours;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.CommandK4Base;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandColor extends CommandK4Base {

    public CommandColor() {

        aliases.add("clr");
        aliases.add("colour");
    }

    @Override
    public String getCommandName() {

        return "color";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {

        return "/color " + Colours.getColourList();
    }

    public static void printColors(ICommandSender sender) {

        sender.addChatMessage(new ChatComponentText("Available colors are " + Colours.getColourList()));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] var2) {

        User sndr = Users.getUserByName(sender.getName());

        boolean isOp = sndr.isOpped();
        int mode = CCConfig.INSTANCE.getInt("mode");

        if(mode == 1 || mode == 2 || (mode == 3 && isOp)) {
            if (var2.length == 0) {
                printColors(sender);
            } else {
                String clr = var2[0].toLowerCase();
                if (clr.equals("help")) {
                    printColors(sender);
                } else if (clr.equals("random")) {
                    sndr.setUserColor(Colours.getRandomColour());
                    sender.addChatMessage(new ChatComponentText("Your color has now been set to " + sndr.getColor()));
                    if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                        if (sender instanceof EntityPlayer) {
                            ((EntityPlayer) sender).refreshDisplayName();
                        }
                    }
                } else if (Colours.get(clr) != null) {
                    if (CCConfig.INSTANCE.isColorBlackListed(clr)) {
                        sender.addChatMessage(new ChatComponentText(Colours.get("red") + "This color has been blacklisted. Try another color!"));
                    } else {
                        sndr.setUserColor(Colours.get(clr));
                        sender.addChatMessage(new ChatComponentText("Your color has now been set to " + sndr.getColor()));
                        if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                            if (sender instanceof EntityPlayer) {
                                ((EntityPlayer) sender).refreshDisplayName();
                            }
                        }
                    }
                } else {
                    printColors(sender);
                }
            }
            if (mode == 3) {
                if (var2.length == 2) {
                    if (Users.getUserByName(var2[0]) != null) {
                        if (Colours.get(var2[1]) != null) {
                            if (CCConfig.INSTANCE.isColorBlackListed(var2[1])) {
                                Users.getUserByName(var2[0]).setUserColor(Colours.get(var2[1]));
                                sender.addChatMessage(new ChatComponentText("Color for " + var2[0] + " has been set to " + var2[1]));
                                if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                                    EntityPlayer target = null;
                                    for (EntityPlayer player : (List<EntityPlayer>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                                        if(player.getName().equalsIgnoreCase(var2[0])) {
                                            target = player;
                                        }
                                    }
                                    if (target != null) {
                                        target.refreshDisplayName();
                                    }
                                }
                            } else {
                                sender.addChatMessage(new ChatComponentText(Colours.get("red") + "This color has been blacklisted. Try another color!"));
                            }
                        }
                    }
                }
            }
        } else {
            sender.addChatMessage(new ChatComponentText("This command is disabled on this server."));
        }
    }
}
