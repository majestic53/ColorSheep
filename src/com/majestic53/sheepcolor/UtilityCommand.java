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

public class UtilityCommand implements CommandExecutor {

	public int cmd;
	public SheepColorPlugin plugin;
	public SheepColorSettings scs;
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param scs a SheepColorSettings representing a current stat of the plugin
	 * @param command an integer representing a particular command to execute
	 */
	public UtilityCommand(SheepColorPlugin plugin, SheepColorSettings scs, int command) {
		this.plugin = plugin;
		this.scs = scs;
		this.cmd = command;
	}

	/**
	 * Run when a command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(cmd) {
		case 0: // colorlist
			sender.sendMessage(ChatColor.BLUE + "COLORS:");
			for(DyeColor col : DyeColor.values())
				sender.sendMessage(ChatColor.YELLOW + col.toString().toLowerCase());
			break;
		case 1: // sheepstatus
			sender.sendMessage(ChatColor.BLUE + "STATUS:");
			sender.sendMessage(ChatColor.YELLOW + "Spawn cap: " + scs.getMaxSheep());
			sender.sendMessage(ChatColor.YELLOW + "Op protection: " + scs.isOpOnly());
			sender.sendMessage(ChatColor.YELLOW + "Spawn colored sheep: " + scs.isSpawnRaveSheep());
			break;
		}
		return true;
    }
}