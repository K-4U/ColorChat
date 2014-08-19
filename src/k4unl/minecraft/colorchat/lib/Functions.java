package k4unl.minecraft.colorchat.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class Functions {

	public static void showMessageInChat(EntityPlayer player, String message){
		player.addChatMessage(new ChatComponentText(message));
	}

    public static void sendChatMessageServerWide(World world, ChatComponentText message){
        for (EntityPlayer player : (List<EntityPlayer>) world.playerEntities) {
            player.addChatMessage(message);
        }
    }
	
}
