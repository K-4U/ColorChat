package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.*;
import k4unl.minecraft.colorchat.lib.config.Config;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandNick implements ICommand {

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender){
        if(Config.getBool("nickChangeOPOnly")){
            if(sender instanceof EntityPlayerMP){
                return MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayerMP) sender).getGameProfile());
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
        if(Config.getBool("nickChangeOPOnly")){
            return "/nick <target> <nick>. Leave nick empty to reset";
        }else{
		    return "/nick <nick>. Leave nick empty to reset";
        }
	}

    @Override
    public List getCommandAliases() {

        return null;
    }

    @Override
	public void processCommand(ICommandSender sender, String[] var2) {
        User target;
        String nickToSet;
        if(Config.getBool("nickChangeOPOnly")){
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
            if(Config.getBool("announceNickChanges")){
                Functions.sendChatMessageServerWide(sender.getEntityWorld(), new ChatComponentText(SpecialChars.GOLD + target.getNick() + "(" + target.getUserName() + ") is now called " + target.getUserName()));
            }
            target.resetNick();
			sender.addChatMessage(new ChatComponentText("Nick is reset!"));
		}else{
            nickToSet = nickToSet.replace("[", "").replace("]", "");
            if(Config.isNickBlackListed(nickToSet)){
                Log.error(target.getUserName() + " tried to set a banned nick (" + nickToSet + ")");
                sender.addChatMessage(new ChatComponentText(SpecialChars.RED + "This nick is banned! You have been reported!"));
                return;
            }
            if(nickToSet.length() >= Config.getInt("minimumNickLength")){
                if(Config.getBool("announceNickChanges")){
                    Functions.sendChatMessageServerWide(sender.getEntityWorld(), new ChatComponentText(SpecialChars.GOLD + "~" + target.getNick() +
                      "(" + target.getUserName() + ") is now called " + nickToSet));
                }
                target.setNick(nickToSet);
                sender.addChatMessage(new ChatComponentText("Nick is set to " + nickToSet));
                ((EntityPlayerMP) sender).refreshDisplayName();
            }else{
                sender.addChatMessage(new ChatComponentText("Your nick should be at least " + Config.getInt("minimumNickLength") + " characters long."));
            }
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender cmd, String[] args) {
		return null;
	}

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {

        return false;
    }

    @Override
    public int compareTo(Object o) {

        return 0;
    }
}
