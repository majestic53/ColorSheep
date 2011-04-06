/**
 * SheepSettings.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class SheepSettings {

	private static final int DefaultMaxSheep = 100;
	private static final boolean DefaultOpOnly = true;
	private static final boolean DefaultSpawnRandom = false;
	private static final String ConfigFile = "/colorsheep.settings";
	
	private int maxSheep;
	private boolean opOnly;
	private boolean spawnRandom;
	private String dataFolder;
	private Properties properties = new Properties();
	
	/**
	 * Constructor
	 * @param settings a string representing a config file directory
	 */
	public SheepSettings(String dataFolder) {
		this.dataFolder = dataFolder;
		File file = new File(dataFolder + ConfigFile);
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
			FileInputStream reader = new FileInputStream(dataFolder + ConfigFile);
			properties.load(reader);
			reader.close();
			maxSheep = Integer.valueOf(properties.getProperty("maxsheep"));
			opOnly = Boolean.valueOf(properties.getProperty("oponly"));
			spawnRandom = Boolean.valueOf(properties.getProperty("spawnrandom"));
			
		} catch(Exception e) {
			defaultConfig();
		}
	}
	
	/**
	 * Write to a config file
	 */
	public void writeConfig() {
		try {
			FileOutputStream writer = new FileOutputStream(dataFolder + ConfigFile);
			properties = new Properties();
			properties.setProperty("maxsheep", String.valueOf(maxSheep));
			properties.setProperty("oponly", String.valueOf(opOnly));
			properties.setProperty("spawnrandom", String.valueOf(spawnRandom));
			properties.store(writer, "ColorSheep");
			writer.close();
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * Sets to a default configuration
	 */
	private void defaultConfig() {
		maxSheep = DefaultMaxSheep;
		opOnly = DefaultOpOnly;
		spawnRandom = DefaultSpawnRandom;
		properties.setProperty("maxsheep", String.valueOf(DefaultMaxSheep));
		properties.setProperty("oponly", String.valueOf(DefaultOpOnly));
		properties.setProperty("spawnrandom", String.valueOf(DefaultSpawnRandom));
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
		return opOnly;
	}
	
	/**
	 * Returns a spawn random setting
	 * @return a boolean representing a spawn ravesheep setting
	 */
	public boolean isSpawnRandom() {
		return spawnRandom;
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
		opOnly = !opOnly;
	}
	
	/**
	 * Toggles a spawn random setting
	 */
	public void toggleSpawnRandom() {
		spawnRandom = !spawnRandom;
	}
}