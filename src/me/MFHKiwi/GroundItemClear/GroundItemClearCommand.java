package me.MFHKiwi.GroundItemClear;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class GroundItemClearCommand implements CommandExecutor {
	private final GroundItemClear plugin;
	private final ChatColor aqua = ChatColor.DARK_AQUA;
	private final ChatColor red = ChatColor.RED;
	private final String[] help_text = {
			aqua + "-=- " + red + "GroundItemClear help" + aqua + " -=-",
			aqua + " - " + red + "/gic help" + aqua + ": Show this help screen",
			aqua + " - " + red + "/gic info" + aqua + ": Show plugin info",
			aqua + " - " + red + "/gic clear" + aqua + ": Clear all items"};
	private final String invalid_command = red + "Invalid command! Use /gic help to see the command's usage.";
	private final String no_permission = red + "You don't have permission to use this command!";
	
	public GroundItemClearCommand(GroundItemClear plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(args.length < 1)) {
			if (args[0].equalsIgnoreCase("help")) {
				for (int i = 0; i < help_text.length; i++) {
					sender.sendMessage(help_text[i]);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(red + plugin.getDescription().getFullName() + aqua + " by " + "MFHKiwi");
				return true;
			} else if (args[0].equalsIgnoreCase("clear")) {
				if (sender.hasPermission("gic.clear") || PermissionsEx.getPermissionManager().has((Player) sender, "gic.clear")) {
					plugin.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin) plugin, new GroundItemClearTask(plugin));
				} else sender.sendMessage(no_permission);
				return true;
			} else {
				sender.sendMessage(invalid_command);
				return true;
			}
		} else {
			sender.sendMessage(invalid_command);
			return true;
		}
	}
}
