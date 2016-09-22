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
				plugin.cLog.sendMessage(sender, "You don't have Permission.", 2);
				return false;
			}
			Player player = (Player)sender;
			plugin.setLoc(player, "SpawnLoc");
			plugin.cLog.sendMessage(sender, "Spawn was set.");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) return false;
				if (!sender.hasPermission("JoinLeaveAction.cmd.spawn.me")) {
					plugin.cLog.sendMessage(sender, "You don't have Permission.", 2);
					return false;
				}
				Player player = (Player)sender;
				plugin.Teleport(player, plugin.LoadConfig.SpawnLoc());
				if (plugin.LoadConfig.SpawnTPMsg_me()) {
					String SpawnTPedMsg_me = plugin.LoadConfig.SpawnTPMsg_me_contents();
					if (SpawnTPedMsg_me != null) {
						SpawnTPedMsg_me = plugin.changeMsg(SpawnTPedMsg_me, sender);
						sender.sendMessage(SpawnTPedMsg_me);
					}
				}
				return true;
			} else {
				Player player = Bukkit.getPlayer(args[0]);
				if (sender.hasPermission("JoinLeaveAction.cmd.spawn.other") || !(sender instanceof Player)) {
					sender.sendMessage("You don't have Permission.");
					return false;
				}
				if (player == null) {
					plugin.cLog.sendMessage(sender, "That player dose not exsist.", 2);
					return false;
				}
				plugin.Teleport(player, plugin.LoadConfig.SpawnLoc());
				if (plugin.LoadConfig.SpawnTPMsg_other()) {
					String SpawnTPedMsg_other = plugin.LoadConfig.SpawnTPMsg_other_contents();
					if (SpawnTPedMsg_other != null) {
						SpawnTPedMsg_other = plugin.changeMsg(SpawnTPedMsg_other, sender);
						sender.sendMessage(SpawnTPedMsg_other);
					}
				}
				return true;
			}
		} else if ((cmd.getName().equalsIgnoreCase("JoinLeaveAction")) || (cmd.getName().equalsIgnoreCase("jla"))) {
			if (!sender.hasPermission("JoinLeaveAction.cmd") && (sender instanceof Player)) {
				plugin.cLog.sendMessage(sender, "You don't have Permission.", 2);
				return false;
			}
			if (args.length == 0) {
				//jla(引数なし)。
				plugin.sendCommandList(sender);
				return true;
			}
			if (args.length > 0) {
				//jla ___(引数が何かしらある時)
//				if (args[0].equalsIgnoreCase("reload")) {
//					if (!sender.hasPermission("JoinLeaveAction.cmd.reload") && (sender instanceof Player)) {
//						plugin.cLog.sendMessage(sender, "You don't have Permission to use reload.", 2);
//						return false;
//					}
//					/*
//						if (!this.file_config.exists())
//						{
//							Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + ChatColor.YELLOW + "Makeing config.yml.");
//							setConfig();
//						}
//						reload_config();
//						try {
//							loadfile.load(file_isPlayerFirstJoin);
//						} catch (IOException
//								| InvalidConfigurationException e) {
//							e.printStackTrace();
//						}
//						sender.sendMessage(this.ChatPrefix + "config.yml is reloaded!");
//						return true;
//					 */
//					plugin.reloadConfig();
//					plugin.cLog.sendMessage(sender, "config.yml is reload!");
//					return true;
//				}
				if (args[0].equalsIgnoreCase("test") && (sender instanceof Player)) {
					if (!sender.hasPermission("JoinLeaveAction.cmd.test")) {
						plugin.cLog.sendMessage(sender, "You don't have Permission to use test.", 2);
						return false;
					}
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("JoinSpawn")) {
							plugin.Teleport((Player) sender, plugin.LoadConfig.SpawnLoc());
							return true;
						}
						if (args[1].equalsIgnoreCase("JoinMsg")) {
							plugin.JoinMsg(sender);
							plugin.tellMsg(sender, plugin.LoadConfig.JoinMessage_contents());
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
					plugin.sendCommandList(sender);
					return false;
				}
				if (args[0].equalsIgnoreCase("help")) {
					plugin.sendCommandList(sender);
					return true;
				}
				plugin.cLog.sendMessage(sender, "Your commands are error.", 2);
				plugin.sendCommandList(sender);
				return false;
			}
		}
		return false;
	}
}
