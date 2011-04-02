/**
 * SheepCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.Random;
import org.bukkit.ChatColor;
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

public class SheepCommand implements CommandExecutor {
	
	public boolean raveMode;
	public SheepColorPlugin plugin;
	public PluginDescriptionFile pdf;
	public SheepColorSettings scs;
	
	/**
	 * Constructor
	 * @param plugin - parent plugin
	 * @param raveMode - set ravemode
	 */
	public SheepCommand(SheepColorPlugin plugin, SheepColorSettings scs, boolean raveMode) {
		this.plugin = plugin;
		this.scs = scs;
		this.raveMode = raveMode;
		pdf = plugin.getDescription();
	}

	/**
	 * Run when either '/colorsheep' or '/ravesheep' is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int count;
		Player player;
		LivingEntity entity;
		Random rand = new Random();
		DyeColor sheepColor = DyeColor.BLACK;
		World world = plugin.getServer().getWorlds().get(0);
		if(!raveMode && args.length != 2)
			return false;
		else if(raveMode && args.length != 1)
			return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");
			return true;
		}
		player = (Player) sender;
		if(scs.isOpOnly() && !player.isOp()) {
			player.sendMessage(ChatColor.RED + "Player is not OP.");
			return true;
		}
		try {
			if((!raveMode && (Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > scs.getMaxSheep())) ||
					(raveMode && (Integer.valueOf(args[0]) < 1 || Integer.valueOf(args[0]) > scs.getMaxSheep()))) {
				player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
				return true;
			}
			if(!raveMode && ((sheepColor = isColor(args[0])) == null)) {
				player.sendMessage(ChatColor.RED + "Invalid color. Try '/colorlist' for a list of valid colors.");
				return true;
			}
			if(!raveMode)
				count = Integer.valueOf(args[1]);
			else
				count = Integer.valueOf(args[0]);
		} catch(NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
			return true;
		}
		for(int i = 0; i < count; i++) {
			entity = world.spawnCreature(player.getLocation(), CreatureType.SHEEP);
			Sheep newSheep = (Sheep) entity;
			if(!raveMode)
				newSheep.setColor(sheepColor);
			else
				newSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
		}
		if(!raveMode)
			player.sendMessage(ChatColor.YELLOW + "" + Integer.valueOf(args[1]) + " " + args[0].toLowerCase() + " sheep spawned.");
		else
			player.sendMessage(ChatColor.YELLOW + "" + Integer.valueOf(args[0]) + " " + " random sheep spawned.");
		return true;
    }

	/**
	 * Returns a DyeColor if it exists, null otherwise
	 * @param color - dyecolor name
	 * @return a DyeColor object, or null
	 */
	public DyeColor isColor(String color) {
		for(DyeColor col : DyeColor.values())
			if(col.toString().equals(color.toUpperCase()))
				return col;
		return null;
	}
}
