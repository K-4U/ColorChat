package k4unl.minecraft.colorchat.events;

import java.util.List;

import k4unl.minecraft.colorchat.lib.SpecialChars;
import k4unl.minecraft.colorchat.lib.User;
import k4unl.minecraft.colorchat.lib.Users;
import k4unl.minecraft.colorchat.lib.config.Config;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventHelper {
	public static void init(){
		MinecraftForge.EVENT_BUS.register(new EventHelper());
		FMLCommonHandler.instance().bus().register(new EventHelper());
	}
	
	@SubscribeEvent
	public void chatEvent(ServerChatEvent event){
		//Log.info("We got a chat message: " + event.username + ":" + event.message);
		
		ChatComponentTranslation orig = event.component.createCopy();
		List siblings = orig.getSiblings();
		
		User usr = Users.getUserByName(event.username);
		
		
		String userName = "<";
		if(usr.getGroup() != null){
			userName+= usr.getGroup().getColor() + "[" + usr.getGroup().getName() + "]";
		}
		if(usr.hasNick()){
			userName+= usr.getColor().toString();

            userName+= Config.getChar("leadingSymbolOnNick");

            userName+= usr.getNick() + "";
		}else{
			userName+= usr.getColor().toString() + "" + usr.getUserName()+ "";
		}
		String chatMessage = SpecialChars.RESET + "> " + event.message.replaceAll("%", "%%");
		String textMessage = userName + chatMessage;
		
		event.component = new ChatComponentTranslation(textMessage);
		
		
		for(Object s : siblings){
			event.component.appendSibling((IChatComponent) s);
		}
	}
}
