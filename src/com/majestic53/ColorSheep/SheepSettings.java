/**
 * SheepSettings.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Arrays;
import java.util.Properties;

public class SheepSettings {

	public static final int DEFMAXSHEEP = 100;
	public static final boolean DEFSPAWNRAND = false;
	public static final String CONFIGFILE = "/colorsheep.settings";
	
	public List<String> worldList = Arrays.asList("");
	public int maxSheep;
	public boolean spawnRandom;
	public String dataFolder;
	public Properties properties = new Properties();
	
	/**
	 * Constructor
	 */
	public SheepSettings(String dataFolder) {
		this.dataFolder = dataFolder;
		File file = new File(dataFolder + CONFIGFILE);
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
			FileInputStream reader = new FileInputStream(dataFolder + CONFIGFILE);
			properties.load(reader);
			reader.close();
			maxSheep = Integer.valueOf(properties.getProperty("maxsheep"));
			spawnRandom = Boolean.valueOf(properties.getProperty("spawnrandom"));
			
			String s = properties.getProperty("enabledworlds").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
			if(s.length() > 0)
				worldList = Arrays.asList(s.split(","));
			else
				worldList = Arrays.asList("");
			
		} catch(Exception e) {
			System.out.print(e.getMessage());
			defaultConfig();
		}
	}
	
	/**
	 * Write to a config file
	 */
	public void writeConfig() {
		try {
			FileOutputStream writer = new FileOutputStream(dataFolder + CONFIGFILE);
			properties = new Properties();
			properties.setProperty("enabledworlds", worldList.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", ""));
			properties.setProperty("maxsheep", String.valueOf(maxSheep));
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
		worldList = Arrays.asList("");
		maxSheep = DEFMAXSHEEP;
		spawnRandom = DEFSPAWNRAND;

		properties.setProperty("enabledworlds", worldList.toString());
		properties.setProperty("maxsheep", String.valueOf(DEFMAXSHEEP));
		properties.setProperty("spawnrandom", String.valueOf(DEFSPAWNRAND));
	}
	
	/**
	 * Returns a max sheep spawn cap
	 */
	public int getMaxSheep() {
		return maxSheep;
	}
	
	/**
	 * Returns a spawn random setting
	 */
	public boolean isSpawnRandom() {
		return spawnRandom;
	}
	
	/**
	 * Sets a max sheep spawn cap
	 */
	public void setMaxSheep(int max) {
		maxSheep = max;
	}
	
	/**
	 * Toggles a spawn random setting
	 */
	public void toggleSpawnRandom() {
		spawnRandom = !spawnRandom;
	}
}