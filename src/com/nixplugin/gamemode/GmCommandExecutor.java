package com.nixplugin.gamemode;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class GmCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Commande joueur uniquement !");
		} else if (args.length != 1){
			sender.sendMessage("Usage : /gm <0|1|2|3>"); 
		} else {
			Player player = (Player) sender;
			int gamemode = 0;
			try {
				gamemode = Integer.parseInt(args[0]);
			} catch (Exception e){
				sender.sendMessage(ChatColor.RED + args[0] + " n'est pas un nombre valide !");
				return false;
			}
			
			if(gamemode < 0 || gamemode > 3){
				sender.sendMessage("Usage : /gm <0|1|2|3>"); 
			} else {
				GameMode gm = getGamemode(gamemode);
				if(gm != null && hasGmPermission(player, gm)){
					player.setGameMode(gm);
				} else {
					player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de vous mettre dans ce gamemode.");
				}
			}
		}
		
		return false;
	}
	
	public GameMode getGamemode(int id){
		switch(id){
		case 0:
			return GameMode.SURVIVAL;
		case 1:
			return GameMode.CREATIVE;
		case 2:
			return GameMode.ADVENTURE;
		case 3:
			return GameMode.SPECTATOR;
		default:
			return null;
		}
	}
	
	public boolean hasGmPermission(Player player, GameMode gm){
		if(player.isOp()){
			return true;
		} else if(gm.equals(GameMode.SURVIVAL) && player.hasPermission("gamemode.survival")) {
			return true;
		} else if(gm.equals(GameMode.CREATIVE) && player.hasPermission("gamemode.creative")) {
			return true;
		} else if(gm.equals(GameMode.ADVENTURE) && player.hasPermission("gamemode.adventure")) {
			return true;
		} else if(gm.equals(GameMode.SPECTATOR) && player.hasPermission("gamemode.spectator")) {
			return true;
		} else {
			return false;
		}
	}

}
