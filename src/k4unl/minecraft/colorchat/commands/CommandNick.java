package k4unl.minecraft.colorchat.commands;

import java.util.List;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandNick extends CommandBase{

	public int getRequiredPermissionLevel(){
        return 4;
    }
	
	@Override
	public String getCommandName() {
		return "nick";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/nick <nick>. Leave nick empty to reset";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		User sndr = Users.getUserByName(sender.getCommandSenderName());
		if(var2.length == 0){
			sndr.resetNick();
			sender.addChatMessage(new ChatComponentText("Nick is reset!"));
		}else{
			sndr.setNick(var2[0]);
			sender.addChatMessage(new ChatComponentText("Nick is set to " + var2[0]));
			
		}
		
	}

	@Override
	public List addTabCompletionOptions(ICommandSender cmd, String[] args) {
		return null;
	}
}
