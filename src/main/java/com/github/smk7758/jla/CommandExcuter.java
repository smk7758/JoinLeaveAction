package com.github.smk7758.jla;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExcuter implements CommandExecutor {
	private Main plugin;

	public CommandExcuter(Main instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (!(sender instanceof Player)) {
				plugin.cLog.sendMessage(sender, "You have to send from Player.", 2);
				return false;
			}
			if (!sender.hasPermission("JoinLeaveAction.cmd.setspawn")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "JoinLeaveAction.cmd.setspawn");
				return false;
			}
			Player player = (Player) sender;
			plugin.Util.getLocation(player, "SpawnLoc");
			plugin.cLog.sendMessage(sender, "Spawn was set.", 0);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) return false;
				if (!sender.hasPermission("JoinLeaveAction.cmd.spawn.me")) {
					plugin.cLog.sendPermissionErrorMessage(sender, "JoinLeaveAction.cmd.spawn.me");
					return false;
				}
				Player player = (Player) sender;
				plugin.Util.teleportPlayer(player, plugin.LoadConfig.SpawnLoc());
				if (plugin.LoadConfig.SpawnTPMsg_me()) {
					String SpawnTPedMsg_me = plugin.LoadConfig.SpawnTPMsg_me_contents();
					if (SpawnTPedMsg_me != null) {
						SpawnTPedMsg_me = plugin.Util.changeMsg(SpawnTPedMsg_me, sender);
						sender.sendMessage(SpawnTPedMsg_me);
					}
				}
				return true;
			} else {
				Player player = Bukkit.getPlayer(args[0]);
				if (sender.hasPermission("JoinLeaveAction.cmd.spawn.other") || !(sender instanceof Player)) {
					plugin.cLog.sendPermissionErrorMessage(sender, "JoinLeaveAction.cmd.spawn.other");
					return false;
				}
				if (player == null) {
					plugin.cLog.sendMessage(sender, "That player dose not exsist.", 2);
					return false;
				}
				plugin.Util.teleportPlayer(player, plugin.LoadConfig.SpawnLoc());
				if (plugin.LoadConfig.SpawnTPMsg_other()) {
					String SpawnTPedMsg_other = plugin.LoadConfig.SpawnTPMsg_other_contents();
					if (SpawnTPedMsg_other != null) {
						SpawnTPedMsg_other = plugin.Util.changeMsg(SpawnTPedMsg_other, sender);
						sender.sendMessage(SpawnTPedMsg_other);
					}
				}
				return true;
			}
		}
		if ((cmd.getName().equalsIgnoreCase("JoinLeaveAction")) || (cmd.getName().equalsIgnoreCase("jla"))) {
			if (!sender.hasPermission("JoinLeaveAction.cmd") && (sender instanceof Player)) {
				plugin.cLog.sendPermissionErrorMessage(sender, "JoinLeaveAction.cmd");
				return false;
			}
			if (args.length == 0) {
				// jla(引数なし)。
				plugin.Util.sendCommandList(sender);
				return true;
			}
			if (args.length > 0) {
				// jla ___(引数が何かしらある時)
				if (args[0].equalsIgnoreCase("Debug")) {
					if (!sender.hasPermission("JoinLeaveAction.cmd.Debug") && (sender instanceof Player)) {
						plugin.cLog.sendPermissionErrorMessage(sender, "JoinLeaveAction.cmd.Debug");
						return false;
					}
					if (plugin.DebugMode) {
						plugin.DebugMode = false;
						plugin.cLog.sendMessage(sender, "DebugMode has benn false.", 0);
					} else {
						plugin.DebugMode = true;
						plugin.cLog.sendMessage(sender, "DebugMode has benn true.", 0);
					}
					plugin.cLog.sendMessage(sender, "This is a check. DebugMode is true.", 3);
					return true;
				}
				if (args[0].equalsIgnoreCase("test") && (sender instanceof Player)) {
					if (!sender.hasPermission("JoinLeaveAction.cmd.test")) {
						plugin.cLog.sendPermissionErrorMessage(sender, "JoinLeaveAction.cmd.test");
						return false;
					}
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("JoinSpawn")) {
							plugin.Util.teleportPlayer((Player) sender, plugin.LoadConfig.SpawnLoc());
							return true;
						}
						if (args[1].equalsIgnoreCase("JoinMsg")) {
							plugin.JoinMsg(sender);
							plugin.Util.sendMsgs(sender, plugin.LoadConfig.JoinMessage_contents());
							return true;
						}
						if (args[1].equalsIgnoreCase("JoinInv")) {
							Player player = (Player) sender;
							player.getInventory().clear();
							plugin.JoinInv(player);
							return true;
						}
						if (args[1].equalsIgnoreCase("LeaveMsg")) {
							plugin.LeaveMsg(sender);
							return true;
						}
					}
					plugin.cLog.sendMessage(sender, "JoinSpawn, JoinMsg, JoinInv, LeaveMsg", 2);
					plugin.Util.sendCommandList(sender);
					return false;
				}
				if (args[0].equalsIgnoreCase("help")) {
					plugin.Util.sendCommandList(sender);
					return true;
				}
				plugin.cLog.sendMessage(sender, "Your commands are error.", 2);
				plugin.Util.sendCommandList(sender);
				return false;
			}
		}
		return false;
	}
}
