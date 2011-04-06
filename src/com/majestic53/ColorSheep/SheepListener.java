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

	private SheepSettings scs;
	
	/**
	 * Constructor
	 * @param scs a SheepSettings representing a plugins settings
	 */
	public SheepListener(SheepSettings scs) {
		this.scs = scs;
	}
	
	/**
	 * Run when a creature spawns
	 */
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Random rand = new Random();
		if((event.getEntity() instanceof Sheep) && scs.isSpawnRandom()) {
			Sheep spawnSheep = (Sheep) event.getEntity();
			spawnSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
		}
	}
}
