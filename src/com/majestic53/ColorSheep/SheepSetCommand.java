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

	private static final String CommandList[] = {"oponly", "spawnrandom", "max"};
	
	private SheepSettings scs;
	
	/**
	 * Constructor
	 * @param scs a SheepSettings representing a plugins settings
	 */
	public SheepSetCommand(SheepSettings scs) {
		this.scs = scs;
	}
	
	/**
	 * Run when a command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(!player.isOp()) {
				player.sendMessage(ChatColor.RED + "Player is not OP.");
				return true;
			}
		}
		if(args.length == 1) {
			if(args[0].equals(CommandList[0])) {
				scs.toggleOpOnly();
				sender.sendMessage(ChatColor.YELLOW + "Op pretection set to " + scs.isOpOnly());
			} else if(args[0].equals(CommandList[1])) {
				scs.toggleSpawnRandom();
				sender.sendMessage(ChatColor.YELLOW + "Random spawn colored sheep set to " + scs.isSpawnRandom());
			} else
				return false;
		} else if(args.length == 2) {
			if(args[0].equals(CommandList[2])) {
				try {
					scs.setMaxSheep(Integer.valueOf(args[1]));
					sender.sendMessage(ChatColor.YELLOW + "Sheep spawn cap set to " + scs.getMaxSheep());
				} catch(NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
					return true;
				}
			} else
				return false;
		} else 
			return false;
		scs.writeConfig();
		return true;
	}
}
