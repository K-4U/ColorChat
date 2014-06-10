package k4unl.minecraft.colorchat.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandCoords extends CommandBase{

	public int getRequiredPermissionLevel(){
        return 0;
    }
	
	@Override
	public String getCommandName() {
		return "coords";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/coords. Print coördinates you're at.";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		String senderCoords = sender.getCommandSenderName() + " is at [" + sender.getPlayerCoordinates().posX + ", " + sender.getPlayerCoordinates().posY + ", " + sender.getPlayerCoordinates().posZ + "]";
		((EntityPlayerMP)sender).mcServer.getConfigurationManager().sendChatMsg(new ChatComponentText(senderCoords));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender cmd, String[] args) {
		return null;
	}
}
