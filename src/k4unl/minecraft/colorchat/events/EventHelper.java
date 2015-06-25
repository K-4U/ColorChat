package k4unl.minecraft.colorchat.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.List;


public class EventHelper {
	public static void init(){
		MinecraftForge.EVENT_BUS.register(new EventHelper());
		FMLCommonHandler.instance().bus().register(new EventHelper());
	}
	
	@SubscribeEvent
	public void chatEvent(ServerChatEvent event){
		//Log.info("We got a chat message: " + event.username + ":" + event.message);
		if(!CCConfig.INSTANCE.getBool("changeDisplayName")) {
			ChatComponentTranslation orig = event.component.createCopy();
			List siblings = orig.getSiblings();

			User usr = Users.getUserByName(event.username);

			String userName = "<";
			if (usr.getGroup() != null) {
				userName += usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
			}
			if (usr.hasNick()) {
				userName += usr.getColor().toString();

				userName += CCConfig.INSTANCE.getChar("leadingSymbolOnNick");

				userName += usr.getNick() + "";
			} else {
				userName += usr.getColor().toString() + "" + usr.getUserName() + "";
			}
			String chatMessage = EnumChatFormatting.RESET + "> " + event.message.replaceAll("%", "%%");
			String textMessage = userName + chatMessage;

			event.component = new ChatComponentTranslation(textMessage);

			for (Object s : siblings) {
				event.component.appendSibling((IChatComponent) s);
			}
		}
	}

    @SubscribeEvent
    public void getDisplayNameEvent(PlayerEvent.NameFormat event){
		if(CCConfig.INSTANCE.getBool("changeDisplayName")) {
			User usr = Users.getUserByName(event.entityPlayer.getGameProfile().getName());
			String displayName = "";
			if (usr.getGroup() != null) {
				displayName += usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
			}
			if (usr.hasNick()) {
				displayName += usr.getColor().toString();

				displayName += CCConfig.INSTANCE.getChar("leadingSymbolOnNick");

				displayName += usr.getNick() + "";
			} else {
				displayName += usr.getColor().toString() + "" + usr.getUserName() + "";
			}

			event.displayname = displayName;
		}
    }
}
