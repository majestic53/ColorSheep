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

	private static final int MaxDist = 100;
	private static final String CommandList[] = {"rave", "status", "colors", "kill"};
	
	private DyeColor dyeColor;
	private ColorSheep plugin;
	private SheepSettings scs;
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param scs a SheepColorSettings representing a current stat of the plugin
	 */
	public SheepCommand(ColorSheep plugin, SheepSettings scs) {
		this.plugin = plugin;
		this.scs = scs;
	}
	
	/**
	 * Run when a command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) {
			if(args[0].equals(CommandList[1])) {
				sender.sendMessage(ChatColor.YELLOW + "Spawn cap: " + scs.getMaxSheep());
				sender.sendMessage(ChatColor.YELLOW + "Op protection: " + scs.isOpOnly());
				sender.sendMessage(ChatColor.YELLOW + "Spawn colored sheep: " + scs.isSpawnRandom());
				return true;
			} else if(args[0].equals(CommandList[2])) {
				for(DyeColor col : DyeColor.values())
					sender.sendMessage(ChatColor.YELLOW + col.toString().toLowerCase());
				return true;
			}
		}
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");
			return true;
		}
		LivingEntity entity;
		Random rand = new Random();
		Player player = (Player) sender;
		World world = plugin.getServer().getWorlds().get(0);
		if(scs.isOpOnly() && !player.isOp()) {
			player.sendMessage(ChatColor.RED + "Player is not OP.");
			return true;
		}
		if(args.length == 1) {
			if(args[0].equals(CommandList[3])) {
				List<LivingEntity> entities = plugin.getServer().getWorlds().get(0).getLivingEntities();
				for(int i = 0; i < entities.size(); i++)
					if(entities.get(i) instanceof Sheep)
						entities.get(i).setHealth(0);
				player.sendMessage(ChatColor.YELLOW + "Killed all sheep.");
			} else
				return false;
		} else if(args.length == 2) {
			if(args[0].equals(CommandList[0])) {
				try {
					if(Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > scs.getMaxSheep())
						throw new NumberFormatException();
					boolean temp = scs.isSpawnRandom();
					if(temp)
						scs.toggleSpawnRandom();				
					for(int i = 0; i < Integer.valueOf(args[1]); i++) {
						entity = world.spawnCreature(player.getTargetBlock(null, MaxDist).getFace(BlockFace.UP).getLocation(), CreatureType.SHEEP);
						Sheep newSheep = (Sheep) entity;
						newSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
					}
					if(temp && !scs.isSpawnRandom())
						scs.toggleSpawnRandom();
					player.sendMessage(ChatColor.YELLOW + "Spawned " + Integer.valueOf(args[1]) + " sheep of random colors.");
				} catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
					return true;
				}
			} else if((dyeColor = isColor(args[0])) != null) {
				try {
					if(Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > scs.getMaxSheep())
						throw new NumberFormatException();
					boolean temp = scs.isSpawnRandom();
					if(temp)
						scs.toggleSpawnRandom();
					for(int i = 0; i < Integer.valueOf(args[1]); i++) {
						entity = world.spawnCreature(player.getTargetBlock(null, MaxDist).getFace(BlockFace.UP).getLocation(), CreatureType.SHEEP);
						Sheep newSheep = (Sheep) entity;
						newSheep.setColor(dyeColor);
					}
					if(temp && !scs.isSpawnRandom())
						scs.toggleSpawnRandom();
					player.sendMessage(ChatColor.YELLOW + "Spawned " + Integer.valueOf(args[1]) + " sheep of color " + dyeColor.toString().toLowerCase() + ".");
				} catch(NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
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
	 * @param color a string representing a dye color
	 * @return an associated DyeColor, or null if a dye color does not exist
	 */
	private DyeColor isColor(String color) {
		for(DyeColor col : DyeColor.values())
			if(col.toString().equals(color.toUpperCase()))
				return col;
		return null;
	}
}
