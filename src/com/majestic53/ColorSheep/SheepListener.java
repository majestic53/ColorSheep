/**
 * SheepListener.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SheepListener extends EntityListener {
	
	public JavaPlugin plugin;
	public SheepSettings scs;
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param scs a SheepSettings representing a plugins settings
	 */
	public SheepListener(JavaPlugin plugin, SheepSettings scs) {
		this.plugin = plugin;
		this.scs = scs;
	}
	
	/**
	 * Run when a creature spawns
	 */
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Random rand = new Random();
		if((event.getEntity() instanceof Sheep) && scs.isSpawnRandom()) {
			Sheep spawnSheep = (Sheep) event.getEntity();
			if(rand.nextBoolean())
				spawnSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
		}
	}
}
