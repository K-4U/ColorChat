package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.text.TextFormatting;

public class Group {

    private String         groupName;
    private TextFormatting groupColor;

    public Group(String _groupName, TextFormatting _groupColor) {

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

    public TextFormatting getColor() {

        return groupColor;
    }

    public void setColor(TextFormatting newColor) {

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
