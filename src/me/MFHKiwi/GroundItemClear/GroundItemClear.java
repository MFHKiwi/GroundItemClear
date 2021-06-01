package me.MFHKiwi.GroundItemClear;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class GroundItemClear extends JavaPlugin {
	public final Logger log = Logger.getLogger("Minecraft");
	public void onEnable() {
		File configFile = new File(this.getDataFolder() + File.separator + "config.yml");
		Configuration config = new Configuration(configFile);
		log.info(String.valueOf(this.getDataFolder()));
		if (!configFile.exists()) {
			if (!this.getDataFolder().exists()) {
				this.getDataFolder().mkdirs();
			} else;
			config.setHeader("# ClearInterval: time in seconds between item clear passes. Numbers below 10 are interpreted as 10.");
			config.setProperty("ClearInterval", Integer.valueOf(300));
			config.save();
		} else config.load();
		int tick_interval = ((Integer)config.getProperty("ClearInterval")) * 20;
		if (tick_interval < 200) tick_interval = 200;
		getCommand("gic").setExecutor(new GroundItemClearCommand(this));
		getServer().getScheduler().scheduleAsyncRepeatingTask((Plugin) this, new GroundItemClearTask(this), 200, tick_interval);
		log.info('[' + getDescription().getFullName() + "] enabled.");
	}
	public void onDisable() {
		getServer().getScheduler().cancelAllTasks();
		log.info('[' + getDescription().getFullName() + "] disabled.");
	}
}
