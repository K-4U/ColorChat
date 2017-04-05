package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.commands.CommandK4Base;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandNick extends CommandK4Base {

    public CommandNick(){

        aliases.add("nck");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
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
	public String getName() {
		return "nick";
	}

	@Override
	public String getUsage(ICommandSender sender) {
        if(CCConfig.INSTANCE.getBool("nickChangeOPOnly")){
            return "/nick <target> <nick>. Leave nick empty to reset";
        }else{
		    return "/nick <nick>. Leave nick empty to reset";
        }
	}

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        User target;
        String nickToSet;
        if(CCConfig.INSTANCE.getBool("nickChangeOPOnly")){
            if(args.length == 0){
                sender.sendMessage(new TextComponentString("Please specify a target"));
                return;
            }
            target = Users.getUserByName(args[0]);
            if(args.length == 2){
                nickToSet = args[1];
            }else{
                nickToSet = "";
            }
        }else{
		    target = Users.getUserByName(sender.getName());
            if(args.length == 1){
                nickToSet = args[0];
            }else{
                nickToSet = "";
            }
        }
		if(nickToSet.equals("")){
            if(CCConfig.INSTANCE.getBool("announceNickChanges")){
                Functions.sendChatMessageServerWide(new TextComponentString(TextFormatting.GOLD + target.getNick() + "(" + target.getUserName() + ") is now called " + target.getUserName()));
            }
            target.resetNick();
            target.updateDisplayName();
			sender.sendMessage(new TextComponentString("Nick is reset!"));
		}else{
            nickToSet = nickToSet.replace("[", "").replace("]", "");
            if(CCConfig.INSTANCE.isNickBlackListed(nickToSet)){
                Log.error(target.getUserName() + " tried to set a banned nick (" + nickToSet + ")");
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "This nick is banned! You have been reported!"));
                return;
            }
            if(nickToSet.length() >= CCConfig.INSTANCE.getInt("minimumNickLength") && nickToSet.length() <= CCConfig.INSTANCE.getInt("maximumNickLength")){
                if(CCConfig.INSTANCE.getBool("announceNickChanges")){
                    Functions.sendChatMessageServerWide(new TextComponentString(TextFormatting.GOLD + "~" + target.getNick() +
                      "(" + target.getUserName() + ") is now called " + nickToSet));
                }
                target.setNick(nickToSet);
                target.updateDisplayName();
                sender.sendMessage(new TextComponentString("Nick is set to " + nickToSet));
                if(CCConfig.INSTANCE.getBool("changeDisplayName")) {
                    ((EntityPlayerMP) sender).refreshDisplayName();
                }
            }else{
                sender.sendMessage(new TextComponentString("Your nick should be between " + CCConfig.INSTANCE.getInt("minimumNickLength") + " and " + CCConfig.INSTANCE.getInt("maximumNickLength") + " characters long."));
            }
		}
	}
}
