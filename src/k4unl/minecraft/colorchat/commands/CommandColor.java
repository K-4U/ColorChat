package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.lib.SpecialChars;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.*;

public class CommandColor extends CommandBase{

	private List<String> aliases;

	public CommandColor(){
		aliases = new ArrayList<String>();
		aliases.add("clr");
	}

	public List getCommandAliases() {

		return aliases;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender){
		return true;
	}
	
	public static Map<String, SpecialChars> colors = new HashMap<String, SpecialChars>(); 
	
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
		return "/color " + getColors();
	}

	public static String getColors(){
		String colorString = "";
		for(String c: colors.keySet()){
            if(!CCConfig.INSTANCE.isColorBlackListed(c)){
			    colorString += ", " + c;
            }
		}
		return colorString;
	}
	
	public static void printColors(ICommandSender sender){
		sender.addChatMessage(new ChatComponentText("Available colors are " + getColors()));
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] var2) {
		User sndr = Users.getUserByName(sender.getName());
		if(var2.length == 0){
			printColors(sender);
		}else{
			String clr = var2[0].toLowerCase();
			if(clr.equals("help")){
				printColors(sender);
			}else if(clr.equals("random")){
                List<String> keysAsArray = new ArrayList<String>(colors.keySet());
                String newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
                while(CCConfig.INSTANCE.isColorBlackListed(newClr)){
				    newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
                }
				
				sndr.setUserColor(colors.get(newClr));
				sender.addChatMessage(new ChatComponentText("Your color has now been set to " + colors.get(newClr) + newClr));
				if(sender.getCommandSenderEntity() instanceof EntityPlayer){
					((EntityPlayer)sender.getCommandSenderEntity()).refreshDisplayName();
				}
			}else if(colors.containsKey(clr)){
                if(CCConfig.INSTANCE.isColorBlackListed(clr)){
                    sender.addChatMessage(new ChatComponentText(colors.get("red") + "This color has been blacklisted. Try another color!"));
                }else{
				    sndr.setUserColor(colors.get(clr));
				    sender.addChatMessage(new ChatComponentText("Your color has now been set to " + colors.get(clr) + clr));
					if(sender.getCommandSenderEntity() instanceof EntityPlayer){
						((EntityPlayer)sender.getCommandSenderEntity()).refreshDisplayName();
					}
                }
			}else{
				printColors(sender);
			}
		}
	}
}
