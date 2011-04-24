/**
 * SheepCommand.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

public class SheepCommand implements CommandExecutor {

	public static final int MAXDIST = 100;
	public static final String COMMANDLIST[] = {"rave", "status", "colors", "kill"};
	
	public DyeColor dyeColor;
	public ColorSheep plugin;
	
	/**
	 * Constructor
	 */
	public SheepCommand(ColorSheep plugin) {
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
			if(args[0].equals(COMMANDLIST[1])) { // status
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.status")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				player.sendMessage(ChatColor.YELLOW + "Spawn cap: " + plugin.scs.getMaxSheep());
				player.sendMessage(ChatColor.YELLOW + "Spawn colored sheep: " + plugin.scs.isSpawnRandom());
				return true;
			} else if(args[0].equals(COMMANDLIST[2])) { // colors
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.colors")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				for(DyeColor col : DyeColor.values())
					player.sendMessage(ChatColor.YELLOW + col.toString().toLowerCase());
				return true;
			}
		}
		LivingEntity entity;
		Random rand = new Random();
		World world = plugin.getServer().getWorlds().get(0);
		if(args.length == 1) {
			if(args[0].equals(COMMANDLIST[3])) {
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.kill")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				List<LivingEntity> entities = plugin.getServer().getWorlds().get(0).getLivingEntities();
				for(int i = 0; i < entities.size(); i++)
					if(entities.get(i) instanceof Sheep)
						entities.get(i).setHealth(0);
				player.sendMessage(ChatColor.YELLOW + "Killed all sheep.");
			} else
				return false;
		} else if(args.length == 2) {
			if(args[0].equals(COMMANDLIST[0])) { // rave:number
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.rave")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				try {
					if(Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > plugin.scs.getMaxSheep())
						throw new NumberFormatException();
					boolean temp = plugin.scs.isSpawnRandom();
					if(temp)
						plugin.scs.toggleSpawnRandom();				
					for(int i = 0; i < Integer.valueOf(args[1]); i++) {
						entity = world.spawnCreature(player.getTargetBlock(null, MAXDIST).getFace(BlockFace.UP).getLocation(), CreatureType.SHEEP);
						Sheep newSheep = (Sheep) entity;
						newSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
					}
					if(temp && !plugin.scs.isSpawnRandom())
						plugin.scs.toggleSpawnRandom();
					player.sendMessage(ChatColor.YELLOW + "Spawned " + Integer.valueOf(args[1]) + " sheep of random colors.");
				} catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + plugin.scs.getMaxSheep() + ").");
					return true;
				}
			} else if((dyeColor = isColor(args[0])) != null) { // color:number
				if(plugin.permissionsEnabled)
					if (!(plugin).permissions.has(player, "colorsheep.spawn")) {
						player.sendMessage(ChatColor.RED + "Player does not have proper permission.");
						return true;
					}
				try {
					if(Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > plugin.scs.getMaxSheep())
						throw new NumberFormatException();
					boolean temp = plugin.scs.isSpawnRandom();
					if(temp)
						plugin.scs.toggleSpawnRandom();
					for(int i = 0; i < Integer.valueOf(args[1]); i++) {
						entity = world.spawnCreature(player.getTargetBlock(null, MAXDIST).getFace(BlockFace.UP).getLocation(), CreatureType.SHEEP);
						Sheep newSheep = (Sheep) entity;
						newSheep.setColor(dyeColor);
					}
					if(temp && !plugin.scs.isSpawnRandom())
						plugin.scs.toggleSpawnRandom();
					player.sendMessage(ChatColor.YELLOW + "Spawned " + Integer.valueOf(args[1]) + " sheep of color " + dyeColor.toString().toLowerCase() + ".");
				} catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + plugin.scs.getMaxSheep() + ").");
					return true;
				}
			} else
				return false;
		} else
			return false;
		return true;
	}
	
	/**
	 * Returns a DyeColor corrisponding to a string
	 */
	private DyeColor isColor(String color) {
		for(DyeColor col : DyeColor.values())
			if(col.toString().equals(color.toUpperCase()))
				return col;
		return null;
	}
}
