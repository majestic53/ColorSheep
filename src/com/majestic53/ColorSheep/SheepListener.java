/**
 * SheepListener.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class SheepListener extends EntityListener {

	public ColorSheep plugin;
	public static ArrayList<Sheep> spawnedSheep = new ArrayList<Sheep>();
	
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
		if((event.getEntity() instanceof Sheep) && plugin.scs.isSpawnRandom() && plugin.scs.worldList.contains(event.getLocation().getWorld().getName())) {
			// add this sheep to the list of sheep
			spawnedSheep.add((Sheep) event.getEntity());
			
			// use the scheduler to set the sheep color just after it spawns
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    public void run() {
			    	while(spawnedSheep.size() > 0){
			    		Sheep sheep = spawnedSheep.get(0);
			    		sheep.setColor(DyeColor.values()[(new Random()).nextInt(DyeColor.values().length)]);
			    		spawnedSheep.remove(0);
			    	}
			    }
			}, 0);
		}
	}
}
