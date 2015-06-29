package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.EnumChatFormatting;

public class Group {

    private String             groupName;
    private EnumChatFormatting groupColor;

    public Group(String _groupName, EnumChatFormatting _groupColor) {

        groupName = _groupName;
        groupColor = _groupColor;
    }

    public Group(String _groupName) {

        groupName = _groupName;
        groupColor = Colours.getRandomColour();
    }

    public String getName() {

        return groupName;
    }

    public EnumChatFormatting getColor() {

        return groupColor;
    }

    public void setColor(EnumChatFormatting newColor) {

        groupColor = newColor;
    }

    public void updateUsers() {

        if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
            for (User u : Users.getUsersByGroup(this)) {
                if (u.getPlayerEntity() != null) {
                    u.getPlayerEntity().refreshDisplayName();
                }
            }
        }
    }
}
