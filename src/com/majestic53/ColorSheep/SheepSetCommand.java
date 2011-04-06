/**
 * SheepSetCommand.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class SheepSetCommand implements CommandExecutor {

	public static final String commandList[] = {"oponly",
												"spawnrandom",
												"max"};
	
	public Logger log;
	public ColorSheep plugin;
	public SheepSettings scs;
	public PluginDescriptionFile pdf;
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param scs a SheepSettings representing a plugins settings
	 * @param log a Logger representing a plugin Logger
	 */
	public SheepSetCommand(ColorSheep plugin, SheepSettings scs, Logger log) {
		this.plugin = plugin;
		this.scs = scs;
		this.log = log;
		pdf = plugin.getDescription();
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
			if(args[0].equals(commandList[0])) {
				scs.toggleOpOnly();
				sender.sendMessage(ChatColor.YELLOW + "Op pretection set to " + scs.isOpOnly());
			} else if(args[0].equals(commandList[1])) {
				scs.toggleSpawnRandom();
				sender.sendMessage(ChatColor.YELLOW + "Random spawn colored sheep set to " + scs.isSpawnRandom());
			} else
				return false;
		} else if(args.length == 2) {
			if(args[0].equals(commandList[2])) {
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
