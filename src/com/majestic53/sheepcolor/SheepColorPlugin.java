/**
 * SheepColorPlugin.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SheepColorPlugin extends JavaPlugin {
	
	public static boolean OpOnly = true;

	public void onDisable() {

		PluginDescriptionFile pdf = this.getDescription();
		this.getServer().broadcastMessage("[" + pdf.getName() + "]" + " version " + pdf.getVersion() + " is disabled.");
		System.out.println("[" + pdf.getName() + "]" + " version " + pdf.getVersion() + " is disabled.");
	}

	public void onEnable() {
		
		getCommand("colorsheep").setExecutor(new ColorSheepCommand(this));
		getCommand("ravesheep").setExecutor(new RaveSheepCommand(this));
		getCommand("colorlist").setExecutor(new ColorListCommand(this));
		
		PluginDescriptionFile pdf = this.getDescription();
		this.getServer().broadcastMessage("[" + pdf.getName() + "]" + " version " + pdf.getVersion() + " is enabled.");
		System.out.println("[" + pdf.getName() + "]" + " version " + pdf.getVersion() + " is enabled.");
	}

}
