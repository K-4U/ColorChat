package k4unl.minecraft.colorchat.commands;

import java.util.List;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandRealName extends CommandBase{

	@Override
	public String getCommandName() {
		return "realname";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
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

	@Override
	public List addTabCompletionOptions(ICommandSender cmd, String[] args) {
		return null;
	}
}
