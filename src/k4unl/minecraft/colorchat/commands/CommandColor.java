package k4unl.minecraft.colorchat.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import k4unl.minecraft.colorchat.lib.SpecialChars;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandColor extends CommandBase{

	private static Map<String, SpecialChars> colors = new HashMap<String, SpecialChars>(); 
	
	static {
		colors.put("black", SpecialChars.BLACK);
		colors.put("darkblue", SpecialChars.DBLUE);
		colors.put("darkgreen", SpecialChars.DGREEN);
		colors.put("darkaqua", SpecialChars.DAQUA);
		colors.put("darkred", SpecialChars.DRED);
		colors.put("darkpurple", SpecialChars.DPURPLE);
		colors.put("gold", SpecialChars.GOLD);
		colors.put("gray", SpecialChars.GRAY);
		colors.put("darkgray", SpecialChars.DGRAY);
		colors.put("blue", SpecialChars.BLUE);
		colors.put("green", SpecialChars.GREEN);
		colors.put("aqua", SpecialChars.AQUA);
		colors.put("red", SpecialChars.RED);
		colors.put("lightpurple", SpecialChars.LPURPLE);
		colors.put("yellow", SpecialChars.YELLOW);
		colors.put("white", SpecialChars.WHITE);
	}
	
	@Override
	public String getCommandName() {
		return "color";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "color name";
	}

	public void printColors(ICommandSender sender){
		String colorString = "Available colors are ";
		for(String c: colors.keySet()){
			colorString += ", " + c;
		}
		sender.addChatMessage(new ChatComponentText(colorString));
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		User sndr = Users.getUserByName(sender.getCommandSenderName());
		if(var2.length == 0){
			printColors(sender);
		}else{
			String clr = var2[0].toLowerCase();
			if(clr == "help"){
				printColors(sender);
			}else if(colors.containsKey(clr)){
				sndr.setUserColor(colors.get(clr));
				sender.addChatMessage(new ChatComponentText("Your color has now been set to " + colors.get(clr) + clr));
			}else{
				printColors(sender);
			}
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender cmd, String[] args) {
		return null;
	}
}
