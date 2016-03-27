package my.smk.plugin.jla;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class Main
extends JavaPlugin
implements Listener
{
	public final Logger log = Logger.getLogger("Minecraft");
	public static Main plugin;
	public String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + "JoinLeaveAction" + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	public String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + "JoinLeaveAction" + ChatColor.WHITE + "] " + ChatColor.WHITE;
	public String LoggerPprefix = ChatColor.WHITE + "[" + ChatColor.RED + "JoinLeaveAction" + ChatColor.WHITE + "] " + ChatColor.WHITE;
	File config = new File(getDataFolder() + File.separator + "Config.yml");
	Location loc;

	public void onEnable()
	{
		PluginDescriptionFile loadFile = getDescription();
		getServer().getPluginManager().registerEvents(this, this);
		this.log.info(loadFile.getName() + " version " + loadFile.getVersion() + " has been enabled.");
		if (!this.config.exists())
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + ChatColor.YELLOW + "Makeing config.yml.");
			setConfig();
		}
		saveDefaultConfig();
		saveConfig();
		reloadConfig();
	}
	public boolean config_Debug_mode = this.getConfig().getBoolean("Debug-mode");

	public void onDisable()
	{
		PluginDescriptionFile loadFile = getDescription();
		this.log.info(loadFile.getName() + " version " + loadFile.getVersion() + " has been disable.");
	}

	public void setConfig()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		return;
	}

	public Location getConfig_Loc(CommandSender sender)
	{
		World pl_world = getServer().getWorld(getConfig().getString("Loc.World"));
		double pl_x = getConfig().getDouble("Loc.x");
		double pl_y = getConfig().getDouble("Loc.y");
		double pl_z = getConfig().getDouble("Loc.z");
		float pl_Yaw = getConfig().getInt("Loc.Yaw");
		float pl_Pitch = getConfig().getInt("Loc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config - World is Null.");
			this.log.info("config - World is Null.");
			if (!(sender instanceof ConsoleCommandSender))
			{
				sender.sendMessage(this.ErroPrefix + "config.yml - World is Null.");
			}
		}
		this.loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}

	public void getConfig_Inv(Player player)
	{
		for (int i = 1; i <= 9; i++)
		{
			String Item = getConfig().getString("Inv." + i + ".Item");
			int ItemStackNumber = getConfig().getInt("Inv." + i + ".ItemStackNumber");
			String ItemName = getConfig().getString("Inv." + i + ".ItemName");
			List<String> ItemLore = getConfig().getStringList("Inv." + i + ".ItemLore");
			Material ItemMaterial = Material.getMaterial(Item);
			if (this.config_Debug_mode == true)
			{
				player.sendMessage(this.ChatPrefix + "Item:"+Item+" ItemStackNumber:"+ItemStackNumber+" ItemName:"+ItemName+" ItemLore:"+ItemLore+" ItemMaterial:"+ItemMaterial);
			}
			if (Item == null || ItemStackNumber == 0 || ItemMaterial == null)
			{
				if (getConfig().getBoolean("Inv_null_erro"))
				{
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config.yml - Inv's \""+i+"\" is Null.");
				}
				continue;
			}
			ItemStack ItemStack = new ItemStack(ItemMaterial, ItemStackNumber);
			if (ItemName != null)
			{
				ItemName = ChatColor.translateAlternateColorCodes('&',ItemName);
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setDisplayName(ItemName);
				ItemStack.setItemMeta(ItemMeta);
			}
			if (ItemLore != null)
			{
				List<String> a = new ArrayList<String>();
				for (String ItemLore_ : ItemLore)
				{
					a.add(ChatColor.translateAlternateColorCodes('&',ItemLore_));
				}
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setLore(a);
				ItemStack.setItemMeta(ItemMeta);
			}
			int a = i - 1;
			if (this.config_Debug_mode)
			{
				player.sendMessage(this.ChatPrefix + "SlotNumber: " + a);
				if (ItemStack.getItemMeta().getLore() == null)
				{
					player.sendMessage("Lore == null");
				} else {
					player.sendMessage(this.ChatPrefix + ItemStack.getItemMeta().getLore().toString());
				}
			}
			player.getInventory().setItem(a, ItemStack);
		}
	}

	public void getConfig_JoinMsg(CommandSender sender)
	{
		String sender_s = sender.getName();
		if (sender == null || sender_s == null)
		{
			if (this.config_Debug_mode)
			{
				Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "Player is Null.");
				if (!(sender instanceof ConsoleCommandSender))
				{
					sender.sendMessage(this.ErroPrefix + "Player is Null.");
				}
			}
			return;
		}
		List<String> user_msg = getConfig().getStringList("JoinMessage_user." + sender_s);
	//user_msgが無だったら存在しない。
		if (user_msg.isEmpty())
		{
			//共通メッセージをやる所
			List<String> JoinMessage = getConfig().getStringList("JoinMessage");
			if (JoinMessage == null)
			{
				if (this.config_Debug_mode)
				{
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config.yml - Player is Null.");
				}
				return;
			}
			for (String msg : JoinMessage)
			{
				msg = msg.replaceAll("%Player%", sender_s);
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				for (Player online_player : getServer().getOnlinePlayers())
				{
					if (getConfig().getBoolean("JoinMessage_to_came_user") == false) //来た人にメッセージをやるか。
					{
						if (online_player == sender) continue;
						//鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
					}
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所
			for (String msg : user_msg)
			{
				msg = msg.replaceAll("%Player%", sender_s);
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				for (Player online_player : getServer().getOnlinePlayers())
				{
					if (getConfig().getBoolean("JoinMessage_to_came_user") == false) //来た人にメッセージをやるか。
					{
						if (online_player == sender) continue;
						//鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
					}
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void getConfig_Jointell(CommandSender sender)
	{
		for (String msg : getConfig().getStringList("JoinMessage_tell"))
		{
			msg = msg.replaceAll("%Player%" , sender.getName());
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
		}
	}

	public void getConfig_LeaveMsg(CommandSender sender)
	{
		String sender_s = sender.getName();
		if (sender == null || sender_s == null)
		{
			if (this.config_Debug_mode)
			{
				Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "Player is Null.");
				if (!(sender instanceof ConsoleCommandSender))
				{
					sender.sendMessage(this.ErroPrefix + "Player is Null.");
				}
			}
			return;
		}
		List<String> user_msg = getConfig().getStringList("LeaveMessage_user." + sender_s);
	//user_msgが無だったら存在しない。
		if (user_msg.isEmpty())
		{
			//共通メッセージをやる所
			List<String> JoinMessage = getConfig().getStringList("LeaveMessage");
			if (JoinMessage == null)
			{
				if (this.config_Debug_mode)
				{
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config.yml - Player is Null.");
				}
				return;
			}
			for (String msg : JoinMessage)
			{
				msg = msg.replaceAll("%Player%", sender_s);
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				for (Player online_player : getServer().getOnlinePlayers())
				{
					if (getConfig().getBoolean("LeaveMessage_to_came_user") == false) //来た人にメッセージをやるか。
					{
						if (online_player == sender) continue;
					}
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所
			for (String msg : user_msg)
			{
				msg = msg.replaceAll("%Player%", sender_s);
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				for (Player online_player : getServer().getOnlinePlayers())
				{
					if (getConfig().getBoolean("LeaveMessage_to_came_user") == false) //来た人にメッセージをやるか。
					{
						if (online_player == sender) continue;
					}
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void JLA_CommandList(CommandSender sender)
	{
		sender.sendMessage(this.ChatPrefix + "Command List!!");
		sender.sendMessage(this.ChatPrefix + "TP you:/spawn | TP <Player>:/spawn <Player>");
		sender.sendMessage(this.ChatPrefix + "Set spawn:/setspawn");
		sender.sendMessage(this.ChatPrefix + "Long: /JoinLeaveAction | Short: /jla");
		sender.sendMessage(this.ChatPrefix + "/jla reload");
		sender.sendMessage(this.ChatPrefix + "/jla test <testing_thing_name>");
		return;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("setspawn"))
		{
			if (sender instanceof Player)
			{
				if (sender.hasPermission("JoinLeaveAction.cmd.setspawn"))
				{
					Player player = (Player)sender;
					String pl_world = player.getWorld().getName();
					double pl_x = player.getLocation().getX();
					double pl_y = player.getLocation().getY();
					double pl_z = player.getLocation().getZ();
					float pl_Yaw = player.getLocation().getYaw();
					float pl_Pitch = player.getLocation().getPitch();
					getConfig().set("Loc.World", pl_world);
					getConfig().set("Loc.x", pl_x);
					getConfig().set("Loc.y", pl_y);
					getConfig().set("Loc.z", pl_z);
					getConfig().set("Loc.Yaw", pl_Yaw);
					getConfig().set("Loc.Pitch", pl_Pitch);
					if (this.config_Debug_mode == true)
					{
						sender.sendMessage("Player:" + player.toString());
						sender.sendMessage("World:" + pl_world);
						sender.sendMessage("x:" + String.valueOf(pl_x));
						sender.sendMessage("y:" + String.valueOf(pl_y));
						sender.sendMessage("z:" + String.valueOf(pl_z));
						sender.sendMessage("Yaw:" + String.valueOf(pl_Yaw));
						sender.sendMessage("Pitch:" + String.valueOf(pl_Pitch));
					}
					saveConfig();
					reloadConfig();
					sender.sendMessage(this.ChatPrefix + "Spawn is setted.");
					return true;
				} else {
					sender.sendMessage(this.ErroPrefix + "You don't have Permission.");
					return false;
				}
			} else {
				sender.sendMessage(this.ErroPrefix + "You have to send from Player.");
			}
		} else if (cmd.getName().equalsIgnoreCase("spawn"))
		{
			if (args.length == 0)
			{
				if (sender instanceof Player)
				{
					if (sender.hasPermission("JoinLeaveAction.cmd.spawn.me"))
					{
						Player player = (Player)sender;
						if (this.config_Debug_mode)
						{
							sender.sendMessage(this.ChatPrefix + this.loc.toString());
						}
						getConfig_Loc(sender);
						if (getConfig_Loc(sender).getWorld() == null) return false;
						player.teleport(this.loc);
						if (getConfig().getBoolean("SpawnTPedMsg_me-true_or_false"))
						{
							String SpawnTPedMsg_me = getConfig().getString("SpawnTPedMsg_me");
							if (SpawnTPedMsg_me != null)
							{
								SpawnTPedMsg_me = ChatColor.translateAlternateColorCodes('&', SpawnTPedMsg_me);
								sender.sendMessage(this.ChatPrefix + SpawnTPedMsg_me);
							}
						}
						return true;
					} else {
						sender.sendMessage(this.ErroPrefix + "You don't have Permission.");
						return false;
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "You have to send from Player.");
					return false;
				}
			} else {
				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				if (player != null)
				{
					if (sender.hasPermission("JoinLeaveAction.cmd.spawn.other") || !(sender instanceof Player))
					{
						if (this.config_Debug_mode == true)
						{
							player.sendMessage(this.ChatPrefix + loc.toString());
						}
						getConfig_Loc(sender);
						if (getConfig_Loc(sender).getWorld() == null) return false;
						player.teleport(this.loc);
						if (getConfig().getBoolean("SpawnTPedMsg_other-true_or_false"))
						{
							String SpawnTPedMsg_other = getConfig().getString("SpawnTPedMsg_other");
							if (SpawnTPedMsg_other != null)
							{
								SpawnTPedMsg_other = ChatColor.translateAlternateColorCodes('&', SpawnTPedMsg_other);
								sender.sendMessage(this.ChatPrefix + SpawnTPedMsg_other);
							}
						}
						return true;
					} else {
							sender.sendMessage("You don't have Permission.");
					}
				} else {
					sender.sendMessage(this.ErroPrefix+ "That player dose not exsist.");
					return false;
				}
			}
		} else if ((cmd.getName().equalsIgnoreCase("JoinLeaveAction")) || (cmd.getName().equalsIgnoreCase("jla")))
		{
			if (sender.hasPermission("JoinLeaveAction.cmd") || !(sender instanceof Player))
			{
				if (args.length >= 1)
				{
					if (args[0].equalsIgnoreCase("reload"))
					{
						reloadConfig();
						sender.sendMessage(this.ChatPrefix + "config.yml is reloaded!");
						return true;
					}
					if (args[0].equalsIgnoreCase("test"))
					{
						if (args.length == 2)
						{
							if (args[1].equalsIgnoreCase("JoinMsg"))
							{
								getConfig_JoinMsg(sender);
								getConfig_Jointell(sender);
								return true;
							}
							if (args[1].equalsIgnoreCase("LeaveMsg"))
							{
								getConfig_LeaveMsg(sender);
								return true;
							}
							if (args[1].equalsIgnoreCase("Inv"))
							{
								if (sender instanceof Player)
								{
									Player player = (Player) sender;
									player.getInventory().clear();
									getConfig_Inv(player);
									return true;
								} else {
									Bukkit.getConsoleSender().sendMessage(this.ChatPrefix + "You have to send from Player.");
									return false;
								}
							}
						}
						sender.sendMessage(this.ErroPrefix + "JoinMsg, LeaveMsg, Inv");
						JLA_CommandList(sender);
						return false;
					}
					if (args[0].equalsIgnoreCase("help"))
					{
						JLA_CommandList(sender);
						return true;
					}
				}
				sender.sendMessage(this.ErroPrefix + "Command is erro.");
				JLA_CommandList(sender);
				return false;
			} else {
				sender.sendMessage(this.ChatPrefix + "You don't have Permission.(JoinLeaveAction.cmd)");
				return false;
			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerJoin_Msg(PlayerJoinEvent event)
	{
		//ここ無理矢理感
		if (getConfig().getBoolean("LeaveMessage-true_or_false"))
		{
			event.setJoinMessage("");
			CommandSender sender = event.getPlayer();
			getConfig_JoinMsg(sender);
			getConfig_Jointell(sender);
		}
	}

	@EventHandler
	public void onPlayerJoin_TP(PlayerJoinEvent event)
	{
		if (getConfig().getBoolean("JoinSpawn"))
		{
			Player player = event.getPlayer();
			CommandSender sender = Bukkit.getConsoleSender();
			getConfig_Loc(sender);
			if (getConfig_Loc(sender).getWorld() == null) return;
			if (this.config_Debug_mode == true)
			{
				player.sendMessage(this.ChatPrefix + this.loc.toString());
			}
			player.teleport(this.loc);
			return;
		}
	}

	@EventHandler
	public void onPlayerJoin_Inv(PlayerJoinEvent event)
	{
		if (getConfig().getBoolean("JoinInv"))
		{
			Player player = event.getPlayer();
			player.getInventory().clear();
			getConfig_Inv(player);
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		if (getConfig().getBoolean("LeaveMessage-true_or_false"))
		{
			CommandSender sender = event.getPlayer();
			event.setQuitMessage("");
			getConfig_LeaveMsg(sender);
		}
	}
}



