package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.k4lib.commands.CommandK4Base;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandCoords extends CommandK4Base {

    @Override
    public String getCommandName() {

        return "coords";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {

        return "/coords. Print coordinates you're at.";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] var2) {

        String senderCoords = sender.getName() + " is at [" + sender.getPosition().getX() + ", " + sender.getPosition().getY() + ", " + sender.getPosition().getZ() + "]";
        ((EntityPlayerMP) sender).mcServer.getConfigurationManager().sendChatMsg(new ChatComponentText(senderCoords));
    }
}
