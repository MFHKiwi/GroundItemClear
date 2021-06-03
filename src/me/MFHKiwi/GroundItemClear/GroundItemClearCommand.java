package me.MFHKiwi.GroundItemClear;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class GroundItemClearCommand implements CommandExecutor {
	private final GroundItemClear plugin; // Provide access to main class.
	private final ChatColor colour1; // Declare colours.
	private final ChatColor colour2;
	private final String[] help_text = new String[4]; // Declare messages.
	private final String invalid_command;
	private final String no_permission;
	
	public GroundItemClearCommand(GroundItemClear plugin, ChatColor colour1, ChatColor colour2) { /* Class constructor
			to allow passing arguments without making onCommand() invalid to CommandExecutor.*/
		this.plugin = plugin; // Initialise variables.
		this.colour1 = colour1;
		this.colour2 = colour2;
		this.help_text[0] = colour2 + "-=- " + colour1 + "GroundItemClear help" + colour2 + " -=-";
		this.help_text[1] = colour2 + " - " + colour1 + "/gic help" + colour2 + ": Show this help screen";
		this.help_text[2] = colour2 + " - " + colour1 + "/gic info" + colour2 + ": Show plugin info";
		this.help_text[3] = colour2 + " - " + colour1 + "/gic clear" + colour2 + ": Clear all items";
		this.invalid_command = colour1 + "Invalid command! Use /gic help to see the command's usage.";
		this.no_permission = colour1 + "You don't have permission to use this command!";
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(args.length < 1)) { // Check if commands arguments were given.
			if (args[0].equalsIgnoreCase("help")) { // If the argument is 'help', print help text.
				for (int i = 0; i < help_text.length; i++) {
					sender.sendMessage(help_text[i]);
				}
				return true;
			} else if (args[0].equalsIgnoreCase("info")) { // If the argument is 'info', give information about plugin.
				sender.sendMessage(colour1 + plugin.getDescription().getFullName() + colour2 + " by " + "MFHKiwi");
				return true;
			} else if (args[0].equalsIgnoreCase("clear")) { // If the argument is 'clear', schedule clear task to run immediately.
				if (sender.hasPermission("gic.clear") || PermissionsEx.getPermissionManager().has((Player) sender, "gic.clear")) { /* Check if
						sender has permission from either permissions.yml or PermissionsEx.*/
					plugin.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin) plugin, new GroundItemClearTask(plugin, this.colour1, this.colour2));
				} else sender.sendMessage(no_permission); // Otherwise, print lack of permission error.
				return true;
			} else {
				sender.sendMessage(invalid_command); // If no valid argument is given, print invalid command error.
				return true;
			}
		} else {
			sender.sendMessage(invalid_command); // If no argument is given, print invalid command error.
			return true;
		}
	}
}
