package xyz.juno.chatsystem.main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import xyz.juno.chatsystem.main.antispam.AntiSpamManager;
import xyz.juno.chatsystem.main.cmds.CmdsInterface.CmdsAPI;
import xyz.juno.chatsystem.main.core.BukkitTaskChainFactory;
import xyz.juno.chatsystem.main.core.TaskChain;
import xyz.juno.chatsystem.main.core.TaskChainFactory;
import xyz.juno.chatsystem.main.core.TaskChainTasks;
import xyz.juno.chatsystem.main.listener.AsyncPlayerChat;
import xyz.juno.chatsystem.main.settings.SettingsInterface.Settings;
import xyz.juno.chatsystem.textapi.ActionBar;
import xyz.juno.chatsystem.textapi.Title;

public class ChatSystem
	extends JavaPlugin
	implements Listener {
	
	private static ChatSystem chatSystem;
	private static TaskChainFactory taskChainFactory;
	public static AntiSpamManager antiSpam;
	
    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }
    
    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }
	
	@Override
	public void onEnable() {
		chatSystem = this;
		saveDefaultConfig();
		taskChainFactory = BukkitTaskChainFactory.create(this);
		antiSpam = new AntiSpamManager(this);
		autoClear();
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChat(), ChatSystemInstance());
	}
	
	public void autoClear() {
		if (getConfig().getBoolean("auto-clear-chat.enable")) {
			/*************************/
			Settings HADCLEAN = Settings.HAD_CLEAN;
			Settings CLEARALLSUCCESS = Settings.CLEARALL_SUCCESS;
			/*************************/
			
			String HAD_CLEAN = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(HADCLEAN.getPath()));
			
			Bukkit.getScheduler().runTaskTimerAsynchronously(ChatSystemInstance(), () -> {
				newChain().async(new TaskChainTasks.GenericTask() {
					
					@Override
					public void runGeneric() {
						Bukkit.getOnlinePlayers().forEach(p -> {
							for (int i = 0; i <= 150; i++) {
								p.sendMessage("");
							}
							
							new Title(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.title")
									.replaceAll("(\\%byplayer%)", "CONSOLE")
									),
									ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.subtitle")
											.replaceAll("(\\%byplayer%)", "CONSOLE")
											),
									20, 40, 20).send(CmdsAPI.sender(p).toPlayer());
							
							new ActionBar(Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.actionbar")
									.replaceAll("(\\%byplayer%)", "CONSOLE"))
									).send(CmdsAPI.sender(p).toPlayer());
							
							CmdsAPI.sender(p).send(HAD_CLEAN
									.replaceAll("(\\%byplayer%)", "CONSOLE")
									);
						});
						
						CmdsAPI.sender(Bukkit.getConsoleSender()).sendPath(CLEARALLSUCCESS.getPath());
					}
				}).execute();
			}, 0, getConfig().getInt("auto-clear-chat.interval") * 20);
		}	
	}
	
	public static ChatSystem ChatSystemInstance() {
		return chatSystem;
	}
	
	public static String Color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}