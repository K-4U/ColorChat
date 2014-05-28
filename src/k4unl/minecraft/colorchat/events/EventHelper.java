package k4unl.minecraft.colorchat.events;

import net.minecraftforge.common.MinecraftForge;

public class EventHelper {

	
	public static void init(){
		MinecraftForge.EVENT_BUS.register(new EventHelper());
	}
}
