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

public class SheepListener extends EntityListener {

	public ColorSheep plugin;
	
	/**
	 * Constructor
	 */
	public SheepListener(ColorSheep plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Run when a creature spawns
	 */
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Random rand = new Random();
		if((event.getEntity() instanceof Sheep) && plugin.scs.isSpawnRandom() && plugin.scs.worldList.contains(event.getLocation().getWorld().getName())) {
			Sheep spawnSheep = (Sheep) event.getEntity();
			spawnSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
		}
	}
}
