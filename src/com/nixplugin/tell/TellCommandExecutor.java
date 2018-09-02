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
				message = new String ("§9" + message + "§r");
				String playerName = (player.getCustomName() == null ? player.getName() : player.getCustomName());
				String friendName = (friend.getCustomName() == null ? friend.getName() : friend.getCustomName());
				
				player.sendMessage(getTransmissionString(1) + friendName + " : " + message);
				friend.sendMessage(playerName + getTransmissionString(2) + message);
				
				for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()){
					if(onlinePlayer.isOp() && !onlinePlayer.getName().equals(player.getName()) && !onlinePlayer.getName().equals(friend.getName())){
						onlinePlayer.sendMessage(playerName + getTransmissionString(3) + friendName + " : " + message);
					}
				}
				
				return true;	
			}
			
		}
		
		
		return false;
	}
	
	public String getTransmissionString(int id){
		String str = "";
		switch(id){
		case 1:
			str = new String("transmis à ");
			break;
		case 2:
			str = new String(" vous transmet : ");
			break;
		case 3:
			str = new String(" transmet à ");
			break;
		}
		return new String("§8"+str+"§r");
	}

}
