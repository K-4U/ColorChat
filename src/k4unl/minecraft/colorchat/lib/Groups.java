package k4unl.minecraft.colorchat.lib;

import java.util.ArrayList;
import java.util.List;

public class Groups {
	private static List<Group> groupList;
	
	public static void init(){
		groupList = new ArrayList<Group>();
	}
	
	public static Group getGroupByName(String name){
		for(Group g : groupList){
			if(g.getName().equals(name)){
				return g;
			}
		}
		return null;
	}
	
	public static Group createNewGroup(String name){
		Group g = getGroupByName(name);
		if(g == null){
			g = new Group(name);
			groupList.add(g);
		}
		return g;
	}
	
	public static boolean setGroupColor(String name, SpecialChars newColor){
		Group g = getGroupByName(name);
		if(g == null){
			return false;
		}else{
			g.setColor(newColor);
			return true;
		}
		
	}
}
