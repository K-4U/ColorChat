package k4unl.minecraft.colorchat;

import k4unl.minecraft.colorchat.commands.Commands;
import k4unl.minecraft.colorchat.events.EventHelper;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.k4lib.lib.config.ConfigHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

@Mod(
        modid = ModInfo.ID,
        name = ModInfo.NAME,
        version = ModInfo.VERSION,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:k4lib"
)
public class ColorChat {

    private ConfigHandler CCConfigHandler = new ConfigHandler();

    @Mod.Instance(value = ModInfo.ID)
    public static ColorChat instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        Log.init();
        CCConfig.INSTANCE.init();
        CCConfigHandler.init(CCConfig.INSTANCE, event.getSuggestedConfigurationFile());
        Users.init();
        Groups.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {

        EventHelper.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {

        Commands.init(event);
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {

        Users.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
        Groups.readFromFile(DimensionManager.getCurrentSaveRootDirectory());
    }
}
