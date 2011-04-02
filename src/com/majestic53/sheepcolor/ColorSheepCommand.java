/**
 * ColorSheepCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.plugin.PluginDescriptionFile;

public class ColorSheepCommand implements CommandExecutor {

	public static int maxSheep = 100;
	
	public SheepColorPlugin plugin;
	public PluginDescriptionFile pdf;
	
	public ColorSheepCommand(SheepColorPlugin plugin) {
		this.plugin = plugin;
		pdf = plugin.getDescription();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		Player player;
		DyeColor sheepColor;
		LivingEntity entity;
		World world = plugin.getServer().getWorlds().get(0);
		
		if(args.length != 2 || !(sender instanceof Player))
			return false;
		player = (Player) sender;
		if(SheepColorPlugin.OpOnly && !player.isOp()) {
			player.sendMessage("[" + pdf.getName() + "]: " + "Player is not OP.");
			return true;
		}
		if(Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > maxSheep) {
			player.sendMessage("[" + pdf.getName() + "]: " + "Invalid number of sheep (1 - " + maxSheep + ").");
			return true;
		}
		
		if((sheepColor = isColor(args[0])) == null) {
			player.sendMessage("[" + pdf.getName() + "]: " + "Invalid color. Try '/colorlist' for a list of valid colors.");
			return true;
		}
		
		for(int i = 0; i < Integer.valueOf(args[1]); i++) {
			entity = world.spawnCreature(player.getLocation(), CreatureType.SHEEP);
			Sheep newSheep = (Sheep) entity;
			newSheep.setColor(sheepColor);
		}
		
		player.sendMessage("[" + pdf.getName() + "]: " + Integer.valueOf(args[1]) + " " + args[0].toLowerCase() + " sheep spawned.");
		return true;
    }
	
	public DyeColor isColor(String color) {

		DyeColor possibleColors[] = DyeColor.values();
	
		// iterate through the colors
		for(DyeColor col : possibleColors) {
			if(col.toString().equals(color.toUpperCase()))
				return col;
		}
		return null;
	}
}
