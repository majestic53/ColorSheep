/**
 * SheepColorPlugin.java
 * @author majestic53
 */

package com.majestic53.sheepcolor;

import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SheepColorPlugin extends JavaPlugin {
	
	public PluginDescriptionFile pdf;
	public Logger log = Logger.getLogger("minecraft");
	public SheepColorSettings scs;
	
	/**
	 * Run when plugin is disabled
	 */
	public void onDisable() {
		scs.writeConfig();
		pdf = this.getDescription();
		log.info(pdf.getName() + " version " + pdf.getVersion() + " is disabled.");
	}

	/**
	 * Run when plugin is enabled
	 */
	public void onEnable() {
		if(!getDataFolder().isDirectory())
			getDataFolder().mkdir();
		scs = new SheepColorSettings(this, getDataFolder().getAbsolutePath(), log);
		setupCommands();
		scs.readConfig();
		pdf = this.getDescription();
		log.info(pdf.getName() + " version " + pdf.getVersion() + " is enabled.");
	}
	
	/**
	 * Setup all available commands
	 */
	public void setupCommands() {
		getCommand("colorsheep").setExecutor(new SheepCommand(this, scs, false));
		getCommand("ravesheep").setExecutor(new SheepCommand(this, scs, true));
		getCommand("colorlist").setExecutor(new UtilityCommand(this));
		getCommand("opsheep").setExecutor(new SettingCommand(this, scs, log, 0));
		getCommand("maxsheep").setExecutor(new SettingCommand(this, scs, log, 1));
	}
}
