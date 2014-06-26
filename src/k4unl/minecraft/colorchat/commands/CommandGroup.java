package k4unl.minecraft.colorchat.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import k4unl.minecraft.colorchat.lib.Group;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.SpecialChars;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class CommandGroup extends CommandBase{

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender){
		
		return MinecraftServer.getServer().getConfigurationManager().func_152596_g(getCommandSenderAsPlayer(sender).getGameProfile());
	}

	
	@Override
	public String getCommandName() {
		return "group";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/group [list]/[create <name>]/[addUser <group> <name>]/[color <group> <color>]";
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
						User sndr = Users.getUserByName(var2[2]);
						if(sndr.getGroup() != null && sndr.getGroup().equals(g)){
							sender.addChatMessage(new ChatComponentText(sndr.getColor() + sndr.getUserName() + SpecialChars.RESET + " is already in this group."));
						}else{
							sndr.setGroup(g);
							sender.addChatMessage(new ChatComponentText("Added " + sndr.getColor() + sndr.getUserName() + SpecialChars.RESET +  " to " + g.getColor() + g.getName()));
						}
					}
				}else{
					sender.addChatMessage(new ChatComponentText("Usage: /group addUser <groupName> <userName>"));
				}
			}else if(var2[0].equals("color")){
				if(var2.length == 3){
					if(Groups.getGroupByName(var2[1]) == null){
						sender.addChatMessage(new ChatComponentText("This group does not exist"));
					}else{
						String clr = var2[2].toLowerCase();
						Group g = Groups.getGroupByName(var2[1]);
						if(clr.equals("help")){
							CommandColor.printColors(sender);
						}else if(clr.equals("random")){
							List<String> keysAsArray = new ArrayList<String>(CommandColor.colors.keySet());
							String newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
							
							g.setColor(CommandColor.colors.get(newClr));
							sender.addChatMessage(new ChatComponentText("The group color has now been set to " + CommandColor.colors.get(newClr) + newClr));
						}else if(CommandColor.colors.containsKey(clr)){
							g.setColor(CommandColor.colors.get(clr));
							sender.addChatMessage(new ChatComponentText("The group color has now been set to " + CommandColor.colors.get(clr) + clr));
						}else{
							CommandColor.printColors(sender);
						}
					}
				}
			}else if(var2[0].equals("list")){
				sender.addChatMessage(new ChatComponentText(Groups.getGroupNames()));
			}
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] var2) {
		return null;
	}
}
