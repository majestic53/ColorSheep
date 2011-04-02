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
	
	public int maxSheep;
	public String settings;
	public String dataFolder;
	public boolean OpOnly;
	public JavaPlugin plugin;
	public Logger log;
	public Properties properties = new Properties();
	
	/**
	 * Constructor
	 * @param settings - config file
	 * @param log - logger
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
	 * Read from the config file
	 */
	public void readConfig() {
		try {
			FileInputStream reader = new FileInputStream(dataFolder + config);
			properties.load(reader);
			reader.close();
			maxSheep = Integer.valueOf(properties.getProperty("maxsheep"));
			OpOnly = Boolean.valueOf(properties.getProperty("oponly"));
		} catch(Exception e) {
			defaultConfig();
		}
	}
	
	/**
	 * Write to the config file
	 */
	public void writeConfig() {
		try {
			FileOutputStream writer = new FileOutputStream(dataFolder + config);
			properties = new Properties();
			properties.setProperty("maxsheep", String.valueOf(maxSheep));
			properties.setProperty("oponly", String.valueOf(OpOnly));
			properties.store(writer, "SheepColorPluginConfig");
			writer.close();
		} catch(Exception e) {
			
		}
	}
	
	public void defaultConfig() {
		maxSheep = defaultMaxSheep;
		OpOnly = defaultOpOnly;
		properties.setProperty("maxsheep", String.valueOf(defaultMaxSheep));
		properties.setProperty("oponly", String.valueOf(defaultOpOnly));
	}
	
	/**
	 * Returns the max sheep allowed to spawn at once
	 * @return - max sheep allowed
	 */
	public int getMaxSheep() {
		return maxSheep;
	}
	
	/**
	 * Returns if the command is only for Op players
	 * @return - true if requires Op
	 */
	public boolean isOpOnly() {
		return OpOnly;
	}
	
	/**
	 * Sets the max sheep allowed to be spawned
	 * @param max - max sheep allowed
	 */
	public void setMaxSheep(int max) {
		maxSheep = max;
	}
	
	/**
	 * Toggles the Op only requirement on/off
	 */
	public void toggleOpOnly() {
		OpOnly = !OpOnly;
	}
}
