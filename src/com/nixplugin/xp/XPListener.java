package com.nixplugin.xp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class XPListener implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		e.setDroppedExp(0);
	}

	@EventHandler
	public void onPlayerFish(PlayerFishEvent e) {
		e.setExpToDrop(0);
	}

}
