/*
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015
 */
package com.github.smk7758.jla;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConsoleLog
{
	 Main plugin;
	 public ConsoleLog(Main instance)
	 {
		 this.plugin = instance;
	 }

	public static final Logger log = Logger.getLogger("Minecraft");
	public String cPrefix = "["+plugin.PluginName+"] ";
	public String Pluginprefix = "[" + ChatColor.GREEN + plugin.PluginName + ChatColor.RESET +"] ";
	public String pError = "[" + ChatColor.RED+ "ERROR" + ChatColor.RESET+"] ";
	public String pInfo =  "[" + ChatColor.RED+ "Info" + ChatColor.RESET+"] ";

	public void info(String Mess)
	{
		log.info(this.cPrefix + Mess);
		//動作に支障が無く情報を出すだけで良い時
	}
	public void debug(String Mess)
	{
		if(plugin.DebugMode) log.info(this.cPrefix + "[Debug] " +Mess);
	}

	public void warn(String Mess)
	{
		log.warning(this.cPrefix + Mess);
		//動作に支障がある時
	}
	public void Message(CommandSender sender, String Text)
	{
		sender.sendMessage(Pluginprefix + Text);
	}
	public void Message(Player p, String Text)
	{
		p.sendMessage(Pluginprefix + Text);
	}
	public void Message(CommandSender sender, String Text,int type)
	{
		if(type==0) sender.sendMessage(Pluginprefix + Text);
		else if(type==1) sender.sendMessage(cPrefix + pInfo + Text);
		else if(type==2) sender.sendMessage(cPrefix + pError + Text);
	}
	public void Message(Player p, String Text,int type)
	{
		if(type==0) p.sendMessage(Pluginprefix + Text);
		else if(type==1) p.sendMessage(cPrefix + pInfo + Text);
		else if(type==2) p.sendMessage(cPrefix + pError + Text);
		else if(type==3) if(plugin.DebugMode) p.sendMessage(cPrefix + "[debug]" + Text);
	}
	public void BroadCast(String Text)
	{
		plugin.getServer().broadcastMessage(Pluginprefix + Text);
	}
}
