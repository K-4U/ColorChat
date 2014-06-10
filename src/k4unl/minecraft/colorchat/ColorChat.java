package k4unl.minecraft.colorchat;

import k4unl.minecraft.colorchat.commands.Commands;
import k4unl.minecraft.colorchat.events.EventHelper;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

//TODO: color server messages too

@Mod(
	modid = ModInfo.ID,
	name = ModInfo.NAME,
	version = ModInfo.VERSION,
	acceptableRemoteVersions="*"
)


public class ColorChat {
	@Instance(value=ModInfo.ID)
	public static ColorChat instance;

	/*
	@SidedProxy(
			//clientSide = ModInfo.PROXY_LOCATION + ".ClientProxy",
			serverSide = ModInfo.PROXY_LOCATION + ".CommonProxy"
	)
	public static CommonProxy proxy;
	*/
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Log.init();
		Users.init();
		Groups.init();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event){		
		//proxy.init();
		//NetworkRegistry.
		EventHelper.init();
	}
	
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		Commands.init(event);
	}
}
