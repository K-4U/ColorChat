package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.k4lib.commands.CommandK4OpOnly;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.DimensionManager;

public class CommandColorChat extends CommandK4OpOnly {
    @Override
    public String getCommandName() {

        return "colorchat";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {

        return "/colorchat version|save users/groups|load users/groups";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length > 0){
            if(args[0].equals("version")){
                sender.addChatMessage(new ChatComponentText("This server is running colorchat V" + ModInfo.VERSION));
            }
            if(args[0].equals("save") && args.length > 1 && args[1].equals("users")){
                Users.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Saved users"));
            }
            if(args[0].equals("load") && args.length > 1 && args[1].equals("users")){
                Users.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Loaded users"));
            }
            if(args[0].equals("save") && args.length > 1 && args[1].equals("groups")){
                Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Saved groups"));
            }
            if(args[0].equals("load") && args.length > 1 && args[1].equals("groups")){
                Groups.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Loaded groups"));
            }
        }
    }
}
