/**
 * SheepListener.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.plugin.java.JavaPlugin;

public class SheepListener extends EntityListener {

	public JavaPlugin plugin;
	public SheepColorSettings scs;
	
	/**
	 * Constructor
	 * @param plugin
	 * @param scs
	 */
	public SheepListener(JavaPlugin plugin, SheepColorSettings scs) {
		this.plugin = plugin;
		this.scs = scs;
	}
	
	/**
	 * Run when a creature spawns
	 */
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Random rand = new Random();
		if((event.getEntity() instanceof Sheep) && scs.isSpawnRaveSheep()) {
			Sheep spawnSheep = (Sheep) event.getEntity();
			spawnSheep.setColor(DyeColor.values()[rand.nextInt(DyeColor.values().length)]);
		}
	}
}
