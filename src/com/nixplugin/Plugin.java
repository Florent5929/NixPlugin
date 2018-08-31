package com.nixplugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nixplugin.compass.CompassCommandExecutor;
import com.nixplugin.messages.MessagesListener;
import com.nixplugin.playerdeath.PlayerDeathListener;
import com.nixplugin.roll.RollCommandExecutor;
import com.nixplugin.tell.TellCommandExecutor;
import com.nixplugin.xp.XPListener;

/**
 * @author Florent L. (Florens)
 */

public class Plugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		if (!this.getDataFolder().exists()) { 
			 this.getDataFolder().mkdir();
		}
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new MessagesListener(), this);
		pm.registerEvents(new XPListener(), this);
		pm.registerEvents(new PlayerDeathListener(), this);

		this.getCommand("roll").setExecutor(new RollCommandExecutor());
		this.getCommand("tell").setExecutor(new TellCommandExecutor());
		this.getCommand("compass").setExecutor(new CompassCommandExecutor());
		
		try {
			PlayerDeathListener.createDefaultConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return an instance of the Plugin.
	 * @return Plugin
	 */

	public static Plugin plugin() {
		return Plugin.getPlugin(Plugin.class);
	}
	
	/**
	 * Return the configuration file as a File and create it if it not exists.
	 * @param name
	 * @return File
	 */
	
	public static File getFile(String name) {
		name = name + ".yml";
		File file = new File(Plugin.plugin().getDataFolder(), name);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException ex) {
				Log.error(Level.SEVERE, "Could not create file " + name);
			}
		}
		return file;
	}
	
	/**
	 * Return the configuration file as FileConfiguration from a file.
	 * @param file
	 * @return FileConfiguration
	 */
	
	public static FileConfiguration getFileConfig(File file) {
		return YamlConfiguration.loadConfiguration(file);
	}
}
