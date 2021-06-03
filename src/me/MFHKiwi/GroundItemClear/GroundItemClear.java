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
	public final Map<String, ChatColor> colours = new HashMap<String, ChatColor>();
	
	public void onEnable() {
		this.colours.put("&0", ChatColor.BLACK);
		this.colours.put("&1", ChatColor.DARK_BLUE);
		this.colours.put("&2", ChatColor.DARK_GREEN);
		this.colours.put("&3", ChatColor.DARK_AQUA);
		this.colours.put("&4", ChatColor.DARK_RED);
		this.colours.put("&5", ChatColor.DARK_PURPLE);
		this.colours.put("&6", ChatColor.GOLD);
		this.colours.put("&7", ChatColor.GRAY);
		this.colours.put("&8", ChatColor.DARK_GRAY);
		this.colours.put("&9", ChatColor.BLUE);
		this.colours.put("&a", ChatColor.GREEN);
		this.colours.put("&b", ChatColor.AQUA);
		this.colours.put("&c", ChatColor.RED);
		this.colours.put("&d", ChatColor.LIGHT_PURPLE);
		this.colours.put("&e", ChatColor.YELLOW);
		this.colours.put("&f", ChatColor.WHITE);
		File configFile = new File(this.getDataFolder() + File.separator + "config.yml");
		Configuration config = new Configuration(configFile);
		if (!configFile.exists()) {
			if (!this.getDataFolder().exists()) {
				this.getDataFolder().mkdirs();
			} else;
			config.setHeader("# ClearInterval: Time in seconds between item clear passes. Numbers below 10 are interpreted as 10.\n"
					+ "# Colour1 and Colour2: Bukkit colour codes to set the colours of in-game messages from GIC.");
			config.setProperty("ClearInterval", Integer.valueOf(300));
			config.setProperty("Colour1", "&c");
			config.setProperty("Colour2", "&b");
			config.save();
		} else config.load();
		this.colour1 = colours.get(config.getProperty("Colour1"));
		this.colour2 = colours.get(config.getProperty("Colour2"));
		int tick_interval = ((Integer)config.getProperty("ClearInterval")) * 20;
		if (tick_interval < 200) tick_interval = 200;
		getCommand("gic").setExecutor(new GroundItemClearCommand(this, colour1, colour2));
		getServer().getScheduler().scheduleAsyncRepeatingTask((Plugin) this, new GroundItemClearTask(this, this.colour1, this.colour2), tick_interval, tick_interval);
		log.info('[' + getDescription().getFullName() + "] enabled.");
	}
	
	public void onDisable() {
		log.info('[' + getDescription().getFullName() + "] disabled.");
	}
}
