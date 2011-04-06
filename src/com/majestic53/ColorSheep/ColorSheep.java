/**
 * ColorSheep.java
 * @author majestic53
 */

package com.majestic53.ColorSheep;

import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ColorSheep extends JavaPlugin {

	public SheepSettings scs;
	public PluginDescriptionFile pdf;
	public SheepListener sheepListener;
	public PluginManager pluginManager;
	public Logger log = Logger.getLogger("minecraft");
	
	/**
	 * Run when a plugin is disabled
	 */
	public void onDisable() {
		scs.writeConfig();
		pdf = this.getDescription();
		log.info(pdf.getName() + " version " + pdf.getVersion() + " is disabled.");
	}

	/**
	 * Run when a plugin is enabled
	 */
	public void onEnable() {
		if(!getDataFolder().isDirectory())
			getDataFolder().mkdir();
		scs = new SheepSettings(this, getDataFolder().getAbsolutePath(), log);
		scs.readConfig();
		sheepListener = new SheepListener(this, scs);
		pluginManager = getServer().getPluginManager();
		pluginManager.registerEvent(Event.Type.CREATURE_SPAWN, sheepListener, Priority.Normal, this);
		getCommand("sheep").setExecutor(new SheepCommand(this, scs));
		getCommand("sheepset").setExecutor(new SheepSetCommand(this, scs, log));
		pdf = this.getDescription();
		log.info(pdf.getName() + " version " + pdf.getVersion() + " is enabled.");
	}
}
