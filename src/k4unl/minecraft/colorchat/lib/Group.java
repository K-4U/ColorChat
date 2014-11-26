package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.commands.CommandColor;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.k4lib.lib.SpecialChars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {

	private String       groupName;
	private SpecialChars groupColor;

	public Group(String _groupName, SpecialChars _groupColor) {

		groupName = _groupName;
		groupColor = _groupColor;
	}

	public Group(String _groupName) {

		groupName = _groupName;
		List<String> keysAsArray = new ArrayList<String>(CommandColor.colors.keySet());
		String newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
        while(CCConfig.INSTANCE.isColorBlackListed(newClr)){
            newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
        }

        groupColor = CommandColor.colors.get(newClr);
	}

	public String getName() {
		return groupName;
	}
	
	public SpecialChars getColor(){
		return groupColor;
	}

	public void setColor(SpecialChars newColor) {
		groupColor = newColor;
	}

	public void updateUsers(){
		for(User u: Users.getUsersByGroup(this)){
			if(u.getPlayerEntity() != null){
				u.getPlayerEntity().refreshDisplayName();
			}
		}
	}
}
