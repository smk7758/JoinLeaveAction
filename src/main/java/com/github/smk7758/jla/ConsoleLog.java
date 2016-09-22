/*
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
package com.github.smk7758.jla;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class ConsoleLog {
	private Main plugin;
	public ConsoleLog(Main instance) {
		plugin = instance;
	}

	public static final Logger log = Logger.getLogger("Minecraft");
	public String PluginPrefix = "[" + ChatColor.GREEN + plugin.PluginName + ChatColor.RESET +"] ";
	public String cPrefix = "["+ plugin.PluginName +"] ";
	public String pInfo =  "[" + ChatColor.RED+ "Info" + ChatColor.RESET+"] ";
	public String pError = "[" + ChatColor.RED+ "ERROR" + ChatColor.RESET+"] ";

	public void info(String msg) {
		log.info(this.cPrefix + msg);
		//動作に支障が無く情報を出すだけで良い時
	}

	public void debug(String msg) {
		if(plugin.DebugMode) log.info(cPrefix + "[Debug] " +msg);
	}

	public void warn(String msg) {
		log.warning(cPrefix + msg);
		//動作に支障がある時
	}

	public void sendMessage(CommandSender sender, String msg) {
		sender.sendMessage(PluginPrefix + msg);
	}

// Playerである意味が無い気がする。sender型で代替化。
//	public void Message(Player p, String msg) {
//		p.sendMessage(PluginPrefix + msg);
//	}

	/**
	 * メッセージを送る
	 *
	 * @param sender 宛先
	 * @param msg メッセージ
	 * @param type 0 PluginPrefix([PluginName])
	 * @param type 1 cPrefix pInfo
	 * @param type 2 cPrefix pError
	 * @param type 3 cPrefix [Debug] | DebugMode
	 */
	public void sendMessage(CommandSender sender, String msg,int type) {
		if(type==0) sender.sendMessage(PluginPrefix + msg);
		if(type==1) sender.sendMessage(cPrefix + pInfo + msg);
		if(type==2) sender.sendMessage(cPrefix + pError + msg);
		if(type==3) if(plugin.DebugMode) sender.sendMessage(cPrefix + "[Debug]" + msg);
		else sender.sendMessage(PluginPrefix + msg);
	}

// Playerである意味が無い気がする。sender型で代替化。
//	/**
//	 * メッセージを送る
//	 *
//	 * @param sender 宛先
//	 * @param msg メッセージ
//	 * @param type 0 PluginPrefix
//	 * @param type 1 cPrefix pInfo
//	 * @param type 2 cPrefix pError
//	 * @param type 3 cPrefix [debug] | DebugMode
//	 */
//	public void Message(Player p, String msg,int type) {
//		if(type==0) p.sendMessage(PluginPrefix + msg);
//		else if(type==1) p.sendMessage(cPrefix + pInfo + msg);
//		else if(type==2) p.sendMessage(cPrefix + pError + msg);
//		else if(type==3) if(plugin.DebugMode) p.sendMessage(cPrefix + "[debug]" + msg);
//	}

	public void sendBroadCast(String msg) {
		plugin.getServer().broadcastMessage(PluginPrefix + msg);
	}
}
