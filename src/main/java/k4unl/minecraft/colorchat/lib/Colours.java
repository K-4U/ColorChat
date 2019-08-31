package k4unl.minecraft.colorchat.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Quetzi on 29/06/15.
 */
public class Colours {

	private static Map<String, TextFormatting> colors = new HashMap<String, TextFormatting>();

	static {
		colors.put("black", TextFormatting.BLACK);
		colors.put("darkblue", TextFormatting.DARK_BLUE);
		colors.put("darkgreen", TextFormatting.DARK_GREEN);
		colors.put("darkaqua", TextFormatting.DARK_AQUA);
		colors.put("darkred", TextFormatting.DARK_RED);
		colors.put("darkpurple", TextFormatting.DARK_PURPLE);
		colors.put("gold", TextFormatting.GOLD);
		colors.put("gray", TextFormatting.GRAY);
		colors.put("darkgray", TextFormatting.DARK_GRAY);
		colors.put("blue", TextFormatting.BLUE);
		colors.put("green", TextFormatting.GREEN);
		colors.put("aqua", TextFormatting.AQUA);
		colors.put("red", TextFormatting.RED);
		colors.put("lightpurple", TextFormatting.LIGHT_PURPLE);
		colors.put("yellow", TextFormatting.YELLOW);
		colors.put("white", TextFormatting.WHITE);
	}

	public static TextFormatting getRandomColour() {

		List<String> keysAsArray = new ArrayList<String>(colors.keySet());
		String newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
		while (CCConfig.isColorBlackListed(newClr)) {
			newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
		}
		return colors.get(newClr);
	}

	public static TextFormatting get(String colour) {

		return colors.get(colour);
	}

	public static String getColourList() {

		String colourString = "";
		Iterator ite = colors.keySet().iterator();
		while (ite.hasNext()) {
			String colour = (String) ite.next();
			if (!CCConfig.isColorBlackListed(colour)) {
				colourString += colour;
			}
			if (ite.hasNext()) {
				colourString += ", ";
			} else {
				colourString += ".";
			}
		}
		return colourString;
	}
}
