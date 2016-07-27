package my.smk.plugin.jla;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginDescriptionFile;

import my.smk.plugin.jla.Load_config;
//import jp.kotmw.loginbonus.FileDatas.PluginFiles;;

public class Main extends JavaPlugin implements Listener
{
//	public final Logger log = Logger.getLogger("Minecraft");
	public static Main plugin;
//	File file_config = new File(folder + "config.yml");
//	public FileConfiguration conf = YamlConfiguration.loadConfiguration(file_config);
	static FileConfiguration conf = null;
	PluginDescriptionFile loadFile = getDescription();
	public String folder = getDataFolder() + File.separator;
	File file_isPlayerFirstJoin = new File(folder + "isPlayerFirstJoin.yml");
	FileConfiguration yaml = new YamlConfiguration();
	public FileConfiguration loadfile = YamlConfiguration.loadConfiguration(file_isPlayerFirstJoin);
	String PluginName = loadFile.getName();
	String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + PluginName + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + PluginName + ChatColor.WHITE + "] " + ChatColor.WHITE;
	Location loc;
	static Server server = null;
	public String filepath = getDataFolder() + File.separator;

	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		server = getServer();
		saveConfig();
		//PluginFiles.ConfigFile("config");
		/*
		try {
			conf = getConfig(null, "config.yml");
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		*/
/*
		if (!this.file_config.exists())
		{
			this.log.info(this.ErroPrefix + ChatColor.YELLOW + "Makeing config.yml.");
			setConfig();
		}
		saveDefaultConfig();
		reload_config();
		//Make isPlayerFirstJoin.yml.Start
		if (!this.file.exists())
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + ChatColor.YELLOW + "Makeing isPlayerFirstJoin.yml.");
			try {
				loadfile.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Make isPlayerFirstJoin.yml.Finish
		 */
	}

	public void onDisable()
	{
	}

	public static FileConfiguration getConfigFile()
	{
		FileConfiguration conf = new YamlConfiguration();
		File file = new File(plugin.getDataFolder() + File.separator, "config.yml");
		try {
		conf.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return conf;
	}

	public static Server getServer_()
	{
		return server;
	}

	public void setConfig()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		return;
	}

	public void setSpawnLoc(Player player)
	{
		String pl_world = player.getWorld().getName();
		double pl_x = player.getLocation().getX();
		double pl_y = player.getLocation().getY();
		double pl_z = player.getLocation().getZ();
		float pl_Yaw = player.getLocation().getYaw();
		float pl_Pitch = player.getLocation().getPitch();
		getConfig().set("SpawnLoc.World", pl_world);
		getConfig().set("SpawnLoc.x", pl_x);
		getConfig().set("SpawnLoc.y", pl_y);
		getConfig().set("SpawnLoc.z", pl_z);
		getConfig().set("SpawnLoc.Yaw", pl_Yaw);
		getConfig().set("SpawnLoc.Pitch", pl_Pitch);
		if (Load_config.Debug_mode == true)
		{
			player.sendMessage("Player:" + player.toString());
			player.sendMessage("World:" + pl_world);
			player.sendMessage("x:" + String.valueOf(pl_x));
			player.sendMessage("y:" + String.valueOf(pl_y));
			player.sendMessage("z:" + String.valueOf(pl_z));
			player.sendMessage("Yaw:" + String.valueOf(pl_Yaw));
			player.sendMessage("Pitch:" + String.valueOf(pl_Pitch));
		}
	}

	public void doJLAInv(Player player)
	{
		for (int i = 1; i <= 9; i++)
		{
			String Item = getConfig().getString("Inv." + i + ".Item");
			int ItemStackNumber = getConfig().getInt("Inv." + i + ".ItemStackNumber");
			String ItemName = getConfig().getString("Inv." + i + ".ItemName");
			List<String> ItemLore = getConfig().getStringList("Inv." + i + ".ItemLore");
			Material ItemMaterial = Material.getMaterial(Item);
			if (Load_config.Debug_mode == true)
			{
				player.sendMessage(this.ChatPrefix + "Item:"+Item+" ItemStackNumber:"+ItemStackNumber+" ItemName:"+ItemName+" ItemLore:"+ItemLore+" ItemMaterial:"+ItemMaterial);
			}
			if (Item == null || ItemStackNumber == 0 || ItemMaterial == null)
			{
				if (Load_config.JoinInv_null_erro)
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
			int SlotNumber = i - 1;
			if (Load_config.Debug_mode)
			{
				player.sendMessage(this.ChatPrefix + "SlotNumber: " + SlotNumber);
				if (ItemStack.getItemMeta().getLore() == null)
				{
					player.sendMessage("Lore == null");
				} else {
					player.sendMessage(this.ChatPrefix + ItemStack.getItemMeta().getLore().toString());
				}
			}
			player.getInventory().setItem(SlotNumber, ItemStack);
		}
	}

	public void doJLAJoinMsg(CommandSender sender)
	{
		String sender_s = sender.getName();
		if (sender == null || sender_s == null)
		{
			if (Load_config.Debug_mode)
			{
				Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "Player is Null.");
				if (!(sender instanceof ConsoleCommandSender))
				{
					sender.sendMessage(this.ErroPrefix + "Player is Null.");
				}
			}
			return;
		}
	//user_msgが無だったら存在しない。
		if (Load_config.JoinMessage_users(sender_s).isEmpty())
		{
			//共通メッセージをやる所
			if (Load_config.JoinMessage_contents == null)
			{
				if (Load_config.Debug_mode)
				{
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config.yml - Player is Null.");
				}
				return;
			}
			for (Player online_player : getServer().getOnlinePlayers())
			{
				if (Load_config.JoinMessage_to_came_user) //来た人(JoinPlayer)にメッセージをやるか。
				{
					if (online_player == sender) continue;
					//鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
				}
				for (String msg : Load_config.JoinMessage_contents)
				{
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			for (Player online_player : getServer().getOnlinePlayers())
			{
				if (!Load_config.JoinMessage_to_came_user) //来た人にメッセージをやるか。
				{
					if (online_player == sender) continue;
					//鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
				}
				//user_msgをやる所
				for (String msg : Load_config.JoinMessage_users(sender_s))
				{
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void doJLALeaveMsg(CommandSender sender)
	{
		String sender_s = sender.getName();
		if (sender == null || sender_s == null)
		{
			if (Load_config.Debug_mode)
			{
				Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "Player is Null.");
				if (!(sender instanceof ConsoleCommandSender))
				{
					sender.sendMessage(this.ErroPrefix + "Player is Null.");
				}
			}
			return;
		}
	//user_msgが無だったら存在しない。
		if (Load_config.LeaveMessage_contents.isEmpty())
		{
			//共通メッセージをやる所
			if (Load_config.LeaveMessage_contents == null)
			{
				if (Load_config.Debug_mode)
				{
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config.yml - Player is Null.");
				}
				return;
			}
			for (Player online_player : getServer().getOnlinePlayers())
			{
				if (!Load_config.LeaveMessage_to_came_user) //来た人にメッセージをやるか。
				{
					if (online_player == sender) continue;
				}
				for (String msg : Load_config.JoinMessage_contents)
				{
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所
			for (Player online_player : getServer().getOnlinePlayers())
			{
				if (!Load_config.LeaveMessage_to_came_user) //来た人にメッセージをやるか。
				{
					if (online_player == sender) continue;
				}
				for (String msg : Load_config.LeaveMessage_contents)
				{
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void doJLAJoinTell(CommandSender sender)
	{
		for (String msg : Load_config.JoinMessage_tell_contents)
		{
			msg = changeMsg(msg, sender);
			sender.sendMessage(msg);
		}
	}

	public String changeMsg(String msg, CommandSender sender)
	{
		msg = msg.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player)
		{
			Player player = (Player)sender;
			msg = msg.replaceAll("%World%", player.getWorld().getName());
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}

	public boolean isPlayerFirstJoin(Player player)
	{
		if (!Load_config.FirstJoin_use_YAML && !player.hasPlayedBefore())
		{//以前プレイしたことがある時
			if (Load_config.Debug_mode) player.sendMessage("isPlayerFirstJoin1");
			return true;
		}
		if (Load_config.FirstJoin_use_YAML)
		{
			List<String> players = loadfile.getStringList("Players");
			for (String s : players)
			{
				if (s.equals(player.getName()))
				{
					return false;
				}
			}
			if (Load_config.Debug_mode) player.sendMessage("isPlayerFirstJoin2");
			players.add(player.getName());//Firstのプレーヤーを追加
			loadfile.set("Players",players);
			try {
				loadfile.save(file_isPlayerFirstJoin);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		if (Load_config.Debug_mode) player.sendMessage("isPlayerFirstJoin == false");
		return false;
	}

	public void doJLAFirstJoinMsg(CommandSender sender)
	{
		String sender_s = sender.getName();
		if (sender == null || sender_s == null)
		{
			if (Load_config.Debug_mode)
			{
				Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "Player is Null.");
				if (!(sender instanceof ConsoleCommandSender))
				{
					sender.sendMessage(this.ErroPrefix + "Player is Null.");
				}
			}
			return;
		}
	//user_msgが無だったら存在しない。
		if (Load_config.FirstJoinMessage_users(sender_s).isEmpty())
		{
			//共通メッセージをやる所
			if (Load_config.FirstJoinMessage_contents == null)
			{
				if (Load_config.Debug_mode) Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "config.yml - Player is Null.");
				return;
			}
			for (Player online_player : getServer().getOnlinePlayers())
			{
				if (!Load_config.FirstJoinMessage_to_came_user) //来た人にメッセージをやるか。
				{
					if (online_player == sender) continue;
					//鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
				}
				for (String msg : Load_config.FirstJoinMessage_contents)
				{
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所
			for (Player online_player : getServer().getOnlinePlayers())
			{
				if (!Load_config.FirstJoinMessage_to_came_user) //来た人にメッセージをやるか。
				{
					if (online_player == sender) continue;
					//鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
				}
				for (String msg : Load_config.FirstJoinMessage_users(sender_s))
				{
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void doJLAFirstJoinTell(CommandSender sender)
	{
		for (String msg : Load_config.FirstJoinMessage_tell_contents)
		{
			msg = changeMsg(msg, sender);
			sender.sendMessage(msg);
		}
	}

	public void JLACommandList(CommandSender sender)
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
			if (!(sender instanceof Player))
			{
				sender.sendMessage(this.ErroPrefix + "You have to send from Player.");
				return false;
			}
			if (!sender.hasPermission("JoinLeaveAction.cmd.setspawn"))
			{
				sender.sendMessage(this.ErroPrefix + "You don't have Permission.");
				return false;
			}
			Player player = (Player)sender;
			setSpawnLoc(player);
			saveConfig();
			reloadConfig();
			sender.sendMessage(this.ChatPrefix + "Spawn is setted.");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("spawn"))
		{
			if (args.length == 0)
			{
				if (!(sender instanceof Player))
				{
					sender.sendMessage(this.ErroPrefix + "You don't have Permission.");
					return false;
				}
				if (!sender.hasPermission("JoinLeaveAction.cmd.spawn.me"))
				{
					Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "You have to send from Player.");
					return false;
				}
				Player player = (Player)sender;
				if (Load_config.Debug_mode) sender.sendMessage(this.ChatPrefix + this.loc.toString());
				if (Load_config.SpawnLoc().getWorld() == null) return false;
				player.teleport(this.loc);
				if (Load_config.SpawnTPMsg_me)
				{
					String SpawnTPedMsg_me = Load_config.SpawnTPMsg_me_contents;
					if (SpawnTPedMsg_me != null)
					{
						SpawnTPedMsg_me = changeMsg(SpawnTPedMsg_me, sender);
						sender.sendMessage(this.ChatPrefix + SpawnTPedMsg_me);
					}
				}
				return true;
			} else {
				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				if (sender.hasPermission("JoinLeaveAction.cmd.spawn.other") || !(sender instanceof Player))
				{
					sender.sendMessage("You don't have Permission.");
					return false;
				}
				if (player == null)
				{
					sender.sendMessage(this.ErroPrefix+ "That player dose not exsist.");
					return false;
				}
				if (Load_config.Debug_mode == true)
				{
					player.sendMessage(this.ChatPrefix + loc.toString());
				}
				if (Load_config.SpawnLoc().getWorld() == null) return false;
				player.teleport(this.loc);
				if (Load_config.SpawnTPMsg_other)
				{
					String SpawnTPedMsg_other = Load_config.SpawnTPMsg_other_contents;
					if (SpawnTPedMsg_other != null)
					{
						SpawnTPedMsg_other = changeMsg(SpawnTPedMsg_other, sender);
						sender.sendMessage(this.ChatPrefix + SpawnTPedMsg_other);
					}
				}
				return true;
			}
		} else if ((cmd.getName().equalsIgnoreCase("JoinLeaveAction")) || (cmd.getName().equalsIgnoreCase("jla")))
		{
			if (!sender.hasPermission("JoinLeaveAction.cmd") && (sender instanceof Player))
			{
				sender.sendMessage(this.ChatPrefix + "You don't have Permission.(JoinLeaveAction.cmd)");
				return false;
			}
			if (args.length == 0)
			{
				//jla(引数なし)。
				JLACommandList(sender);
				return true;
			}
			if (args.length > 0)
			{
				//jla ___(引数が何かしらある時)
				if (args[0].equalsIgnoreCase("reload"))
				{
					if (sender.hasPermission("JoinLeaveAction.cmd.reload") || !(sender instanceof Player))
					{
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
							getConfigFile();
							sender.sendMessage(this.ChatPrefix + "config.yml is reload!");
							return true;
					} else {
						sender.sendMessage(this.ErroPrefix + "You don't have Permission to use reload.");
					}
				}
				if (args[0].equalsIgnoreCase("test"))
				{
					if (!sender.hasPermission("JoinLeaveAction.cmd.test") && (sender instanceof Player))
					{
						sender.sendMessage(this.ErroPrefix + "You don't have Permission to use test.");
						return false;
					}
					if (args.length == 2)
					{
						if (args[1].equalsIgnoreCase("JoinMsg"))
						{
							doJLAJoinMsg(sender);
							doJLAJoinTell(sender);
							return true;
						}
						if (args[1].equalsIgnoreCase("LeaveMsg"))
						{
							doJLALeaveMsg(sender);
							return true;
						}
						if (args[1].equalsIgnoreCase("Inv"))
						{
							if (sender instanceof Player)
							{
								Player player = (Player) sender;
								player.getInventory().clear();
								doJLAInv(player);
								return true;
							} else {
								Bukkit.getConsoleSender().sendMessage(this.ChatPrefix + "You have to send from Player.");
								return false;
							}
						}
					}
					sender.sendMessage(this.ErroPrefix + "JoinMsg, LeaveMsg, Inv");
					JLACommandList(sender);
					return false;
				}
				if (args[0].equalsIgnoreCase("help"))
				{
					JLACommandList(sender);
					return true;
				}
				sender.sendMessage(this.ErroPrefix + "Command is erro.");
				JLACommandList(sender);
				return false;
			}
		}
	return false;
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		getServer().broadcastMessage("SS");
		if (getConfig().getBoolean("Debug_mode"))
		{
			event.setJoinMessage("");
			CommandSender sender = event.getPlayer();
			sender.sendMessage(getConfigFile().getStringList("JoinMessage_contents").toString());
//			doJLAJoinMsg(sender);
//			doJLAJoinTell(sender);
		}
		getServer().broadcastMessage("FF");
/*		if (Load_config.JoinMessage)
		{
			event.setJoinMessage("");
			CommandSender sender = event.getPlayer();
			doJLAJoinMsg(sender);
			doJLAJoinTell(sender);
		}
		if (Load_config.JoinSpawn)
		{
			Player player = event.getPlayer();
			if (Load_config.SpawnLoc().getWorld() == null) return;
			if (Load_config.Debug_mode) player.sendMessage(this.ChatPrefix + this.loc.toString());
			player.teleport(this.loc);
		}
		if (Load_config.JoinInv)
		{
			Player player = event.getPlayer();
			player.getInventory().clear();
			doJLAInv(player);
		}
		if (Load_config.JoinMessage)
		{
			if (isPlayerFirstJoin(event.getPlayer()))
			{
				CommandSender sender = event.getPlayer();
				if (Load_config.Debug_mode) sender.sendMessage(this.ChatPrefix + "You are FistJoiner!");
				doJLAFirstJoinMsg(sender);
				doJLAFirstJoinTell(sender);
			}
		}*/
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		if (Load_config.LeaveMessage)
		{
			CommandSender sender = event.getPlayer();
			event.setQuitMessage("");
			doJLALeaveMsg(sender);
		}
	}
}

//TODO
//設定ファイル関係をまとめること。
//命名法則がクソ
