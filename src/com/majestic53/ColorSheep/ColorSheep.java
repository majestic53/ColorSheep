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
import org.bukkit.plugin.Plugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class ColorSheep extends JavaPlugin {

	public SheepSettings scs;
	public PluginDescriptionFile pdf;
	public SheepListener sheepListener;
	public PluginManager pluginManager;
	public Logger log = Logger.getLogger("minecraft");
	public PermissionHandler permissions;
	public boolean permissionsEnabled = false;
	
	/**
	 * Run when a plugin is disabled
	 */
	public void onDisable() {
		scs.writeConfig();
		pdf = this.getDescription();
		log.info("[" + pdf.getName() + "] version " + pdf.getVersion() + " is disabled.");
	}

	/**
	 * Run when a plugin is enabled
	 */
	public void onEnable() {
		if(!getDataFolder().isDirectory())
			getDataFolder().mkdir();
		scs = new SheepSettings(getDataFolder().getAbsolutePath());
		scs.readConfig();
		sheepListener = new SheepListener(this);
		pluginManager = getServer().getPluginManager();
		pluginManager.registerEvent(Event.Type.CREATURE_SPAWN, sheepListener, Priority.Normal, this);
		pdf = this.getDescription();
		getCommand("sheep").setExecutor(new SheepCommand(this));
		getCommand("sheepset").setExecutor(new SheepSetCommand(this));
		log.info("[" + pdf.getName() + "] version " + pdf.getVersion() + " is enabled.");
		setupPermissions();
	}
	
	/**
	 * Setup permissions
	 */
	private void setupPermissions() {
		Plugin perm = this.getServer().getPluginManager().getPlugin("Permissions");
		if(permissions == null) {
			if(perm != null) {
				permissions = ((Permissions)perm).getHandler();
				permissionsEnabled = true;
				log.info("[" + pdf.getName() + "] Permissions enabled.");
			} else
				log.info("[" + pdf.getName() + "] Permission system not detected.");
		}
	}
}
