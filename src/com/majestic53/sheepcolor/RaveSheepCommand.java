/**
 * RaveSheepCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.Random;
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

public class RaveSheepCommand implements CommandExecutor {

	public static int maxSheep = 100;
	
	public SheepColorPlugin plugin;
	public PluginDescriptionFile pdf;
	
	public RaveSheepCommand(SheepColorPlugin plugin) {
		this.plugin = plugin;
		pdf = plugin.getDescription();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		Player player;
		LivingEntity entity;
		Random rand = new Random();
		DyeColor possibleColors[] = DyeColor.values();
		World world = plugin.getServer().getWorlds().get(0);
		
		if(args.length != 1 || !(sender instanceof Player))
			return false;
		player = (Player) sender;
		if(SheepColorPlugin.OpOnly && !player.isOp()) {
			player.sendMessage("[" + pdf.getName() + "]: " + "Player is not OP.");
			return true;
		}
		if(Integer.valueOf(args[0]) < 1 || Integer.valueOf(args[0]) > maxSheep) {
			player.sendMessage("[" + pdf.getName() + "]: " + "Invalid number of sheep (1 - " + maxSheep + ").");
			return true;
		}
		
		for(int i = 0; i < Integer.valueOf(args[0]); i++) {
			entity = world.spawnCreature(player.getLocation(), CreatureType.SHEEP);
			Sheep newSheep = (Sheep) entity;
			newSheep.setColor(possibleColors[rand.nextInt(possibleColors.length)]);
		}
		
		player.sendMessage("[" + pdf.getName() + "]: " + Integer.valueOf(args[0]) + " random sheep spawned.");
		return true;
    }
}
