/**
 * SheepCommand.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.List;
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
	
	public int cmd;
	public SheepColorPlugin plugin;
	public PluginDescriptionFile pdf;
	public SheepColorSettings scs;
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param scs a SheepColorSettings representing a current stat of the plugin
	 * @param command an integer representing a particular command to execute
	 */
	public SheepCommand(SheepColorPlugin plugin, SheepColorSettings scs, int command) {
		this.plugin = plugin;
		this.scs = scs;
		this.cmd = command;
		pdf = plugin.getDescription();
	}

	/**
	 * Run when a command is invoked by a player
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		LivingEntity entity;
		Random rand = new Random();
		DyeColor sheepColor = DyeColor.BLACK;
		World world = plugin.getServer().getWorlds().get(0);
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to use this command.");
			return true;
		}
		player = (Player) sender;
		if(scs.isOpOnly() && !player.isOp()) {
			player.sendMessage(ChatColor.RED + "Player is not OP.");
			return true;
		}
		switch(cmd) {
		case 0: // colorsheep
			if(args.length != 2)
				return false;
			try {
				if(Integer.valueOf(args[1]) < 1 || Integer.valueOf(args[1]) > scs.getMaxSheep())
					throw new NumberFormatException();
				if((sheepColor = isColor(args[0])) == null) {
					player.sendMessage(ChatColor.RED + "Invalid color. Try '/colorlist' for a list of valid colors.");
					return true;
				}
				boolean temp = scs.isSpawnRaveSheep();
				if(temp)
					scs.SpawnRaveSheep = !scs.SpawnRaveSheep;
				for(int i = 0; i < Integer.valueOf(args[1]); i++) {
					entity = world.spawnCreature(player.getLocation(), CreatureType.SHEEP);
					Sheep newSheep = (Sheep) entity;
					newSheep.setColor(sheepColor);
					
				}
				if(temp)
					scs.SpawnRaveSheep = temp;
			} catch(NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
				return true;
			}
			player.sendMessage(ChatColor.YELLOW + "" + Integer.valueOf(args[1]) + " " + args[0].toLowerCase() + " sheep spawned.");
			break;
		case 1: // ravesheep
			if(args.length != 1)
				return false;
			try {
				if(Integer.valueOf(args[0]) < 1 || Integer.valueOf(args[0]) > scs.getMaxSheep())
					throw new NumberFormatException();
				boolean temp = scs.isSpawnRaveSheep();
				if(temp)
					scs.SpawnRaveSheep = !scs.SpawnRaveSheep;
				for(int i = 0; i < Integer.valueOf(args[0]); i++) {
					entity = world.spawnCreature(player.getLocation(), CreatureType.SHEEP);
					Sheep newSheep = (Sheep) entity;
					newSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
					
				}
				if(temp)
					scs.SpawnRaveSheep = temp;
			} catch(NumberFormatException e) {
				player.sendMessage(ChatColor.RED + "Invalid number of sheep (1 - " + scs.getMaxSheep() + ").");
				return true;
			}
			player.sendMessage(ChatColor.YELLOW + "" + Integer.valueOf(args[0]) + " random sheep spawned.");
			break;
		case 2: //killsheep
			if(args.length != 0)
				return false;
			List<LivingEntity> entities = plugin.getServer().getWorlds().get(0).getLivingEntities();
			for(int i = 0; i < entities.size(); i++)
				if(entities.get(i) instanceof Sheep)
					entities.get(i).setHealth(0);
			break;
		}
		return true;
    }

	/**
	 * Returns a DyeColor corrisponding to a string
	 * @param color a string representing a dye color
	 * @return an associated dye color, or null if a dye color does not exist
	 */
	public DyeColor isColor(String color) {
		for(DyeColor col : DyeColor.values())
			if(col.toString().equals(color.toUpperCase()))
				return col;
		return null;
	}
}
