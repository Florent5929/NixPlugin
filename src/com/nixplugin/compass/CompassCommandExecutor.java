package com.nixplugin.compass;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CompassCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Commande joueur uniquement !");
		} else if (args.length != 2) {
			sender.sendMessage("Usage : /compass <x> <z>");
		} else {
			int x, z;
			try {
				x = Integer.parseInt(args[0]);
				z = Integer.parseInt(args[1]);
			} catch (Exception e){
				sender.sendMessage("x et/ou y ne sont pas des nombres entiers !");
				return false;
			}
			Player player = (Player) sender;
			player.setCompassTarget(new Location(player.getWorld(), x, 0, z));
			player.sendMessage("Votre boussole pointe désormais vers une nouvelle position.");
			return true;
		}
		return false;
	}

}
