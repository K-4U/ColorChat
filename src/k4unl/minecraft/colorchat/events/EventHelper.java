package k4unl.minecraft.colorchat.events;

import k4unl.minecraft.colorchat.lib.Colours;
import k4unl.minecraft.colorchat.lib.Groups;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.CCConfig;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.util.List;


public class EventHelper {

    public static void init() {

        MinecraftForge.EVENT_BUS.register(new EventHelper());
    }

    @SubscribeEvent
    public void chatEvent(ServerChatEvent event) {
        //Log.info("We got a chat message: " + event.username + ":" + event.message);
        if (!CCConfig.INSTANCE.getBool("changeDisplayName")) {
            TextComponentTranslation orig = (TextComponentTranslation) event.getComponent().createCopy();
            List siblings = orig.getSiblings();

            User usr = Users.getUserByName(event.getUsername());

            String userName = "<";
            if (usr.getGroup() != null) {
                userName += usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
            }
            if (CCConfig.INSTANCE.getInt("mode") == 4) {
                userName += usr.isOpped() ? CCConfig.INSTANCE.getString("opColor") : CCConfig.INSTANCE.getString("playerColor");
            } else {
                userName += usr.getColor().toString();
            }
            if (usr.hasNick()) {
                userName += CCConfig.INSTANCE.getChar("leadingSymbolOnNick");
                userName += usr.getNick();
            } else {
                userName += usr.getUserName();
            }
            String chatMessage = TextFormatting.RESET + "> " + event.getMessage().replaceAll("%", "%%");
            String textMessage = userName + chatMessage;

            TextComponentTranslation end = new TextComponentTranslation(textMessage);

            for (Object s : siblings) {
                end.appendSibling((ITextComponent) s);
            }

            event.setComponent(end);
        }

    }

    @SubscribeEvent
    public void getDisplayNameEvent(PlayerEvent.NameFormat event) {

        if (CCConfig.INSTANCE.getBool("changeDisplayName")) {
            User usr = Users.getUserByName(event.getEntityPlayer().getGameProfile().getName());
            String displayName = "";
            if (usr.getGroup() != null) {
                displayName += usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
            }
            if (CCConfig.INSTANCE.getInt("mode") == 4) {
                displayName += usr.isOpped() ? CCConfig.INSTANCE.getString("opColor") : CCConfig.INSTANCE.getString("playerColor");
            } else {
                displayName += usr.getColor().toString();
            }
            if (usr.hasNick()) {
                displayName += CCConfig.INSTANCE.getChar("leadingSymbolOnNick");
                displayName += usr.getNick();
            } else {
                displayName += usr.getUserName();
            }
            event.setDisplayname(displayName);
        }
    }

    @SubscribeEvent
    public void saveEvent(Save event) {

        Users.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
        Groups.saveToFile(DimensionManager.getCurrentSaveRootDirectory());
    }

    @SubscribeEvent
    public void playerLoggedIn(PlayerLoggedInEvent event) {

        if (CCConfig.INSTANCE.getInt("mode") == 1) {
            User user = Users.getUserByName(event.player.getName());
            TextFormatting colour = Colours.getRandomColour();
            user.setUserColor(colour);
        }
    }
}
