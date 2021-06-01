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
	private final ChatColor aqua = ChatColor.DARK_AQUA;
	private final ChatColor red = ChatColor.RED;

	public GroundItemClearTask(GroundItemClear plugin) {
		this.plugin = plugin;
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
		Bukkit.getServer().broadcastMessage(aqua + "Removed " + red + item_count + aqua + " items.");
		plugin.log.info("Removed " + item_count + " items.");
	}
}
