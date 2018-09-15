package com.nixplugin.eclair;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import net.md_5.bungee.api.ChatColor;

public class EclairCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Commande joueur uniquement !");
		} else if (args.length > 1){
			sender.sendMessage("Usage : /eclair ou /eclair <joueur>"); 
		} else {
			Player player = (Player) sender;
			if(player.isOp() || player.hasPermission("nixplugin.eclair")){
				if(args.length == 1){
				 	ExecutePlayerCommand(player, args[0]);
				} else {
					ExecuteSimpleCommand(player);
				}
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de lancer un éclair !");
			}
		}
		return false;
	}
	
	public void ExecuteSimpleCommand(Player player){
		World world = player.getWorld();
		Location loc = player.getEyeLocation();
		Vector vec = loc.getDirection();
		loc.add(vec.multiply(4.0d));
		world.strikeLightning(loc);
	}
	
	public void ExecutePlayerCommand(Player player, String targetName){
		Player target = Bukkit.getPlayerExact(targetName);
		if(target == null){
			player.sendMessage(ChatColor.RED + "Le joueur " + targetName + " n'a pas été trouvé dans la liste des joueurs connectés.");
		} else {
			World world = target.getWorld();
			world.strikeLightning(target.getLocation());
			player.sendMessage(ChatColor.DARK_GREEN + "Vous avez envoyé un éclair sur " + targetName + ".");
		}
	}

}
