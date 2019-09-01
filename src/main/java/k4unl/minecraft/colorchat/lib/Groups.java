package k4unl.minecraft.colorchat.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.minecraft.util.text.TextFormatting;

public class Groups {

	private static List<Group> groupList = new ArrayList<>();

	public static Group getGroupByName(String name) {

		for (Group g : groupList) {
			if (g.getName().equals(name)) {
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

	public static void readFromFile(File dir) {

		groupList.clear();
		if (dir != null) {
			Gson gson = new Gson();
			String p = dir.getAbsolutePath();
			p += "/colors.groups.json";
			File f = new File(p);
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				FileInputStream ipStream = new FileInputStream(f);
				InputStreamReader reader = new InputStreamReader(ipStream);
				BufferedReader bReader = new BufferedReader(reader);
				String json = bReader.readLine();
				reader.close();
				ipStream.close();
				bReader.close();

				Type myTypeMap = new TypeToken<List<Group>>() {
				}.getType();
				groupList = gson.fromJson(json, myTypeMap);
				if (groupList == null) {
					groupList = new ArrayList<Group>();
				}

				//Log.info("Read from file: " + json);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}


		}
	}

	public static void saveToFile(File dir) {

		if (dir != null) {
			Gson gson = new Gson();
			String json = gson.toJson(groupList);
			//Log.info("Saving: " + json);
			String p = dir.getAbsolutePath();
			p += "/colors.groups.json";
			File f = new File(p);
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				PrintWriter opStream = new PrintWriter(f);
				opStream.write(json);
				opStream.flush();
				opStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

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
