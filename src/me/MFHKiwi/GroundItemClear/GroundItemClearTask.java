package me.MFHKiwi.GroundItemClear;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class GroundItemClearTask implements Runnable  {
	private final GroundItemClear plugin; // Provide access to main class.
	private final ChatColor colour1; // Create colours.
	private final ChatColor colour2;

	public GroundItemClearTask(GroundItemClear plugin, ChatColor colour1, ChatColor colour2) { /* Class constructor
			to allow passing arguments without making run() invalid to Runnable.*/
		this.plugin = plugin; // Initialise variables.
		this.colour1 = colour1;
		this.colour2 = colour2;
	}
	
	public static final List<Entity> getItemsInWorld(World world) { // Method to collect a list of all items in the given world.
		List<Entity> items = new ArrayList<Entity>();
		for (Entity entity : world.getEntities()) { // Pass through list given by world.getEntities().
			if (entity instanceof Item) { // Check if entity is org.bukkit.entity.Item.
				items.add(entity); // If so, add to item list.
			} else;
		}
		return items; // Return item list.
	}
	
	public void run() {
		int item_count = 0;
		for (World world : Bukkit.getServer().getWorlds()) { // Go through all worlds.
			item_count = item_count + getItemsInWorld(world).size(); // Save size of item list.
			for (Entity entity : getItemsInWorld(world)) { // Remove every entity in the list.
				entity.remove();
			}
		}
		Bukkit.getServer().broadcastMessage(colour2 + "Removed " + colour1 + item_count + colour2 + " items."); /* Announce in
			server chat and console the amount of items removed.*/
		plugin.log.info("Removed " + item_count + " items.");
	}
}
