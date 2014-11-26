package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class CommandRealName extends CommandBase{

	private List<String> aliases;

	public CommandRealName() {

		aliases = new ArrayList<String>();
		aliases.add("rn");
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

		return "realname";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/realname <nick>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		
		if(var2.length == 0){
			sender.addChatMessage(new ChatComponentText("Usage: /realname <nick>"));
		}else{
			User usr = Users.getUserByNick(var2[0]);
			if(usr != null){
				sender.addChatMessage(new ChatComponentText(var2[0] + " = " + usr.getUserName()));	
			}else{
				sender.addChatMessage(new ChatComponentText(var2[0] + " is not a registered nickname"));
			}
		}
	}
}
