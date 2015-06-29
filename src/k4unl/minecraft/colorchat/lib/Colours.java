package k4unl.minecraft.colorchat.lib;

import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

/**
 * Created by Quetzi on 29/06/15.
 */
public class Colours {

    private static Map<String, EnumChatFormatting> colors = new HashMap<String, EnumChatFormatting>();

    static {
        colors.put("black", EnumChatFormatting.BLACK);
        colors.put("darkblue", EnumChatFormatting.DARK_BLUE);
        colors.put("darkgreen", EnumChatFormatting.DARK_GREEN);
        colors.put("darkaqua", EnumChatFormatting.DARK_AQUA);
        colors.put("darkred", EnumChatFormatting.DARK_RED);
        colors.put("darkpurple", EnumChatFormatting.DARK_PURPLE);
        colors.put("gold", EnumChatFormatting.GOLD);
        colors.put("gray", EnumChatFormatting.GRAY);
        colors.put("darkgray", EnumChatFormatting.DARK_GRAY);
        colors.put("blue", EnumChatFormatting.BLUE);
        colors.put("green", EnumChatFormatting.GREEN);
        colors.put("aqua", EnumChatFormatting.AQUA);
        colors.put("red", EnumChatFormatting.RED);
        colors.put("lightpurple", EnumChatFormatting.LIGHT_PURPLE);
        colors.put("yellow", EnumChatFormatting.YELLOW);
        colors.put("white", EnumChatFormatting.WHITE);
    }

    public static EnumChatFormatting getRandomColour() {

        List<String> keysAsArray = new ArrayList<String>(colors.keySet());
        String newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
        while (CCConfig.INSTANCE.isColorBlackListed(newClr)) {
            newClr = keysAsArray.get(new Random().nextInt(keysAsArray.size()));
        }
        return colors.get(newClr);
    }

    public static EnumChatFormatting get(String colour) {

        return colors.get(colour);
    }

    public static String getColourList() {

        String colourString = "";
        Iterator ite = colors.keySet().iterator();
        while (ite.hasNext()) {
            if (!CCConfig.INSTANCE.isColorBlackListed((String)ite.next())) {
                colourString += ite.next();
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
