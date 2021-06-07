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
	public final Logger log = Logger.getLogger("Minecraft");
	public ChatColor colour1;
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
	}};
	
	public void onEnable() { // Plugin entry point
		File configFile = new File(this.getDataFolder() + File.separator + "config.yml");
		Configuration config = new Configuration(configFile);
		if (!configFile.exists()) {
			if (!this.getDataFolder().exists()) {
				this.getDataFolder().mkdirs();
			} else;
			config.setHeader("# ClearInterval: Time in seconds between item clear passes. Numbers below 10 are interpreted as 10.\n"
					+ "# Colour1 and Colour2: Bukkit colour codes to set the colours of in-game messages from GIC."); /*
					Put default settings in config.*/
			config.setProperty("ClearInterval", Integer.valueOf(300));
			config.setProperty("Colour1", "&c");
			config.setProperty("Colour2", "&3");
			config.save();
		} else config.load();
		this.colour1 = colours.get(config.getProperty("Colour1"));
		this.colour2 = colours.get(config.getProperty("Colour2"));
		int tick_interval = ((Integer)config.getProperty("ClearInterval")) * 20;
		if (tick_interval < 400) tick_interval = 400;
		getCommand("gic").setExecutor(new GroundItemClearCommand(this, colour1, colour2));
		getServer().getScheduler().scheduleSyncRepeatingTask((Plugin) this, new GroundItemClearWaitTask(this, this.colour1, this.colour2, tick_interval, 400), 100, 1);
		log.info('[' + getDescription().getFullName() + "] enabled.");
	}
	
	public void onDisable() {
		log.info('[' + getDescription().getFullName() + "] disabled.");
	}
}
