package k4unl.minecraft.colorchat.worlddata;

import k4unl.minecraft.colorchat.lib.Group;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

/**
 * @author Koen Beckers (K-4U)
 */
public class ColourChatData extends WorldSavedData {
	public ColourChatData() {
		super(ModInfo.ID);
	}

	public static ColourChatData get(ServerWorld world) {
		DimensionSavedDataManager storage = world.getSavedData();
		return storage.getOrCreate(ColourChatData::new, ModInfo.ID);
	}

	@Override
	public void read(CompoundNBT nbt) {
		ListNBT groups = nbt.getList("groups", 10);
		for (int i = 0; i < groups.size(); ++i) {
			CompoundNBT compoundnbt = groups.getCompound(i);
			Group group = new Group(compoundnbt);
			Groups.addGroup(group);
		}

		ListNBT users = nbt.getList("users", 10);
		for (int i = 0; i < users.size(); ++i) {
			CompoundNBT compoundnbt = users.getCompound(i);
			User user = new User(compoundnbt);
			Users.addUser(user);
		}
	}

	@Override
	public boolean isDirty() {
		return true;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ListNBT groups = new ListNBT();
		for (Group group : Groups.getGroups()) {
			CompoundNBT nbt = group.save();
			groups.add(nbt);
		}
		compound.put("groups", groups);

		ListNBT users = new ListNBT();
		for (User user : Users.getUsers()) {
			CompoundNBT nbt = user.save();
			users.add(nbt);
		}
		compound.put("users", users);

		return compound;
	}
}
