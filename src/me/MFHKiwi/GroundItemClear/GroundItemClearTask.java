package me.MFHKiwi.GroundItemClear;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class GroundItemClearTask implements Runnable  {
	private final GroundItemClear plugin;
	private final ChatColor colour1;
	private final ChatColor colour2;

	public GroundItemClearTask(GroundItemClear plugin, ChatColor colour1, ChatColor colour2) {
		this.plugin = plugin;
		this.colour1 = colour1;
		this.colour2 = colour2;
	}
	
	public static final List<Entity> getItemsInWorld(World world) {
		List<Entity> items = new ArrayList<Entity>();
		for (Entity entity : world.getEntities()) {
			if (entity instanceof Item) {
				items.add(entity);
			} else;
		}
		return items;
	}
	
	public void run() {
		int item_count = 0;
		for (World world : Bukkit.getServer().getWorlds()) {
			item_count = item_count + getItemsInWorld(world).size();
			for (Entity entity : getItemsInWorld(world)) {
				entity.remove();
			}
		}
		Bukkit.getServer().broadcastMessage(colour2 + "Removed " + colour1 + item_count + colour2 + " items.");
		plugin.log.info("Removed " + item_count + " items.");
	}
}
