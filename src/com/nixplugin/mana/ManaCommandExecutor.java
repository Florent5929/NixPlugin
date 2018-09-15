package com.nixplugin.mana;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class ManaCommandExecutor implements CommandExecutor {
	
	public final String USAGE = new String("Usages :\n"
			+ "/mana get <joueur>\n"
			+ "/mana set <joueur> <montant>\n"
			+ "/mana add <joueur> <montant>");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if ((sender instanceof Player) && !((Player)sender).isOp()) {
			sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande !");
		} else if (args.length != 2 && args.length != 3) {
			sender.sendMessage(USAGE);
		} else {
			args[0] = args[0].toUpperCase();
			
			switch(args[0]){
			case "GET":
				ExecuteGetCommand(sender, args[1]);
				break;
			case "SET":
				ExecuteSetCommand(sender, args);
				break;
			case "ADD":
				ExecuteAddCommand(sender, args);
				break;
			default:
				sender.sendMessage(USAGE);
				break;
			}
		}
		
		return false;
	}
	
	public void ExecuteGetCommand(CommandSender sender, String playerName){
		Player player = Bukkit.getPlayerExact(playerName);
		if(player == null){
			sender.sendMessage(ChatColor.RED + "Le joueur " + playerName + " n'a pas été trouvé dans la liste des joueurs connectés.");
		} else {
			int lvl = player.getLevel();
			sender.sendMessage(ChatColor.GOLD + "Le nombre de lvl (mana) du joueur " + playerName + " est de " + lvl + ".");
		}
	}
	
	public void ExecuteSetCommand(CommandSender sender, String[] args){
		Player player = Bukkit.getPlayerExact(args[1]);
		if(player == null){
			sender.sendMessage(ChatColor.RED + "Le joueur " + args[1] + " n'a pas été trouvé dans la liste des joueurs connectés.");
		} else if (args.length != 3){
			sender.sendMessage(USAGE);
		} else {
			int oldLvl = player.getLevel();
			try {
				player.setLevel(Integer.parseInt(args[2]));
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Erreur : " + args[2] + " n'est pas un nombre !");
				return;
			}
			sender.sendMessage(ChatColor.DARK_GREEN + "Le nombre de lvl (mana) du joueur " + args[1] + " est passé de "
					+ oldLvl + " à " + args[2] + ".");
		}
	}
	
	public void ExecuteAddCommand(CommandSender sender, String[] args){
		Player player = Bukkit.getPlayerExact(args[1]);
		if(player == null){
			sender.sendMessage(ChatColor.RED + "Le joueur " + args[1] + " n'a pas été trouvé dans la liste des joueurs connectés.");
		} else if (args.length != 3){
			sender.sendMessage(USAGE);
		} else {
			int oldLvl = player.getLevel();
			int newLvl;
			try {
				newLvl = oldLvl + Integer.parseInt(args[2]);
				player.setLevel((newLvl >= 0 ? newLvl : 0));
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Erreur : " + args[2] + " n'est pas un nombre !");
				return;
			}
			sender.sendMessage(ChatColor.DARK_GREEN + "Le nombre de lvl (mana) du joueur " + args[1] + " est passé de "
					+ oldLvl + " à " + newLvl + " (" + (newLvl >= oldLvl ? "+" : "") +args[2] + ").");
		}
	}

}
