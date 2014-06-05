package k4unl.minecraft.colorchat.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandGroup extends CommandBase{

	@Override
	public int getRequiredPermissionLevel(){
        return 3;
    }

	
	@Override
	public String getCommandName() {
		return "group";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		if(var2.length == 0){
			sender.addChatMessage(new ChatComponentText("Usage: " + getCommandUsage(sender)));
		}else{
			if(var2[0].equals("create")){
				
			}
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] var2) {
		return null;
	}
}
