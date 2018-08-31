package com.nixplugin.ressources;

import java.util.Random;

import org.bukkit.entity.Player;

public class Functions {
	
	public static int getRandomIntegerBetween(int minimum, int maximum) {

		Random r = new Random();
		return (minimum + r.nextInt(maximum + 1 - minimum));
	}
	
	public static boolean getIfPlayersAreNear(Player player, Player cible, Float distance) {

		if (player.getWorld().equals(cible.getWorld())) {

			if (player.getLocation().distance(cible.getLocation()) <= distance) {
				return true;
			}
		}

		return false;
	}

}
