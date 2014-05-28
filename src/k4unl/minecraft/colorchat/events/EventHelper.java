package k4unl.minecraft.colorchat.events;

import k4unl.minecraft.colorchat.lib.Log;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHelper {

	
	public static void init(){
		MinecraftForge.EVENT_BUS.register(new EventHelper());
	}
	
	@SubscribeEvent
	public static void chatEvent(ServerChatEvent event){
		Log.info("We got a chat message: " + event.username + ":" + event.message);
		event.setCanceled(true);
	}
}
