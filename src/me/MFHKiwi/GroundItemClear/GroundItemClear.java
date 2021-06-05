package me.MFHKiwi.GroundItemClear;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class GroundItemClear extends JavaPlugin {
	public final Logger log = Logger.getLogger("Minecraft"); // Create logger to log to console.
	public ChatColor colour1; // Create colour variables to pass to other classes.
	public ChatColor colour2;
	@SuppressWarnings("serial")
	public final Map<String, ChatColor> colours = new HashMap<String, ChatColor>() {{
		put("&0", ChatColor.BLACK); // Fill colour map.
		put("&1", ChatColor.DARK_BLUE);
		put("&2", ChatColor.DARK_GREEN);
		put("&3", ChatColor.DARK_AQUA);
		put("&4", ChatColor.DARK_RED);
		put("&5", ChatColor.DARK_PURPLE);
		put("&6", ChatColor.GOLD);
		put("&7", ChatColor.GRAY);
		put("&8", ChatColor.DARK_GRAY);
		put("&9", ChatColor.BLUE);
		put("&a", ChatColor.GREEN);
		put("&b", ChatColor.AQUA);
		put("&c", ChatColor.RED);
		put("&d", ChatColor.LIGHT_PURPLE);
		put("&e", ChatColor.YELLOW);
		put("&f", ChatColor.WHITE);
	}}; // Create colour map to allow simple Bukkit chat codes to be used in config.yml
	
	public void onEnable() { // Plugin entry point
		File configFile = new File(this.getDataFolder() + File.separator + "config.yml"); // Create file object to interact with config.yml.
		Configuration config = new Configuration(configFile); // Pass configFile to Bukkit's configuration manager.
		if (!configFile.exists()) { // Check if config and config folder exist.
			if (!this.getDataFolder().exists()) {
				this.getDataFolder().mkdirs(); // If not, create folder and config.
			} else;
			config.setHeader("# ClearInterval: Time in seconds between item clear passes. Numbers below 10 are interpreted as 10.\n"
					+ "# Colour1 and Colour2: Bukkit colour codes to set the colours of in-game messages from GIC."); /*
					Put default settings in config.*/
			config.setProperty("ClearInterval", Integer.valueOf(300));
			config.setProperty("Colour1", "&c");
			config.setProperty("Colour2", "&b");
			config.save(); // Write config.
		} else config.load(); // If config exists, load it.
		this.colour1 = colours.get(config.getProperty("Colour1")); // Load colours from config.
		this.colour2 = colours.get(config.getProperty("Colour2"));
		int tick_interval = ((Integer)config.getProperty("ClearInterval")) * 20; // Load interval (in ticks) from config.
		if (tick_interval < 200) tick_interval = 200; // If interval is lower than 200 ticks (10 seconds), set to 200 ticks.
		getCommand("gic").setExecutor(new GroundItemClearCommand(this, colour1, colour2)); // Register command /gic.
		getServer().getScheduler().scheduleAsyncRepeatingTask((Plugin) this, new GroundItemClearTask(this, this.colour1, this.colour2), tick_interval, tick_interval); /*
			Schedule clear task.*/
		log.info('[' + getDescription().getFullName() + "] enabled."); /* Print load message to console with information from
			plugin.yml*/
	}
	
	public void onDisable() {
		log.info('[' + getDescription().getFullName() + "] disabled."); // Print unload message to console.
	}
}
