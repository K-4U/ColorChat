package k4unl.minecraft.colorchat;

import k4unl.minecraft.colorchat.lib.config.ConfigHandler;
import net.minecraftforge.common.DimensionManager;
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
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(
	modid = ModInfo.ID,
	name = ModInfo.NAME,
	version = ModInfo.VERSION,
	acceptableRemoteVersions="*"
)


public class ColorChat {
	@Instance(value=ModInfo.ID)
	public static ColorChat instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Log.init();
        ConfigHandler.init(event.getSuggestedConfigurationFile());
		Users.init();
		Groups.init();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event){		
		EventHelper.init();
	}
	
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		Commands.init(event);
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent event){
		Users.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
		Groups.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
	}
	
	@EventHandler
	public void serverStop(FMLServerStoppingEvent event){
		Users.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
		Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());		
	}
}
