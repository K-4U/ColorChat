package k4unl.minecraft.colorchat.lib;

import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class Groups {

	private static List<Group> groupList = new ArrayList<>();

	public static Group getGroupByName(String name) {

		for (Group g : groupList) {
			if (g.getName().equalsIgnoreCase(name)) {
				return g;
			}
		}
		return null;
	}

	public static Group createNewGroup(String name) {

		Group g = getGroupByName(name);
		if (g == null) {
			g = new Group(name);
			groupList.add(g);
		}
		return g;
	}


	public static Group addGroup(Group group) {
		Group g = getGroupByName(group.getName());
		if (g == null) {
			groupList.add(group);
		}
		return group;
	}

	public static void removeGroupByName(String name) {

		Group g = getGroupByName(name);
		Users.removeGroupFromUsers(g);
		if (g != null) {
			groupList.remove(g);
		}
	}

	public static boolean setGroupColor(String name, TextFormatting newColor) {

		Group g = getGroupByName(name);
		if (g == null) {
			return false;
		} else {
			g.setColor(newColor);
			return true;
		}
	}

	public static void updateAll() {

		for (Group g : groupList) {
			g.updateUsers();
		}
	}

	public static String[] getGroupNames() {

		List<String> all = new ArrayList<>();
		for (Group g : groupList) {
			all.add(g.getName());
		}
		return all.toArray(new String[0]);
	}


	public static List<Group> getGroups() {
		return groupList;
	}
}
