/**
 * SheepColorPlugin.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.logging.Logger;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class SheepColorPlugin extends JavaPlugin {
	
	public PluginDescriptionFile pdf;
	public Logger log = Logger.getLogger("minecraft");
	public SheepColorSettings scs;
	public SheepListener sheepListener;
	
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
		scs = new SheepColorSettings(this, getDataFolder().getAbsolutePath(), log);
		scs.readConfig();
		pdf = this.getDescription();
		sheepListener = new SheepListener(this, scs);
		setupListeners();
		setupCommands();
		log.info(pdf.getName() + " version " + pdf.getVersion() + " is enabled.");
	}
	
	/**
	 * Setup all available commands
	 */
	public void setupCommands() {
		getCommand("colorsheep").setExecutor(new SheepCommand(this, scs, 0));
		getCommand("ravesheep").setExecutor(new SheepCommand(this, scs, 1));
		getCommand("killsheep").setExecutor(new SheepCommand(this, scs, 2));
		getCommand("colorlist").setExecutor(new UtilityCommand(this, scs, 0));
		getCommand("sheepstatus").setExecutor(new UtilityCommand(this, scs, 1));
		getCommand("opsheep").setExecutor(new SettingCommand(this, scs, log, 0));
		getCommand("maxsheep").setExecutor(new SettingCommand(this, scs, log, 1));
		getCommand("spawnravesheep").setExecutor(new SettingCommand(this, scs, log, 2));
	}
	
	/**
	 * Setup all listeners
	 */
	public void setupListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.CREATURE_SPAWN, sheepListener, Priority.Normal, this);
	}
}
