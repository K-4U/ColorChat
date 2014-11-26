package k4unl.minecraft.colorchat.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandCoords extends CommandBase{

	private List<String> aliases;

	public CommandCoords() {

		aliases = new ArrayList<String>();
		aliases.add("cr");
	}

	public List getCommandAliases() {

		return aliases;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {

		return true;
	}

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
		((EntityPlayerMP)sender).mcServer.getConfigurationManager().sendChatMsg(new ChatComponentText(senderCoords));
	}

}
