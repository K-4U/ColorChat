package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class CommandColor extends CommandBase {

    private List<String> aliases;

    public CommandColor() {

        aliases = new ArrayList<String>();
        aliases.add("clr");
    }

    @Override
    public List getCommandAliases() {

        aliases.add("colour");
        return aliases;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {

        return true;
    }

    public static Map<String, EnumChatFormatting> colors = new HashMap<String, EnumChatFormatting>();

    static {
        colors.put("black", EnumChatFormatting.BLACK);
        colors.put("darkblue", EnumChatFormatting.DARK_BLUE);
        colors.put("darkgreen", EnumChatFormatting.DARK_GREEN);
        colors.put("darkaqua", EnumChatFormatting.DARK_AQUA);
        colors.put("darkred", EnumChatFormatting.DARK_RED);
        colors.put("darkpurple", EnumChatFormatting.DARK_PURPLE);
        colors.put("gold", EnumChatFormatting.GOLD);
        colors.put("gray", EnumChatFormatting.GRAY);
        colors.put("darkgray", EnumChatFormatting.DARK_GRAY);
        colors.put("blue", EnumChatFormatting.BLUE);
        colors.put("green", EnumChatFormatting.GREEN);
        colors.put("aqua", EnumChatFormatting.AQUA);
        colors.put("red", EnumChatFormatting.RED);
        colors.put("lightpurple", EnumChatFormatting.LIGHT_PURPLE);
        colors.put("yellow", EnumChatFormatting.YELLOW);
        colors.put("white", EnumChatFormatting.WHITE);
    }

    @Override
    public String getCommandName() {

        return "color";
    }


    @Override
    public String getCommandUsage(ICommandSender sender) {

        return "/color " + getColors();
    }

    public static String getColors() {

        String colorString = "";
        for (String c : colors.keySet()) {
            if (!CCConfig.INSTANCE.isColorBlackListed(c)) {
                colorString += ", " + c;
            }
        }
        return colorString;
    }

    public static void printColors(ICommandSender sender) {

        sender.addChatMessage(new ChatComponentText("Available colors are " + getColors()));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] var2) {

        User sndr = Users.getUserByName(sender.getCommandSenderName());

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
                    List<String> keysAsArray = new ArrayList<String>(colors.keySet());
                    String newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
                    while (CCConfig.INSTANCE.isColorBlackListed(newClr)) {
                        newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
                    }

                    sndr.setUserColor(colors.get(newClr));
                    sender.addChatMessage(new ChatComponentText("Your color has now been set to " + colors.get(newClr) + newClr));
                    if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                        if (sender instanceof EntityPlayer) {
                            ((EntityPlayer) sender).refreshDisplayName();
                        }
                    }
                } else if (colors.containsKey(clr)) {
                    if (CCConfig.INSTANCE.isColorBlackListed(clr)) {
                        sender.addChatMessage(new ChatComponentText(colors.get("red") + "This color has been blacklisted. Try another color!"));
                    } else {
                        sndr.setUserColor(colors.get(clr));
                        sender.addChatMessage(new ChatComponentText("Your color has now been set to " + colors.get(clr) + clr));
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
                        if (colors.containsKey(var2[1])) {
                            if (CCConfig.INSTANCE.isColorBlackListed(var2[1])) {
                                Users.getUserByName(var2[0]).setUserColor(colors.get(var2[1]));
                                sender.addChatMessage(new ChatComponentText("Color for " + var2[0] + " has been set to " + var2[1]));
                                if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
                                    EntityPlayer target = null;
                                    for (EntityPlayer player : (List<EntityPlayer>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                                        if(player.getCommandSenderName().equalsIgnoreCase(var2[0])) {
                                            target = player;
                                        }
                                    }
                                    if (target != null) {
                                        target.refreshDisplayName();
                                    }
                                }
                            } else {
                                sender.addChatMessage(new ChatComponentText(colors.get("red") + "This color has been blacklisted. Try another color!"));
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
