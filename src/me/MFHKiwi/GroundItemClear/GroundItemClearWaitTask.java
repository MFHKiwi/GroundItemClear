package me.MFHKiwi.GroundItemClear;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class GroundItemClearWaitTask implements Runnable {
	private final GroundItemClear plugin;
	private final ChatColor colour1;
	private final ChatColor colour2;
	private final int interval;
	private final int announce_before;
	private int tick_count;
	
	public GroundItemClearWaitTask(GroundItemClear plugin, ChatColor colour1, ChatColor colour2, int interval, int announce_before) {
		this.plugin = plugin;
		this.colour1 = colour1;
		this.colour2 = colour2;
		this.interval = interval;
		this.announce_before = announce_before;
	}
	
	public void run() {
		if (tick_count == (interval - announce_before)) {
			int announce_before_seconds = announce_before / 20;
			plugin.getServer().broadcastMessage(colour2 + "Clearing all ground items in " + colour1 + announce_before_seconds + colour2 + " seconds.");
			plugin.log.info("Clearing all ground items in " + announce_before_seconds + " seconds.");
		}
		tick_count++;
		if (tick_count >= (interval)) {
			tick_count = 0;
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin) plugin, new GroundItemClearTask(plugin, this.colour1, this.colour2));
		}
	}
}
