package xyz.juno.chatsystem.main.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import xyz.juno.chatsystem.main.ChatSystem;
import xyz.juno.chatsystem.main.antispam.AntiSpam;
import xyz.juno.chatsystem.main.cmds.CmdsInterface.CmdsAPI;
import xyz.juno.chatsystem.main.settings.SettingsInterface.Settings;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		Player PLAYER = e.getPlayer();
		
		/*****************************/
		Settings WAIT_TO_CHAT = Settings.WAIT_TO_CHAT;
		/*****************************/
		
		String WAIT = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(WAIT_TO_CHAT.getPath()));
		
		if (ChatSystem.antiSpam.getHashMap().containsKey(CmdsAPI.sender(PLAYER).toPlayer().getName())) {
			e.setCancelled(true);
			CmdsAPI.sender(PLAYER).send(WAIT
					.replaceAll("(\\%time%)", String.valueOf(AntiSpam.getTime()))
					, WAIT_TO_CHAT.isPrefix());
			return;
		} else {
			ChatSystem.antiSpam.addTask(CmdsAPI.sender(PLAYER).toPlayer().getName(), ChatSystem.ChatSystemInstance().getConfig().getInt("cooldown-to-chat"));
			return;
		}
	}
}