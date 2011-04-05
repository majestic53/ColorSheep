/**
 * SettingCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingCommand implements CommandExecutor {
	
	public int cmd;
	public SheepColorSettings scs;
	public SheepColorPlugin plugin;
	public Logger log;
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param scs a SheepColorSettings representing a current stat of the plugin
	 * @param log a Logger representing a plugin Logger
	 * @param command an integer representing a particular command to execute
	 */
	public SettingCommand(SheepColorPlugin plugin, SheepColorSettings scs, Logger log, int command) {
		this.cmd = command;
		this.scs = scs;
		this.log = log;
		this.plugin = plugin;
	}

	/**
	 * Run when a command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		switch(cmd) {
		case 0: // opsheep
			if(!(sender instanceof Player)) {
				scs.toggleOpOnly();
				if(scs.isOpOnly())
					log.info("ColorSheep op protection active.");
				else
					log.info("ColorSheep op protection deactive.");
			} else {
				player = (Player) sender;
				if(!player.isOp()) {
					player.sendMessage(ChatColor.RED + "Player is not OP.");
					return true;
				}
				if(args.length != 0)
					return false;
				scs.toggleOpOnly();
				if(scs.isOpOnly())
					player.sendMessage(ChatColor.YELLOW + "ColorSheep op protection active.");
				else
					player.sendMessage(ChatColor.YELLOW + "ColorSheep op protection deactive.");
			}
			break;
		case 1: // maxsheep
			if(!(sender instanceof Player)) {
				if(args.length != 1)
					return false;
				try {
					if(Integer.valueOf(args[0]) < 1 || Integer.valueOf(args[0]) > Integer.MAX_VALUE)
						throw new NumberFormatException();
					scs.setMaxSheep(Integer.valueOf(args[0]));
					log.info("Max spawnable sheep set to " + scs.maxSheep + ".");
				} catch(NumberFormatException e) {
					log.info("Invalid number of sheep.");
					return true;
				}
			} else {
				player = (Player) sender;
				if(!player.isOp()) {
					player.sendMessage(ChatColor.RED + "Player is not OP.");
					return true;
				}
				if(args.length != 1)
					return false;
				try {
					if(Integer.valueOf(args[0]) < 1 || Integer.valueOf(args[0]) > Integer.MAX_VALUE)
						throw new NumberFormatException();
					scs.setMaxSheep(Integer.valueOf(args[0]));
					player.sendMessage(ChatColor.YELLOW + "Max spawnable sheep set to " + scs.maxSheep + ".");
				} catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid number of sheep.");
					return true;
				}
			}
			break;
		case 2: // spawnravesheep
			if(!(sender instanceof Player)) {
				scs.toggleSpawnRaveSheep();
				if(scs.isSpawnRaveSheep())
					log.info("Spawn randomly colored sheep enabled.");
				else
					log.info("Spawn randomly colored sheep disabled.");
			} else {
				player = (Player) sender;
				if(!player.isOp()) {
					player.sendMessage(ChatColor.RED + "Player is not OP.");
					return true;
				}
				if(args.length != 0)
					return false;
				scs.toggleSpawnRaveSheep();
				if(scs.isSpawnRaveSheep())
					player.sendMessage(ChatColor.YELLOW + "Spawn randomly colored sheep enabled.");
				else
					player.sendMessage(ChatColor.YELLOW + "Spawn randomly colored sheep disabled.");
			}
			break;
		}
		scs.writeConfig();
		return true;
    }
}
