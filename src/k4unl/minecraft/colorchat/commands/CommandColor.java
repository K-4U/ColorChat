package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Colours;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.CommandK4Base;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

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

        sender.addChatMessage(new TextComponentString("Available colors are " + Colours.getColourList()));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        User sndr = Users.getUserByName(sender.getName());

        boolean isOp = sndr.isOpped();
        int mode = CCConfig.INSTANCE.getInt("mode");

        if(mode == 1 || mode == 2 || (mode == 3 && isOp)) {
            if (args.length == 0) {
                printColors(sender);
            } else {
                String clr = args[0].toLowerCase();
                if (clr.equals("help")) {
                    printColors(sender);
                } else if (clr.equals("random")) {
                    sndr.setUserColor(Colours.getRandomColour());
                    sender.addChatMessage(new TextComponentString("Your color has now been set to " + sndr.getColor()));
                    if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                        if (sender instanceof EntityPlayer) {
                            ((EntityPlayer) sender).refreshDisplayName();
                        }
                    }
                } else if (Colours.get(clr) != null) {
                    if (CCConfig.INSTANCE.isColorBlackListed(clr)) {
                        sender.addChatMessage(new TextComponentString(Colours.get("red") + "This color has been blacklisted. Try another color!"));
                    } else {
                        sndr.setUserColor(Colours.get(clr));
                        sender.addChatMessage(new TextComponentString("Your color has now been set to " + sndr.getColor()));
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
                if (args.length == 2) {
                    if (Users.getUserByName(args[0]) != null) {
                        if (Colours.get(args[1]) != null) {
                            if (CCConfig.INSTANCE.isColorBlackListed(args[1])) {
                                Users.getUserByName(args[0]).setUserColor(Colours.get(args[1]));
                                sender.addChatMessage(new TextComponentString("Color for " + args[0] + " has been set to " + args[1]));
                                if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                                    EntityPlayerMP target = null;
                                    for (EntityPlayerMP player : server.getPlayerList().getPlayerList()) {
                                        if(player.getName().equalsIgnoreCase(args[0])) {
                                            target = player;
                                        }
                                    }
                                    if (target != null) {
                                        target.refreshDisplayName();
                                    }
                                }
                            } else {
                                sender.addChatMessage(new TextComponentString(Colours.get("red") + "This color has been blacklisted. Try another color!"));
                            }
                        }
                    }
                }
            }
        } else {
            sender.addChatMessage(new TextComponentString("This command is disabled on this server."));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

        User sndr = Users.getUserByName(sender.getName());

        boolean isOp = sndr.isOpped();
        int mode = CCConfig.INSTANCE.getInt("mode");

        return (mode == 1 || mode == 2 || (mode == 3 && isOp));
    }
}
