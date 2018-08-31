package com.nixplugin.playerdeath;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.nixplugin.Plugin;

public class PlayerDeathListener implements Listener {
	
	public static File file;
	public static FileConfiguration config;
	public static World world;
	public static int x, y, z;

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Location loc = new Location(world, x, y, z);
		e.setRespawnLocation(loc);
	}
	
	public static void createDefaultConfig() throws IOException {
		file = Plugin.getFile("death");
		config = Plugin.getFileConfig(file);
		
		if(file.length() == 0){
			String header = new String("Respawn after death.");
			config.options().header(header);
			config.options().copyHeader(true);	
			config.createSection("world");
			config.createSection("x");
			config.createSection("y");
			config.createSection("z");
			config.set("world", "world");
			config.set("x", 0);
			config.set("y", 64);
			config.set("z", 0);
			config.save(file);
		}
		
		world = Bukkit.getWorld(config.getString("world"));
		x = config.getInt("x");
		y = config.getInt("y");
		z = config.getInt("z");
		
		
	}
	
}
