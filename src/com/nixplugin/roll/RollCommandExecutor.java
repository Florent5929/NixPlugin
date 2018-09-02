package com.nixplugin.roll;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.nixplugin.ressources.Functions;

public class RollCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Commande joueur uniquement !");
		} else if (args.length != 0 && args.length != 1 && args.length != 2) {
			sender.sendMessage("Usage : /roll ou /roll <nombre> ou /roll <nombre> <rayon>");
		} else {
			int number;
			float rayon = 0;

			if (args.length == 0) {
				number = 100;
			} else {
				try {
					number = Integer.parseInt(args[0]);
					rayon = (args.length == 2 ? Float.parseFloat(args[1]) : 0);
				} catch (Exception e) {
					sender.sendMessage(args[0] + " ou le rayon ne sont pas des nombres.");
					return false;
				}
			}

			if (number < 2 || number > 9999) {
				sender.sendMessage("Veuillez renseigner un nombre compris entre 2 et 9999.");
				return false;
			} else {
				int roll = Functions.getRandomIntegerBetween(1, number);
				Player player = (Player) sender;
				String name = (player.getCustomName() != null ? player.getCustomName() : player.getName());

				for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
					if(rayon == 0 || Functions.getIfPlayersAreNear(player, onlinePlayer, rayon)){
					onlinePlayer.sendMessage(name + " a tiré un " + roll + " sur son dé à " + number + " faces.");
					}
				}
				
				return true;
			}

		}

		return false;
	}

}
