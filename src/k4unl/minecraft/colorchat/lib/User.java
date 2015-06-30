package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class User {

    private EnumChatFormatting userColor;
    private String             nick;
    private String             realUserName;
    private boolean            hasNick;
    private Group              group;

    public User(String _username, EnumChatFormatting _userColor, String _nick) {

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

    public EnumChatFormatting getColor() {

        return userColor;
    }

    public void setNick(String newNick) {

        nick = newNick;
        hasNick = true;
    }

    public void resetNick() {

        hasNick = false;
    }

    public void setUserColor(EnumChatFormatting newColor) {

        userColor = newColor;
    }

    public boolean hasNick() {

        return hasNick;
    }

    public Group getGroup() {

        return group;
    }

    public void setGroup(Group newGroup) {

        group = newGroup;
    }

    public EntityPlayer getPlayerEntity() {

        for (EntityPlayer player : (List<EntityPlayer>) MinecraftServer.getServer().getEntityWorld().playerEntities) {
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
        return Functions.isPlayerOpped(realUserName);
    }
}
