package com.nixplugin.warp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import com.nixplugin.Plugin;
import net.md_5.bungee.api.ChatColor;

public class WarpCommandExecutor implements CommandExecutor {

	public static String[] params = new String[] { "x", "y", "z", "pitch", "yaw", "world" };
	public static Map<String, Warp> warps;
	public static File file;
	public static FileConfiguration config;
	public static String usage = new String(
			"Usages :\n" + "/warp <name>\n" + "/warp list\n" + "/warp set <name>\n" + "/warp del <name>");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		int nbArgs = args.length;

		if (!(sender instanceof Player)) {
			sender.sendMessage("Commande joueur uniquement !");
			return false;
		} else {
			Player player = (Player) sender;

			switch (nbArgs) {
			case 1:
				if (args[0].equals("list")) {
					this.ExecuteListCommand(player);
					return true;
				} else {
					this.ExecuteWarpCommand(player, args[0]);
					return true;
				}
			case 2:
				if (args[0].equals("set")) {
					this.ExecuteSetCommand(player, args[1]);
					return true;
				} else if (args[0].equals("del")) {
					this.ExecuteDelCommand(player, args[1]);
					return true;
				} else {
					player.sendMessage(usage);
					return false;
				}
			default:
				player.sendMessage(usage);
				return false;
			}
		}
	}

	public void ExecuteWarpCommand(Player player, String name) {
		if (this.canUseWarp(player)) {
			this.teleportToWarp(player, name);
		} else {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser les warps.");
		}
	}

	public void ExecuteSetCommand(Player player, String name) {
		if (this.canCreateWarp(player)) {
			this.createWarp(player, name);
		} else {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de créer un warp.");
		}
	}

	public void ExecuteDelCommand(Player player, String name) {
		if (this.canCreateWarp(player)) {
			this.deleteWarp(player, name);
		} else {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de supprimer un warp.");
		}
	}

	public void ExecuteListCommand(Player player) {
		Warp currentWarp;
		if (!this.canUseWarp(player)) {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de lister les warps.");
		} else if (warps.size() > 0) {
			String print = new String("§dVoici la liste des warps existants :§r");
			for (String key : warps.keySet()) {
				currentWarp = warps.get(key);
				print = new String(print + "\n" + currentWarp.toString());
			}
			player.sendMessage(print);
		} else {
			player.sendMessage("§dAucun warp n'est actuellement répertorié.§r");
		}

	}

	public void createWarp(Player player, String name) {
		String regex = new String("[\\\\/:*?\"<>|ÀÁÂÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝàáâãäçèéêëìíîïñòóôõöùúûüýÿ ]");

		if (warps.containsKey(name)) {
			player.sendMessage(ChatColor.RED + "Le warp " + name + " existe déjà.\n" + warps.get(name).toString());
		} else if (name.equals("set") || name.equals("del") || name.equals("list")) {
			player.sendMessage(
					ChatColor.RED + "Vous ne pouvez pas nommer un warp " + name + ", c'est un nom réservé.\n" + usage);
		} else if (!name.replaceAll(regex, "").equals(name)) {
			player.sendMessage(ChatColor.RED + name
					+ " n'est pas un nom valide car les caractères suivants, ainsi que les espaces, sont interdits.\n"
					+ regex);
		} else {
			Location loc = player.getLocation();
			warps.put(name, new Warp(name, player.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(),
					loc.getPitch(), loc.getYaw()));
			player.sendMessage(
					ChatColor.GREEN + "Le warp " + name + " a été crée avec succès.\n" + warps.get(name).toString());
		}
	}

	public void deleteWarp(Player player, String name) {

		if (!warps.containsKey(name)) {
			player.sendMessage(
					ChatColor.RED + "Le warp " + name + " n'existe pas, vous ne pouvez donc pas le supprimer.");
		} else {
			player.sendMessage(ChatColor.GREEN + "Le warp " + name + " a été supprimé avec succès.\n"
					+ warps.get(name).toString());
			warps.remove(name);
		}
	}

	public void teleportToWarp(Player player, String name) {

		if (!warps.containsKey(name)) {
			player.sendMessage(ChatColor.RED + "Le warp " + name + " n'existe pas.");
		} else {
			Warp warp = warps.get(name);
			World world = Bukkit.getWorld(warp.getWorld());
			if (world == null) {
				player.sendMessage(ChatColor.RED + "Warp invalide. Le monde " + warp.getWorld() + " n'existe pas.");
			} else {
				Location loc = new Location(world, warp.getX(), warp.getY(), warp.getZ(), warp.getYaw(),
						warp.getPitch());
				player.teleport(loc);
				player.sendMessage(ChatColor.GREEN + "Vous avez été téléporté avec succès.");
			}
		}
	}

	public boolean canCreateWarp(Player player) {
		if (player.isOp() || player.hasPermission("warps.manage")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canUseWarp(Player player) {
		if (player.isOp() || player.hasPermission("warps.use") || player.hasPermission("warps.manage")) {
			return true;
		} else {
			return false;
		}
	}

	public static void createDefaultConfig() throws IOException {
		file = Plugin.getFile("warps");
		config = Plugin.getFileConfig(file);

		if (file.length() == 0) {
			config.createSection("warps");
			config.save(file);
		}

		warps = new HashMap<String, Warp>();
	}

	public static void loadFromConfig() {
		file = Plugin.getFile("warps");
		config = Plugin.getFileConfig(file);
		Warp currentWarp;
		double x, y, z;
		float pitch, yaw;
		String world;
		ConfigurationSection cs = config.getConfigurationSection("warps");
		for (String key : cs.getKeys(false)) {
			x = config.getDouble("warps." + key + ".x");
			y = config.getDouble("warps." + key + ".y");
			z = config.getDouble("warps." + key + ".z");
			pitch = Float.parseFloat(config.getString("warps." + key + ".pitch"));
			yaw = Float.parseFloat(config.getString("warps." + key + ".yaw"));
			world = config.getString("warps." + key + ".world");
			currentWarp = new Warp(key, world, x, y, z, pitch, yaw);
			warps.put(key, currentWarp);
		}
	}

	public static void saveOnConfig() throws IOException {
		file = Plugin.getFile("warps");
		config = Plugin.getFileConfig(file);
		Warp currentWarp;
		ConfigurationSection cs;
		
		config.set("warps", null);
		config.createSection("warps");

		for (String key : warps.keySet()) {

			cs = config.getConfigurationSection("warps");

			currentWarp = warps.get(key);
			cs.createSection(key);
			cs = cs.getConfigurationSection(key);

			for (String param : params) {
				cs.createSection(param);
			}

			config.set(new String("warps." + key + ".x"), currentWarp.getX());
			config.set(new String("warps." + key + ".y"), currentWarp.getY());
			config.set(new String("warps." + key + ".z"), currentWarp.getZ());
			config.set(new String("warps." + key + ".pitch"), currentWarp.getPitch());
			config.set(new String("warps." + key + ".yaw"), currentWarp.getYaw());
			config.set(new String("warps." + key + ".world"), currentWarp.getWorld());

		}

		config.save(file);
	}

}
