package xyz.juno.chatsystem.main.antispam;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import xyz.juno.chatsystem.main.ChatSystem;
import xyz.juno.chatsystem.main.core.TaskChainTasks;

public class AntiSpam extends BukkitRunnable {

    private BukkitTask task;
    private String name;
    private int stop;
    private int start;
    
    private static int time;
    
	public AntiSpam(String name, int start, JavaPlugin plugin, boolean async) {
		this.name = name;
		this.start = start;
		
        if (async) {
            this.task = runTaskTimerAsynchronously(plugin, 0, 20);
        } else {
            this.task = runTaskTimer(plugin, 0, 20);
        }
    }
    
    public void performGenerator() {
    	ChatSystem.newChain().async(new TaskChainTasks.GenericTask() {
    		@Override
	        public void runGeneric() {
    			stop = 0;
    			
    			if (start == stop) {
    				ChatSystem.antiSpam.removeInTask(name);
    			} else {
    				start -= 1;
    			}
    			
    			time = start;
    			
    		}
    	}).execute();
	}

    public static int getTime() {
    	return time;
    }
    
    @Override
    public void run() {
    	performGenerator();
	}

    public BukkitTask getBukkitTask() {
        return task;
    }
}
