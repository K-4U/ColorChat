package k4unl.minecraft.colorchat.commands;

import java.util.List;

import k4unl.minecraft.colorchat.lib.Group;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.SpecialChars;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
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
		return "/group [create <name>]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		if(var2.length == 0){
			sender.addChatMessage(new ChatComponentText("Usage: " + getCommandUsage(sender)));
		}else{
			if(var2[0].equals("create")){
				if(var2.length == 2){
					if(Groups.getGroupByName(var2[1]) == null){
						Group g = Groups.createNewGroup(var2[1]);
						sender.addChatMessage(new ChatComponentText("Group " + g.getColor() + var2[1] + "" + SpecialChars.RESET + " has been created"));
					}else{
						sender.addChatMessage(new ChatComponentText("This group already exists"));
					}
				}else{
					sender.addChatMessage(new ChatComponentText("Usage: /group create <name>"));
				}
			}else if(var2[0].equals("addUser")){
				if(var2.length == 3){
					if(Groups.getGroupByName(var2[1]) == null){
						sender.addChatMessage(new ChatComponentText("This group does not exist"));
					}else{
						Group g = Groups.getGroupByName(var2[1]);
						User sndr = Users.getUserByName(sender.getCommandSenderName());
						sndr.setGroup(g);
					}
				}else{
					sender.addChatMessage(new ChatComponentText("Usage: /group addUser <groupName> <userName>"));
				}
			}
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] var2) {
		return null;
	}
}
