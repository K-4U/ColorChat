package k4unl.minecraft.colorchat.commands;

import k4unl.minecraft.colorchat.lib.Group;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.lib.SpecialChars;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandGroup extends CommandBase {


	private List<String> aliases;

	public CommandGroup(){
		aliases = new ArrayList<String>();
		aliases.add("gr");
	}

	public List getCommandAliases() {

		return aliases;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender){
        if(sender instanceof EntityPlayerMP){
			return true;//Functions.isPlayerOpped(((EntityPlayerMP) sender).getGameProfile());
        } else {
            return true;
        }
	}

	
	@Override
	public String getCommandName() {
		return "group";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/group [list]/[create <name>]/[remove <name>]/[addUser <group> <name>]/[delUser <group> <name>]/[color <group> <color>]/[save]/[load]";
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
            }else if(var2[0].equals("remove")){
                if(var2.length == 2){
                    if(Groups.getGroupByName(var2[1]) != null){
                        Group g = Groups.getGroupByName(var2[1]);
                        sender.addChatMessage(new ChatComponentText("Group " + g.getColor() + var2[1] + "" + SpecialChars.RESET + " has been removed"));
						g.updateUsers();
                        Groups.removeGroupByName(var2[1]);
                    }else{
                        sender.addChatMessage(new ChatComponentText("This group doesn't exists"));
                    }
                }else{
                    sender.addChatMessage(new ChatComponentText("Usage: /group remove <name>"));
                }
            }else if(var2[0].equals("save")){
                Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Groups saved to file!"));
            }else if(var2[0].equals("load")){
                Groups.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
                sender.addChatMessage(new ChatComponentText("Groups loaded from file!"));
				Groups.updateAll();
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
							sndr.updateDisplayName();
							sender.addChatMessage(new ChatComponentText("Added " + sndr.getColor() + sndr.getUserName() + SpecialChars.RESET +  " to " + g.getColor() + g.getName()));
						}
					}
				}else{
					sender.addChatMessage(new ChatComponentText("Usage: /group addUser <groupName> <userName>"));
				}
            }else if(var2[0].equals("delUser")){
                if(var2.length == 3){
                    if(Groups.getGroupByName(var2[1]) == null){
                        sender.addChatMessage(new ChatComponentText("This group does not exist"));
                    }else{
                        Group g = Groups.getGroupByName(var2[1]);
                        User sndr = Users.getUserByName(var2[2]);
                        if(sndr.getGroup() != null && sndr.getGroup().equals(g)){
                            sndr.setGroup(null);
                            sender.addChatMessage(new ChatComponentText(sndr.getColor() + sndr.getUserName() + SpecialChars.RESET + " is removed from " + g.getColor() + g.getName()));
							sndr.updateDisplayName();
                        }else{
                            sender.addChatMessage(new ChatComponentText(sndr.getColor() + sndr.getUserName() + SpecialChars.RESET + " is not in this group"));
                        }
                    }
                }else{
                    sender.addChatMessage(new ChatComponentText("Usage: /group delUser <groupName> <userName>"));
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
                            while(CCConfig.INSTANCE.isColorBlackListed(newClr)){
                                newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
                            }

							g.setColor(CommandColor.colors.get(newClr));
							g.updateUsers();
							sender.addChatMessage(new ChatComponentText("The group color has now been set to " + CommandColor.colors.get(newClr) + newClr));

						}else if(CommandColor.colors.containsKey(clr)){
                            if(CCConfig.INSTANCE.isColorBlackListed(clr)){
                                sender.addChatMessage(new ChatComponentText(CommandColor.colors.get("red") + "This color has been blacklisted. Try " +
                                  "another color!"));
                            }else{
                                g.setColor(CommandColor.colors.get(clr));
								g.updateUsers();
                                sender.addChatMessage(new ChatComponentText("The group color has now been set to " + CommandColor.colors.get(clr) + clr));
                            }
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
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {

        return false;
    }

    @Override
    public int compareTo(Object o) {

        return 0;
    }
}
