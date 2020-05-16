package k4unl.minecraft.colorchat.network.packages;

import io.netty.buffer.ByteBuf;
import k4unl.minecraft.colorchat.lib.Group;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.k4lib.network.messages.AbstractPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
public class PackageAllDataToClient extends AbstractPacket {

	private List<Group> groups = new ArrayList<>();
	private List<User> users = new ArrayList<>();

	public PackageAllDataToClient() {
	}

	public PackageAllDataToClient(ByteBuf buffer) {
		PackageAllDataToClient packet = new PackageAllDataToClient();
		//Read group number
		int groupCount = buffer.readInt();
		for (int i = 0; i < groupCount; i++) {
			String groupName = packet.readString(buffer);
			TextFormatting groupColor = TextFormatting.fromColorIndex(buffer.readInt());
			packet.getGroups().add(new Group(groupName, groupColor));
		}

		//Now users
		int userCount = buffer.readInt();
		for (int i = 0; i < userCount; i++) {
			String userName = packet.readString(buffer);
			String nickName = packet.readString(buffer);
			String groupName = packet.readString(buffer);
			TextFormatting userColour = TextFormatting.fromColorIndex(buffer.readInt());
			packet.getUsers().add(new User(userName, userColour, nickName, groupName));
		}
	}

	public PackageAllDataToClient(List<Group> groups, List<User> users) {
		this.groups = groups;
		this.users = users;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public List<User> getUsers() {
		return users;
	}

	@Override
	public void handleClientSide(PlayerEntity player) {
		Users.getUsers().clear();
		Groups.getGroups().clear();
		Groups.getGroups().addAll(this.getGroups());
		Users.getUsers().addAll(this.getUsers());
	}

	@Override
	public void handleServerSide(PlayerEntity player) {

	}

	@Override
	public void toBytes(ByteBuf buffer) {
		//Groups first:
		buffer.writeInt(getGroups().size());
		for (Group group : getGroups()) {
			writeString(group.getName(), buffer);
			buffer.writeInt(group.getColor().getColorIndex());
		}

		//Then users:
		buffer.writeInt(getUsers().size());
		for (User user : getUsers()) {
			writeString(user.getUserName(), buffer);
			writeString(user.getNick(), buffer);
			writeString(user.getGroup().getName(), buffer);
			buffer.writeInt(user.getColor().getColorIndex());
		}
	}


}
