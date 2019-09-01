package k4unl.minecraft.colorchat.events;

import java.util.List;

import k4unl.minecraft.colorchat.lib.Colours;
import k4unl.minecraft.colorchat.lib.Log;
import k4unl.minecraft.colorchat.lib.Mode;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class EventHelper {

	public static void init() {

		MinecraftForge.EVENT_BUS.register(new EventHelper());
	}

	@SubscribeEvent
	public void chatEvent(ServerChatEvent event) {
		Log.info("We got a chat message: " + event.getUsername() + ":" + event.getMessage());
		Boolean aBoolean = CCConfig.changeDisplayName.get();
		if (!aBoolean) {
			TranslationTextComponent orig = (TranslationTextComponent) event.getComponent().deepCopy();
			List siblings = orig.getSiblings();

			User usr = Users.getUserByName(event.getUsername());

			String userName = "<";
			if (usr.getGroup() != null) {
				userName += usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
			}
			if (CCConfig.mode.get() == Mode.SET) {
				userName += usr.isOpped() ? CCConfig.opColour.get() : CCConfig.playerColour.get();
			} else {
				userName += usr.getColor().toString();
			}
			if (usr.hasNick()) {
				userName += CCConfig.leadingSymbolOnNick.get();
				userName += usr.getNick();
			} else {
				userName += usr.getUserName();
			}
			String chatMessage = TextFormatting.RESET + "> " + event.getMessage().replaceAll("%", "%%");
			String textMessage = userName + chatMessage;

			TranslationTextComponent end = new TranslationTextComponent(textMessage);

			for (Object s : siblings) {
				end.appendSibling((ITextComponent) s);
			}

			event.setComponent(end);
		}

	}

	@SubscribeEvent
	public void getDisplayNameEvent(PlayerEvent.NameFormat event) {

		if (CCConfig.changeDisplayName.get()) {
			User usr = Users.getUserByName(event.getEntityPlayer().getName().getUnformattedComponentText());
			String displayName = "";
			if (usr.getGroup() != null) {
				displayName += usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
			}
			if (CCConfig.mode.get() == Mode.SET) {
				displayName += usr.isOpped() ? CCConfig.opColour.get() : CCConfig.playerColour.get();
			} else {
				displayName += usr.getColor().toString();
			}
			if (usr.hasNick()) {
				displayName += CCConfig.leadingSymbolOnNick.get();
				displayName += usr.getNick();
			} else {
				displayName += usr.getUserName();
			}
			event.setDisplayname(displayName);
		}
	}

	@SubscribeEvent
	public void saveEvent(Save event) {

		/*Users.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
		Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());*/
	}

	@SubscribeEvent
	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

		if (CCConfig.mode.get() == Mode.RANDOM) {
			User user = Users.getUserByName(event.getPlayer().getName().getUnformattedComponentText());
			TextFormatting colour = Colours.getRandomColour();
			user.setUserColor(colour);
		}
	}
}
