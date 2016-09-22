package com.github.smk7758.jla;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {
	private Main plugin;
	public Util(Main instance) {
		plugin = instance;
	}

	public void getLocation(Player player, String type) {
		String world = player.getWorld().getName();
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		float yaw = player.getLocation().getYaw();
		float pitch = player.getLocation().getPitch();
		plugin.FileIO.LocationIO(true, "config", type, world, x, y, z, yaw, pitch);
		plugin.cLog.sendMessage(player, "Player:" + player.getName(), 3);
		plugin.cLog.sendMessage(player, "World:" + world, 3);
		plugin.cLog.sendMessage(player, "X:" + x, 3);
		plugin.cLog.sendMessage(player, "Y:" + y, 3);
		plugin.cLog.sendMessage(player, "Z:" + z, 3);
		plugin.cLog.sendMessage(player, "Yaw:" + yaw, 3);
		plugin.cLog.sendMessage(player, "Pitch:" + pitch, 3);
	}

	public void teleportPlayer(Player player, Location loc) {
		plugin.cLog.sendMessage(player, loc.toString(), 3);
		if (loc.getWorld() == null) {
			plugin.cLog.warn("Teleport did not work because the World is null.");
			plugin.cLog.sendMessage(player, "Teleport did not work because the World is null.", 2);
			return;
		}
		player.teleport(loc);
	}

	public void tellMsg (CommandSender sender, List<String> msgs) {
		for (String msg : msgs) {
			msg = changeMsg(msg, sender);
			sender.sendMessage(msg);
		}
	}

	public String changeMsg(String msg, CommandSender sender) {//Msgの中のテンプレがあったら変更
		msg = msg.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player) {
			Player player = (Player)sender;
			msg = msg.replaceAll("%World%", player.getWorld().getName());
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}
}
