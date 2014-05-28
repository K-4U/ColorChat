package k4unl.minecraft.colorchat;

import k4unl.minecraft.colorchat.events.EventHelper;
import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.colorchat.network.PacketPipeline;
import k4unl.minecraft.colorchat.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



@Mod(
	modid = ModInfo.ID,
	name = ModInfo.NAME,
	version = ModInfo.VERSION
)


public class ColorChat {
	@Instance(value=ModInfo.ID)
	public static ColorChat instance;

	
	@SidedProxy(
			//clientSide = ModInfo.PROXY_LOCATION + ".ClientProxy",
			serverSide = ModInfo.PROXY_LOCATION + ".CommonProxy"
	)
	public static CommonProxy proxy;
	
	@EventHandler
	@SideOnly(Side.SERVER)
	public void preInit(FMLPreInitializationEvent event){
		Log.init();
	}
	
	@EventHandler
	@SideOnly(Side.SERVER)
	public void load(FMLInitializationEvent event){
		
		PacketPipeline.init();
		
		proxy.init();
		
		EventHelper.init();
	}
	
	
	@EventHandler
	@SideOnly(Side.SERVER)
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
