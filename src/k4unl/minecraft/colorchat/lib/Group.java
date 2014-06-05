package k4unl.minecraft.colorchat.lib;


public class Group {
	private String groupName;
	private SpecialChars groupColor;
	
	public Group(String _groupName, SpecialChars _groupColor){
		groupName = _groupName;
		groupColor = _groupColor;
	}
	
	public Group(String _groupName){
		groupName = _groupName;
		groupColor = SpecialChars.getRandom();
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
}
