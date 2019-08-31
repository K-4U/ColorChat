package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;

public class Group {

	private String groupName;
	private TextFormatting groupColor;

	public Group(String _groupName, TextFormatting _groupColor) {

		groupName = _groupName;
		groupColor = _groupColor;
	}

	public Group(String _groupName) {

		groupName = _groupName;
		groupColor = Colours.getRandomColour();
	}

	public Group(CompoundNBT compoundnbt) {
		this(compoundnbt.getString("name"), Colours.get(compoundnbt.getString("colour")));
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

		if (CCConfig.changeDisplayName.get()) {
			for (User u : Users.getUsersByGroup(this)) {
				if (u.getPlayerEntity() != null) {
					u.updateDisplayName();
				}
			}
		}
	}

	public CompoundNBT save() {
		CompoundNBT ret = new CompoundNBT();
		ret.putString("name", getName());
		ret.putString("colour", getColor().getFriendlyName());
		return ret;
	}
}
