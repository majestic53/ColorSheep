/**
 * SheepSetCommand.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SheepSetCommand implements CommandExecutor {

	public static final String COMMANDLIST[] = {"spawnrandom", "max"};
	
	public ColorSheep plugin;
	
	/**
	 * Constructor
	 */
	public SheepSetCommand(ColorSheep plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Run when a command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");
			return true;
		}
		Player player = (Player) sender;
		if(args.length == 1) {
			if(args[0].equals(COMMANDLIST[0])) { // spawnrandom
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.spawnrand")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				plugin.scs.toggleSpawnRandom();
				player.sendMessage(ChatColor.YELLOW + "Random spawn colored sheep set to " + plugin.scs.isSpawnRandom());
			} else
				return false;
		} else if(args.length == 2) {
			if(args[0].equals(COMMANDLIST[1])) { // max:number
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.max")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				try {
					plugin.scs.setMaxSheep(Integer.valueOf(args[1]));
					player.sendMessage(ChatColor.YELLOW + "Sheep spawn cap set to " + plugin.scs.getMaxSheep());
				} catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + plugin.scs.getMaxSheep() + ").");
					return true;
				}
			} else
				return false;
		} else 
			return false;
		plugin.scs.writeConfig();
		return true;
	}
}
