package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.*;
import k4unl.minecraft.colorchat.lib.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandNick extends CommandBase{

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender){
		return true;
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
            if(Config.getBool("announceNickChanges")){
                Functions.sendChatMessageServerWide(sender.getEntityWorld(), new ChatComponentText(SpecialChars.GOLD + sndr.getNick() + "(" + sndr.getUserName() + ") is now called " + sndr.getUserName()));
            }
            sndr.resetNick();
			sender.addChatMessage(new ChatComponentText("Nick is reset!"));
		}else{
            var2[0] = var2[0].replace("[", "").replace("]", "");
            if(Config.isNickBlackListed(var2[0])){
                Log.error(sndr.getUserName() + " tried to set a banned nick (" + var2[0] + ")");
                sender.addChatMessage(new ChatComponentText(SpecialChars.RED + "This nick is banned! You have been reported!"));
                return;
            }
            if(var2[0].length() >= Config.getInt("minimumNickLength")){
                if(Config.getBool("announceNickChanges")){
                    Functions.sendChatMessageServerWide(sender.getEntityWorld(), new ChatComponentText(SpecialChars.GOLD + "~" + sndr.getNick() + "(" + sndr.getUserName() + ") is now called " + var2[0]));
                }
                sndr.setNick(var2[0]);
                sender.addChatMessage(new ChatComponentText("Nick is set to " + var2[0]));
            }else{
                sender.addChatMessage(new ChatComponentText("Your nick should be at least " + Config.getInt("minimumNickLength") + " characters long."));
            }
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender cmd, String[] args) {
		return null;
	}
}
