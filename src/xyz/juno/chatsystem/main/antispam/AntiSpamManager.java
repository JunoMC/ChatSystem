package xyz.juno.chatsystem.main.antispam;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiSpamManager {
	private ConcurrentHashMap<String, AntiSpam> cooldown = new ConcurrentHashMap<>();
    private JavaPlugin plugin;

    public AntiSpamManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addTask(String name, int time) {
    	cooldown.put(name, new AntiSpam(name, time, plugin, true));
    }

    public void removeTask(String name) {
    	cooldown.remove(name).getBukkitTask().cancel();
    }
    
    public void removeInTask(String name) {
		Iterator<Entry<String, AntiSpam>> iterator = cooldown.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, AntiSpam> next = iterator.next();
			String str = next.getKey();
			if (str.matches(str)) {
				cooldown.remove(str).getBukkitTask().cancel();
			}
		}
    }
    
    public ConcurrentHashMap<String, AntiSpam> getHashMap() {
    	return cooldown;
    }
}
