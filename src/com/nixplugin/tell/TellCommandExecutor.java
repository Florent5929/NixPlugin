package com.nixplugin.tell;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TellCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Commande joueur uniquement !");
		} else if (args.length < 2) {
			sender.sendMessage("Usage : /tell <joueur> <message>");
		} else {
			Player player = (Player) sender;
			Player friend = Bukkit.getPlayer(args[0]);
			
			if(friend == null){
				player.sendMessage(args[0] + " n'a pas été trouvé parmi les joueurs connectés.");
				return false;
			} else {
				String message = new String("");
				for(int i = 1; i < args.length; i++){
					message = new String(message + (i == 1 ? "" : " ") + args[i]);
				}
				message = new String ("\"" + message + "\"");
				
				player.sendMessage("Vous dites à " + (friend.getCustomName() == null ? friend.getName() : friend.getCustomName()) + " : " + message);
				friend.sendMessage((player.getCustomName() == null ? player.getName() : player.getCustomName()) + " vous dit : " + message);
				
				for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()){
					if(onlinePlayer.isOp() && !onlinePlayer.getName().equals(player.getName()) && !onlinePlayer.getName().equals(friend.getName())){
						onlinePlayer.sendMessage((player.getCustomName() == null ? player.getName() : player.getCustomName()) + " dit à " + (friend.getCustomName() == null ? friend.getName() : friend.getCustomName()) + " : " + message);
					}
				}
				
				return true;	
			}
			
		}
		
		
		return false;
	}

}
