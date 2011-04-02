/**
 * UtilityCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilityCommand implements CommandExecutor {

	public SheepColorPlugin plugin;
	
	/**
	 * Constructor
	 * @param plugin - parent plugin
	 */
	public UtilityCommand(SheepColorPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Run when '/colorlist' command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");
			return true;
		}
		player = (Player) sender;
		for(DyeColor col : DyeColor.values())
			player.sendMessage(ChatColor.YELLOW + col.toString().toLowerCase());
		return true;
    }
}