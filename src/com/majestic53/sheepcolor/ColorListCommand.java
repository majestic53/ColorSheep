/**
 * ColorListCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ColorListCommand implements CommandExecutor {

	SheepColorPlugin plugin;
	
	public ColorListCommand(SheepColorPlugin plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		Player player;
		DyeColor possibleColors[] = DyeColor.values();
		
		if(!(sender instanceof Player))
			return false;
		
		player = (Player) sender;
		player.sendMessage("Possible sheep colors:");
		for(DyeColor col : possibleColors) {
			player.sendMessage(col.toString());
		}
		return true;
    }
}
