package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.k4lib.lib.Functions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;

public class User {

	private TextFormatting userColor;
	private String nick;
	private String realUserName;
	private boolean hasNick;
	private String group;

	public User(String _username, TextFormatting _userColor, String _nick) {

		realUserName = _username;
		userColor = _userColor;
		nick = _nick;
	}

	public User(String _username) {

		realUserName = _username;
		userColor = Colours.getRandomColour();
	}

	public User(CompoundNBT compoundnbt) {
		this.realUserName = compoundnbt.getString("realUsername");
		this.nick = compoundnbt.getString("nick");
		this.userColor = Colours.get(compoundnbt.getString("color"));
		this.hasNick = compoundnbt.getBoolean("hasNick");
		this.group = compoundnbt.getString("group"); //TODO: Move groups to scoreboard
	}

	public CompoundNBT save() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putString("realUsername", getUserName());
		nbt.putString("nick", getNick());
		nbt.putString("color", getColor().getFriendlyName());
		nbt.putBoolean("hasNick", hasNick());
		if (null != group) {
			nbt.putString("group", group);
		}
		return nbt;
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

	public void setNick(String newNick) {

		nick = newNick;
		hasNick = true;
	}

	public TextFormatting getColor() {

		return userColor;
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

	public PlayerEntity getPlayerEntity() {

		for (PlayerEntity player : Functions.getServer().getPlayerList().getPlayers()) {
			if (player.getGameProfile().getName().equals(this.getUserName())) {
				return player;
			}
		}
		return null;
	}

	public void updateDisplayName() {
		//TODO: RE-enable me whenever forge fixes the refreshDisplayName
        /*
        if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
            getPlayerEntity().getDisplayName()refreshDisplayName();
        }*/
	}

	public boolean isOpped() {

		if (this.realUserName.equals("Server")) {
			return true;
		}

		return Functions.isPlayerOpped(getPlayerEntity().getGameProfile());
	}


}
