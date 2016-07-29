package com.github.smk7758.jla;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commander {
	Main plugin;
	ConsoleLog cLog;
	LoadConfig LoadConfig;
	FileIO FileIO;
	public Commander(Main instance)
	{
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (!(sender instanceof Player)) {
				cLog.Message(sender, "You have to send from Player.", 2);
				return false;
			}
			if (!sender.hasPermission("JoinLeaveAction.cmd.setspawn")) {
				cLog.Message(sender, "You don't have Permission.", 2);
				return false;
			}
			Player p = (Player) sender;
			plugin.setLoc(p, "SpawnLoc");
			plugin.reloadConfig(); //大丈夫?
			//saveConfig();
			//reloadConfig();
			cLog.Message(sender, "Spawn was set.");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) return false;
				if (!sender.hasPermission("JoinLeaveAction.cmd.spawn.me")) {
					cLog.Message(sender, "You don't have Permission.", 2);
					return false;
				}
				Player p = (Player) sender;
				plugin.Teleport(p, LoadConfig.SpawnLoc());
				if (LoadConfig.SpawnTPMsg_me) {
					String SpawnTPedMsg_me = LoadConfig.SpawnTPMsg_me_contents;
					if (SpawnTPedMsg_me != null) {
						SpawnTPedMsg_me = plugin.ChangeMsg(SpawnTPedMsg_me, sender);
						sender.sendMessage(SpawnTPedMsg_me);
					}
				}
				return true;
			} else {
				Player p = Bukkit.getPlayer(args[0]);
				if (sender.hasPermission("JoinLeaveAction.cmd.spawn.other") || !(sender instanceof Player)) {
					sender.sendMessage("You don't have Permission.");
					return false;
				}
				if (p == null) {
					cLog.Message(sender, "That player dose not exsist.", 2);
					return false;
				}
				plugin.Teleport(p, LoadConfig.SpawnLoc());
				if (LoadConfig.SpawnTPMsg_other) {
					String SpawnTPedMsg_other = LoadConfig.SpawnTPMsg_other_contents;
					if (SpawnTPedMsg_other != null) {
						SpawnTPedMsg_other = plugin.ChangeMsg(SpawnTPedMsg_other, sender);
						sender.sendMessage(SpawnTPedMsg_other);
					}
				}
				return true;
			}
		} else if ((cmd.getName().equalsIgnoreCase("JoinLeaveAction")) || (cmd.getName().equalsIgnoreCase("jla"))) {
			if (!sender.hasPermission("JoinLeaveAction.cmd") && (sender instanceof Player)) {
				cLog.Message(sender, "You don't have Permission.", 2);
				return false;
			}
			if (args.length == 0) {
				//jla(引数なし)。
				plugin.sendCommandList(sender);
				return true;
			}
			if (args.length > 0) {
				//jla ___(引数が何かしらある時)
				if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("JoinLeaveAction.cmd.reload") && (sender instanceof Player)) {
						cLog.Message(sender, "You don't have Permission to use reload.", 2);
						return false;
					}
					/*
						if (!this.file_config.exists())
						{
							Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + ChatColor.YELLOW + "Makeing config.yml.");
							setConfig();
						}
						reload_config();
						try {
							loadfile.load(file_isPlayerFirstJoin);
						} catch (IOException
								| InvalidConfigurationException e) {
							e.printStackTrace();
						}
						sender.sendMessage(this.ChatPrefix + "config.yml is reloaded!");
						return true;
					 */
					plugin.reloadConfig(); //大丈夫?
					cLog.Message(sender, "config.yml is reload!");
					return true;
				}
//他が完了するまで放置
//				if (args[0].equalsIgnoreCase("test") && (sender instanceof Player)) {
//					if (!sender.hasPermission("JoinLeaveAction.cmd.test")) {
//						cLog.Message(sender, "You don't have Permission to use test.", 2);
//						return false;
//					}
//					if (args.length > 1) {
//						if (args[1].equalsIgnoreCase("JoinSpawn")) {
//							plugin.Teleport((Player) sender, LoadConfig.SpawnLoc());
//							return true;
//						}
//						if (args[1].equalsIgnoreCase("JoinMsg")) {
//							plugin.JoinMsg(sender);
//							plugin.TellMsg(sender, LoadConfig.JoinMessage_contents);
//							return true;
//						}
//						if (args[1].equalsIgnoreCase("JoinInv")) {
//							Player p = (Player) sender;
//							p.getInventory().clear();
//							plugin.JoinInv(p);
//							return true;
//						}
//						if (args[1].equalsIgnoreCase("LeaveMsg")) {
//							plugin.LeaveMsg(sender);
//							return true;
//						}
//					}
//					cLog.Message(sender, "JoinMsg, LeaveMsg, Inv", 2);
//					plugin.sendCommandList(sender);
//					return false;
//				}
				if (args[0].equalsIgnoreCase("help")) {
					plugin.sendCommandList(sender);
					return true;
				}
				cLog.Message(sender, "Your commands are error.", 2);
				plugin.sendCommandList(sender);
				return false;
			}
		}
		return false;
	}
}
