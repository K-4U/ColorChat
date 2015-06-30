package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.CommandK4Base;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandNick extends CommandK4Base {

    public CommandNick(){

        aliases.add("nck");
    }


	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender){
        if(CCConfig.INSTANCE.getBool("nickChangeOPOnly")){
            if(sender instanceof EntityPlayerMP){
                return Functions.isPlayerOpped(((EntityPlayerMP) sender).getGameProfile());
            } else {
                return true;
            }
        }else{
            return true;
        }
	}
	
	@Override
	public String getCommandName() {
		return "nick";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
        if(CCConfig.INSTANCE.getBool("nickChangeOPOnly")){
            return "/nick <target> <nick>. Leave nick empty to reset";
        }else{
		    return "/nick <nick>. Leave nick empty to reset";
        }
	}

    @Override
	public void processCommand(ICommandSender sender, String[] var2) {
        User target;
        String nickToSet;
        if(CCConfig.INSTANCE.getBool("nickChangeOPOnly")){
            if(var2.length == 0){
                sender.addChatMessage(new ChatComponentText("Please specify a target"));
                return;
            }
            target = Users.getUserByName(var2[0]);
            if(var2.length == 2){
                nickToSet = var2[1];
            }else{
                nickToSet = "";
            }
        }else{
		    target = Users.getUserByName(sender.getCommandSenderName());
            if(var2.length == 1){
                nickToSet = var2[0];
            }else{
                nickToSet = "";
            }
        }
		if(nickToSet.equals("")){
            if(CCConfig.INSTANCE.getBool("announceNickChanges")){
                Functions.sendChatMessageServerWide(sender.getEntityWorld(), new ChatComponentText(EnumChatFormatting.GOLD + target.getNick() + "(" + target.getUserName() + ") is now called " + target.getUserName()));
            }
            target.resetNick();
            target.updateDisplayName();
			sender.addChatMessage(new ChatComponentText("Nick is reset!"));
		}else{
            nickToSet = nickToSet.replace("[", "").replace("]", "");
            if(CCConfig.INSTANCE.isNickBlackListed(nickToSet)){
                Log.error(target.getUserName() + " tried to set a banned nick (" + nickToSet + ")");
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This nick is banned! You have been reported!"));
                return;
            }
            if(nickToSet.length() >= CCConfig.INSTANCE.getInt("minimumNickLength") && nickToSet.length() <= CCConfig.INSTANCE.getInt("maximumNickLength")){
                if(CCConfig.INSTANCE.getBool("announceNickChanges")){
                    Functions.sendChatMessageServerWide(sender.getEntityWorld(), new ChatComponentText(EnumChatFormatting.GOLD + "~" + target.getNick() +
                      "(" + target.getUserName() + ") is now called " + nickToSet));
                }
                target.setNick(nickToSet);
                target.updateDisplayName();
                sender.addChatMessage(new ChatComponentText("Nick is set to " + nickToSet));
                if(CCConfig.INSTANCE.getBool("changeDisplayName")) {
                    ((EntityPlayerMP) sender).refreshDisplayName();
                }
            }else{
                sender.addChatMessage(new ChatComponentText("Your nick should be between " + CCConfig.INSTANCE.getInt("minimumNickLength") + " and " + CCConfig.INSTANCE.getInt("maximumNickLength") + " characters long."));
            }
		}
	}
}
