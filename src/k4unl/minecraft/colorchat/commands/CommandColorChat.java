package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by koen_000 on 12-4-2015.
 */
public class CommandColorChat extends CommandBase {
    @Override
    public String getCommandName() {

        return "colorchat";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {

        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length > 0){
            if(args[0].equals("version")){
                sender.addChatMessage(new ChatComponentText("This server is running colorchat V" + ModInfo.VERSION));
            }
            if(args[0].equals("save") && args.length > 1 && args[1].equals("users")){
                Users.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
            }
            if(args[0].equals("load") && args.length > 1 && args[1].equals("users")){
                Users.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
            }
        }

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        if(sender.getCommandSenderName().equals("K4Unl") || sender.getCommandSenderName().equals("ThatsMe")){
            return true;
        }
        if(sender instanceof EntityPlayerMP){
            return Functions.isPlayerOpped(((EntityPlayerMP) sender).getGameProfile());
        } else {
            return true;
        }
    }

}
