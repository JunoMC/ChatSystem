package xyz.juno.chatsystem.main.cmds;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.juno.chatsystem.main.ChatSystem;
import xyz.juno.chatsystem.main.cmds.CmdsInterface.CmdsAPI;
import xyz.juno.chatsystem.main.settings.SettingsInterface.Settings;
import xyz.juno.chatsystem.textapi.ActionBar;
import xyz.juno.chatsystem.textapi.Title;

public class Cmds implements CommandExecutor {

	/*
	 * @author Juno
	 */
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] a) {
		
		/************* ARGUMENTS *************/
		Arguments HELP = Arguments.HELP;
		Arguments RELOAD = Arguments.RELOAD;
		Arguments INFO = Arguments.INFO;
		Arguments CLEARALL = Arguments.CLEARALL;
		Arguments CLEAR = Arguments.CLEAR;
		/*************************************/
		
		/************** SETTINGS **************/
		Settings HELP_MESSAGES = Settings.HELP;
		Settings MUST_BE_PLAYER = Settings.MUST_BE_PLAYER;
		Settings NO_PERMISSION = Settings.NO_PERMISSION;
		Settings RELOAD_SUCCESS = Settings.RELOAD_SUCCESS;
		Settings RELOAD_ERROR = Settings.RELOAD_ERROR;
		Settings CLEARALLSUCCESS = Settings.CLEARALL_SUCCESS;
		Settings HADCLEAN = Settings.HAD_CLEAN;
		Settings CLEAR_SUCCESS = Settings.CLEAR_SUCCESS;
		Settings PLAYER_IS_NOT_EXIST = Settings.PLAYER_IS_NOT_EXIST;
		/**************************************/
		
		if (CmdsAPI.sender(sender).isLength(a, 0)) {
			if (CmdsAPI.sender(sender).isPlayer()) {
				if (CmdsAPI.sender(sender).isHas(HELP.getPermission())) {					
					for (String help : ChatSystem.ChatSystemInstance().getConfig().getStringList(HELP_MESSAGES.getPath())) {
						CmdsAPI.sender(sender).send(ChatSystem.Color(help.replace("{lenh}", label)));
					}
				} else {
					CmdsAPI.sender(sender).sendPath(NO_PERMISSION.getPath());
				}
			} else {
				for (String help : ChatSystem.ChatSystemInstance().getConfig().getStringList(HELP_MESSAGES.getPath())) {
					CmdsAPI.sender(sender).send(ChatSystem.Color(help.replace("{lenh}", label)));
				}
			}
		}
		
		if (CmdsAPI.sender(sender).isLength(a, 1)) {
			if (CmdsAPI.sender(sender).isArgumentSimilar(a[0], HELP.getArgument())) {
				if (CmdsAPI.sender(sender).isPlayer()) {
					if (CmdsAPI.sender(sender).isHas(HELP.getPermission())) {					
						for (String help : ChatSystem.ChatSystemInstance().getConfig().getStringList(HELP_MESSAGES.getPath())) {
							CmdsAPI.sender(sender).send(ChatSystem.Color(help.replace("{lenh}", label)));
						}
					} else {
						CmdsAPI.sender(sender).sendPath(NO_PERMISSION.getPath());
					}
				} else {
					for (String help : ChatSystem.ChatSystemInstance().getConfig().getStringList(HELP_MESSAGES.getPath())) {
						CmdsAPI.sender(sender).send(ChatSystem.Color(help.replace("{lenh}", label)));
					}
				}
			}
			
			if (CmdsAPI.sender(sender).isArgumentSimilar(a[0], INFO.getArgument())) {
				if (CmdsAPI.sender(sender).isPlayer()) {
					if (CmdsAPI.sender(sender).isHas(INFO.getPermission())) {
						CmdsAPI.sender(sender).send(ChatSystem.Color("&m                     "));
						
						TextComponent author = new TextComponent(ChatSystem.Color("&fAuthor: "));
						
						TextComponent name = new TextComponent(ChatSystem.Color("&eJuno"));
						name.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.facebook.com/profile.php?id=100033827385372"));
			            name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatSystem.Color("&bGo to Juno's facebook")).create()));
						
						ArrayList<TextComponent> array = new ArrayList<TextComponent>();
						array.add(author);
						array.add(name);
						
						TextComponent message = new TextComponent("");
						
						for (TextComponent total : array) {
							message.addExtra(total);
						}
						
						CmdsAPI.sender(sender).toPlayer().spigot().sendMessage(message);
						CmdsAPI.sender(sender).send(ChatSystem.Color("&fVersion: 1.0"));
						
						CmdsAPI.sender(sender).send(ChatSystem.Color("&m                     "));
					} else {
						CmdsAPI.sender(sender).sendPath(NO_PERMISSION.getPath());
					}
				} else {
					CmdsAPI.sender(sender).sendPath(MUST_BE_PLAYER.getPath());
				}
			}
			
			if (CmdsAPI.sender(sender).isArgumentSimilar(a[0], RELOAD.getArgument())) {
				if (CmdsAPI.sender(sender).isPlayer()) {
					if (CmdsAPI.sender(sender).isHas(RELOAD.getPermission())) {
						try {
							ChatSystem.ChatSystemInstance().reloadConfig();
							ChatSystem.ChatSystemInstance().autoClear();
						} catch (Exception ex) {
							CmdsAPI.sender(sender).sendPath(RELOAD_ERROR.getPath());
						} finally {
							CmdsAPI.sender(sender).sendPath(RELOAD_SUCCESS.getPath());
						}
					} else {
						CmdsAPI.sender(sender).sendPath(NO_PERMISSION.getPath());
					}
				} else {
					try {
						ChatSystem.ChatSystemInstance().reloadConfig();
						ChatSystem.ChatSystemInstance().autoClear();
					} catch (Exception ex) {
						CmdsAPI.sender(sender).sendPath(RELOAD_ERROR.getPath());
					} finally {
						CmdsAPI.sender(sender).sendPath(RELOAD_SUCCESS.getPath());
					}
				}
			}
			
			if (CmdsAPI.sender(sender).isArgumentSimilar(a[0], CLEARALL.getArgument())) {
				if (CmdsAPI.sender(sender).isPlayer()) {
					
					String HAD_CLEAN = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(HADCLEAN.getPath()));
					
					if (CmdsAPI.sender(sender).isHas(CLEARALL.getPermission())) {
						Bukkit.getOnlinePlayers().forEach(p -> {
							for (int i = 0; i <= 150; i++) {
								p.sendMessage("");
							}
							
							CmdsAPI.sender(p).send(HAD_CLEAN
									.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
									);
							new Title(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.title")
									.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
									),
									ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.subtitle")
											.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
											),
									20, 40, 20).send(CmdsAPI.sender(p).toPlayer());
							
							new ActionBar(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.actionbar")
									.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName()))
									).send(CmdsAPI.sender(p).toPlayer());
							
						});
						
						CmdsAPI.sender(sender).sendPath(CLEARALLSUCCESS.getPath());
						
						new Title(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("clear-success.title")
								),
								ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("clear-success.subtitle")
										),
								20, 40, 20).send(CmdsAPI.sender(sender).toPlayer());
						
						new ActionBar(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.actionbar"))).send(CmdsAPI.sender(sender).toPlayer());
						
					} else {
						CmdsAPI.sender(sender).sendPath(NO_PERMISSION.getPath());
					}
				} else {
					String HAD_CLEAN = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(HADCLEAN.getPath()));
					
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
						
						new ActionBar(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.actionbar")
								.replaceAll("(\\%byplayer%)", "CONSOLE"))
								).send(CmdsAPI.sender(p).toPlayer());
						
						CmdsAPI.sender(p).send(HAD_CLEAN
								.replaceAll("(\\%byplayer%)", "CONSOLE")
								);
					});
					
					CmdsAPI.sender(sender).sendPath(CLEARALLSUCCESS.getPath());
				}
			}
		}
		
		if (CmdsAPI.sender(sender).isMinMaxLength(a, 0, 3)) {
			if (CmdsAPI.sender(sender).isArgumentSimilar(a[0], CLEAR.getArgument())) {
				if (CmdsAPI.sender(sender).isPlayer()) {
					if (CmdsAPI.sender(sender).isHas(CLEAR.getPermission())) {
						String HAD_CLEAN = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(HADCLEAN.getPath()));
						
						if (CmdsAPI.sender(sender).isLength(a, 1)) {
							String CLEARSUCCESS = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(CLEAR_SUCCESS.getPath()));
							
							for (int i = 0; i <= 150; i++) {
								CmdsAPI.sender(sender).send("");
							}
							
							new Title(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.title")
									.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
									),
									ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.subtitle")
											.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
											),
									20, 40, 20).send(CmdsAPI.sender(sender).toPlayer());
							
							new ActionBar(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.actionbar")
									.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName()))
									).send(CmdsAPI.sender(sender).toPlayer());
							
							CmdsAPI.sender(sender).send(CLEARSUCCESS
									.replaceAll("(\\%toplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
									);
						}
						
						if (CmdsAPI.sender(sender).isLength(a, 2)) {
							Player PLAYER = Bukkit.getPlayer(a[1]);
							
							if (PLAYER == null) {
								String NOT_EXIST = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(PLAYER_IS_NOT_EXIST.getPath()));
								
								CmdsAPI.sender(sender).send(NOT_EXIST
										.replaceAll("(\\%player%)", a[1])
										);
							} else {
								String CLEARSUCCESS = ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString(CLEAR_SUCCESS.getPath()));
								
								for (int i = 0; i <= 150; i++) {
									CmdsAPI.sender(PLAYER).send("");
								}
								
								new Title(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.title")
										.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
										),
										ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.subtitle")
												.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
												),
										20, 40, 20).send(CmdsAPI.sender(PLAYER).toPlayer());
								
								new ActionBar(ChatSystem.Color(ChatSystem.ChatSystemInstance().getConfig().getString("had-clean.actionbar")
										.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName()))
										).send(CmdsAPI.sender(PLAYER).toPlayer());
								
								CmdsAPI.sender(PLAYER).send(HAD_CLEAN
										.replaceAll("(\\%byplayer%)", CmdsAPI.sender(sender).toPlayer().getName())
										);
								
								CmdsAPI.sender(sender).send(CLEARSUCCESS
										.replaceAll("(\\%toplayer%)", a[1])
										);
							}
							
						}
						
					} else {
						CmdsAPI.sender(sender).sendPath(NO_PERMISSION.getPath());
					}
				} else {
					CmdsAPI.sender(sender).sendPath(MUST_BE_PLAYER.getPath());
				}
			}
		}
		
		return false;
	}
}