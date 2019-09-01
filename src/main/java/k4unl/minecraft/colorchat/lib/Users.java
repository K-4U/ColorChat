package k4unl.minecraft.colorchat.lib;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private static List<User> userList;

	public static void init() {

		userList = new ArrayList<User>();
	}

	public static void addUser(User user) {
		for (User u : userList) {
			if (u.getUserName().equals(user.getUserName())) {
				return;
			}
		}
		userList.add(user);
	}

	public static User getUserByName(String username) {

		for (User u : userList) {
			if (u.getUserName().equals(username)) {
				return u;
			}
		}
		User nUser = new User(username);
		userList.add(nUser);
		return nUser;
	}

	public static User getUserByNick(String nick) {

		for (User u : userList) {
			if (u.getNick().equals(nick)) {
				return u;
			}
		}
		return null;
	}

	public static List<User> getUsersByGroup(Group g) {

		List<User> userList = new ArrayList<User>();
		for (User u : userList) {
			if (u.getGroup().equals(g)) {
				userList.add(u);
			}
		}
		return userList;
	}

	public static void removeGroupFromUsers(Group g) {

		for (User u : userList) {
			if (u.getGroup().equals(g)) {
				u.setGroup(null);
			}
		}
	}

	public static List<User> getUsers() {
		return userList;
	}

	public static List<String> getNickNames() {
		List<String> ret = new ArrayList<>();
		for (User user : userList) {
			ret.add(user.getNick());
		}
		return ret;
	}
}
