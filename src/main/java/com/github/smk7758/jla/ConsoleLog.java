/*
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
package com.github.smk7758.jla;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

/**
 * 単独実行(Util不使用)のみ
 */
public class ConsoleLog {
	private Main plugin;

	public ConsoleLog(Main instance) {
		plugin = instance;
	}

	public static final Logger log = Logger.getLogger("Minecraft");

	public void info(String msg) {
		log.info(plugin.cPrefix + msg);
		// 動作に支障が無く情報を出すだけで良い時
	}

	public void debug(String msg) {
		if (plugin.DebugMode) log.info(plugin.cPrefix + "[Debug] " + msg);
	}

	public void warn(String msg) {
		log.warning(plugin.cPrefix + msg);
		// 動作に支障がある時
	}

	/**
	 * メッセージを送る
	 *
	 * @param sender 宛先
	 * @param msg メッセージ
	 * @param type 0 PluginPrefix([PluginName]) 普通のメッセージ
	 * @param type 1 cPrefix pInfo コンソールへのメッセージ
	 * @param type 2 cPrefix pError エラーメッセージ
	 * @param type 3 cPrefix [Debug] デバッグメッセージ
	 */
	public void sendMessage(CommandSender sender, String msg, int type) {
		if (type == 0) sender.sendMessage(plugin.PluginPrefix + msg);
		if (type == 1) sender.sendMessage(plugin.cPrefix + plugin.pInfo + msg);
		if (type == 2) sender.sendMessage(plugin.cPrefix + plugin.pError + msg);
		if (type == 3 && plugin.DebugMode)
			sender.sendMessage(plugin.cPrefix + "[Debug]" + msg);
		else
			sender.sendMessage(plugin.PluginPrefix + msg);
	}

	public void sendPermissionErrorMessage(CommandSender sender, String permission) {
		sender.sendMessage(plugin.cPrefix + plugin.pError + "You don't have Permission.");
		if (plugin.DebugMode) sender.sendMessage(plugin.cPrefix + "[Debug] Permission:" + permission);
	}

	public void sendBroadCast(String msg) {
		plugin.getServer().broadcastMessage(plugin.PluginPrefix + msg);
	}
}
