package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.k4lib.commands.CommandK4Base;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRealName extends CommandK4Base {
    
    public CommandRealName() {
        
        aliases.add("rn");
    }
    
    @Override
    public String getName() {
        
        return "realname";
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        
        return "/realname <nick>";
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("Usage: /realname <nick>"));
        } else {
            User usr = Users.getUserByNick(args[0]);
            if (usr != null) {
                sender.sendMessage(new TextComponentString(args[0] + " = " + usr.getUserName()));
            } else {
                sender.sendMessage(new TextComponentString(args[0] + " is not a registered nickname"));
            }
        }
    }
    
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        
        return true;
    }
}
