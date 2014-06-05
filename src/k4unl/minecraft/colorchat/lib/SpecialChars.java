
package k4unl.minecraft.colorchat.lib;

import java.util.Random;



public enum SpecialChars {
	BLACK("0"),DBLUE("1"),DGREEN("2"),DAQUA("3"),DRED("4"),DPURPLE("5"),GOLD("6"),GRAY("7"),DGRAY("8"),BLUE("9"),GREEN("a"),AQUA("b"),RED("c"),LPURPLE("d"),YELLOW("e"),WHITE("f"),RESET("r");
	
	
	public static final SpecialChars[] VALID_COLORS = {BLACK, DBLUE, DGREEN, DAQUA, DRED, DPURPLE, GOLD, GRAY, DGRAY, BLUE, GREEN, AQUA, RED, LPURPLE, YELLOW, WHITE};
	private static final String MCStyle  = "\u00A7";
	private final String color;
	
	private SpecialChars(String num){
		this.color = MCStyle + num;
	}
	
	public static SpecialChars getRandom(){
		return VALID_COLORS[new Random().nextInt(VALID_COLORS.length)];
	}
	
	@Override
	public String toString(){
		return this.color;
	}
}
