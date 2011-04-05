/**
 * SheepColorSettings.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class SheepColorSettings {

	public static final String config = "/colorsheep.settings";
	public static final int defaultMaxSheep = 100;
	public static final boolean defaultOpOnly = true;
	public static final boolean defaultSpawnRaveSheep = false;
	
	public int maxSheep;
	public String settings;
	public String dataFolder;
	public boolean OpOnly;
	public boolean SpawnRaveSheep;
	public JavaPlugin plugin;
	public Logger log;
	public Properties properties = new Properties();
	
	/**
	 * Constructor
	 * @param plugin a plugin representing a parent plugin
	 * @param settings a string representing a config file
	 * @param log a Logger representing a plugin Logger
	 */
	public SheepColorSettings(JavaPlugin plugin, String dataFolder, Logger log) {
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
			SpawnRaveSheep = Boolean.valueOf(properties.getProperty("ravesheep"));
			
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
			properties.setProperty("ravesheep", String.valueOf(SpawnRaveSheep));
			properties.store(writer, "SheepColorPluginConfig");
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
		SpawnRaveSheep = defaultSpawnRaveSheep;
		properties.setProperty("maxsheep", String.valueOf(defaultMaxSheep));
		properties.setProperty("oponly", String.valueOf(defaultOpOnly));
		properties.setProperty("ravesheep", String.valueOf(defaultSpawnRaveSheep));
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
	 * Returns a spawn ravesheep setting
	 * @return a boolean representing a spawn ravesheep setting
	 */
	public boolean isSpawnRaveSheep() {
		return SpawnRaveSheep;
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
	 * Toggles a spawn ravesheep setting
	 */
	public void toggleSpawnRaveSheep() {
		SpawnRaveSheep = !SpawnRaveSheep;
	}
}
