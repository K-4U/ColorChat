package k4unl.minecraft.colorchat;

import k4unl.minecraft.colorchat.commands.Commands;
import k4unl.minecraft.colorchat.events.EventHelper;
import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import k4unl.minecraft.colorchat.lib.config.ModInfo;
import k4unl.minecraft.colorchat.worlddata.ColourChatData;
import k4unl.minecraft.k4lib.commands.CommandsRegistry;
import k4unl.minecraft.k4lib.lib.config.Config;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModInfo.ID)
public class ColorChat {

	public static ColorChat instance;

	public ColorChat() {
		Log.init();
		Config config = new CCConfig();
		config.load(ModInfo.ID);

		Users.init();

		EventHelper.init();

		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		EventHelper.init();
		MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
	}

	@SubscribeEvent
	public void onServerStart(FMLServerStartedEvent event) {

		ColourChatData colourChatData = ColourChatData.get(event.getServer().getWorld(DimensionType.OVERWORLD));

		boolean b = event.getServer() instanceof DedicatedServer;
		CommandsRegistry commandsRegistry = new Commands(b, event.getServer().getCommandManager().getDispatcher());
	}
}
