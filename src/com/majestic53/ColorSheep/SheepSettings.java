/**
 * SheepSettings.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class SheepSettings {

	public static final int defaultMaxSheep = 100;
	public static final boolean defaultOpOnly = true;
	public static final boolean defaultSpawnRandom = false;
	public static final String config = "/colorsheep.settings";
	
	public int maxSheep;
	public boolean OpOnly;
	public boolean SpawnRandom;
	public String settings;
	public String dataFolder;
	public Logger log;
	public JavaPlugin plugin;
	public Properties properties = new Properties();
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param settings a string representing a config file directory
	 * @param log a Logger representing a plugin Logger
	 */
	public SheepSettings(JavaPlugin plugin, String dataFolder, Logger log) {
		this.plugin = plugin;
		this.log = log;
		this.dataFolder = dataFolder;
		File file = new File(dataFolder + config);
		if(file.exists())
			readConfig();
		else {
			defaultConfig();
			writeConfig();
		}
	}
	
	/**
	 * Read from a config file
	 */
	public void readConfig() {
		try {
			FileInputStream reader = new FileInputStream(dataFolder + config);
			properties.load(reader);
			reader.close();
			maxSheep = Integer.valueOf(properties.getProperty("maxsheep"));
			OpOnly = Boolean.valueOf(properties.getProperty("oponly"));
			SpawnRandom = Boolean.valueOf(properties.getProperty("spawnrandom"));
			
		} catch(Exception e) {
			defaultConfig();
		}
	}
	
	/**
	 * Write to a config file
	 */
	public void writeConfig() {
		try {
			FileOutputStream writer = new FileOutputStream(dataFolder + config);
			properties = new Properties();
			properties.setProperty("maxsheep", String.valueOf(maxSheep));
			properties.setProperty("oponly", String.valueOf(OpOnly));
			properties.setProperty("spawnrandom", String.valueOf(SpawnRandom));
			properties.store(writer, "ColorSheep");
			writer.close();
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * Sets to a default configuration
	 */
	public void defaultConfig() {
		maxSheep = defaultMaxSheep;
		OpOnly = defaultOpOnly;
		SpawnRandom = defaultSpawnRandom;
		properties.setProperty("maxsheep", String.valueOf(defaultMaxSheep));
		properties.setProperty("oponly", String.valueOf(defaultOpOnly));
		properties.setProperty("spawnrandom", String.valueOf(defaultSpawnRandom));
	}
	
	/**
	 * Returns a max sheep spawn cap
	 * @return max an integer representing a max sheep spawn cap
	 */
	public int getMaxSheep() {
		return maxSheep;
	}
	
	/**
	 * Returns a Op only requirement
	 * @return a boolean representing an Op only requirement
	 */
	public boolean isOpOnly() {
		return OpOnly;
	}
	
	/**
	 * Returns a spawn random setting
	 * @return a boolean representing a spawn ravesheep setting
	 */
	public boolean isSpawnRandom() {
		return SpawnRandom;
	}
	
	/**
	 * Sets a max sheep spawn cap
	 * @param max a max sheep spawn cap
	 */
	public void setMaxSheep(int max) {
		maxSheep = max;
	}
	
	/**
	 * Toggles an Op only requirement
	 */
	public void toggleOpOnly() {
		OpOnly = !OpOnly;
	}
	
	/**
	 * Toggles a spawn random setting
	 */
	public void toggleSpawnRandom() {
		SpawnRandom = !SpawnRandom;
	}
}