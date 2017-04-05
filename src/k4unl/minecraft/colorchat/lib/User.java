package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class User {
    
    private TextFormatting userColor;
    private String         nick;
    private String         realUserName;
    private boolean        hasNick;
    private String         group;
    
    public User(String _username, TextFormatting _userColor, String _nick) {
        
        realUserName = _username;
        userColor = _userColor;
        nick = _nick;
    }
    
    public User(String _username) {
        
        realUserName = _username;
        userColor = Colours.getRandomColour();
    }
    
    public String getUserName() {
        
        return realUserName;
    }
    
    public String getNick() {
        
        if (hasNick) {
            return nick;
        } else {
            return realUserName;
        }
    }
    
    public TextFormatting getColor() {
        
        return userColor;
    }
    
    public void setNick(String newNick) {
        
        nick = newNick;
        hasNick = true;
    }
    
    public void resetNick() {
        
        hasNick = false;
    }
    
    public void setUserColor(TextFormatting newColor) {
        
        userColor = newColor;
    }
    
    public boolean hasNick() {
        
        return hasNick;
    }
    
    public Group getGroup() {
        
        return Groups.getGroupByName(group);
    }
    
    public void setGroup(Group newGroup) {
        
        group = newGroup.getName();
    }
    
    public EntityPlayer getPlayerEntity() {
        
        for (EntityPlayer player : Functions.getServer().getPlayerList().getPlayers()) {
            if (player.getGameProfile().getName().equals(this.getUserName())) {
                return player;
            }
        }
        return null;
    }
    
    public void updateDisplayName() {
        
        if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
            getPlayerEntity().refreshDisplayName();
        }
    }
    
    public boolean isOpped() {
        
        if (this.realUserName.equals("Server")) {
            return true;
        }
        
        return Functions.isPlayerOpped(getPlayerEntity().getGameProfile());
    }
}
