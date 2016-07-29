package com.github.smk7758.jla;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

public class Main extends JavaPlugin implements Listener {
	public String PluginName = getDescription().getName();
	static FileConfiguration conf = null;
	public String folder = getDataFolder() + File.separator;

	public ConsoleLog cLog = new ConsoleLog(this);
	public FileIO FileIO = new FileIO(this);
	public LoadConfig LoadConfig = new LoadConfig(this);
	public boolean DebugMode = false;

	File file_isPlayerFirstJoin = new File(folder + "isPlayerFirstJoin.yml");
	FileConfiguration yaml = new YamlConfiguration();
	public FileConfiguration loadfile = YamlConfiguration.loadConfiguration(file_isPlayerFirstJoin);
	Location loc;
	static Server server = null;
	public String filepath = getDataFolder() + File.separator;

	@Override
	public void onEnable() {
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

	@Override
	public void onDisable() {
	}

	public FileConfiguration getConfigFile() {
		FileConfiguration conf = new YamlConfiguration();
		File file = new File(folder, "config.yml");
		try {
		conf.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return conf;
	}

	public void setConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
	}

	public void setLoc(Player p, String Type) {
		World World = p.getWorld();
		Short X = (short)p.getLocation().getX();
		Short Y = (short)p.getLocation().getY();
		Short Z = (short)p.getLocation().getZ();
		Short Yaw = (short)p.getLocation().getYaw();
		Short Pitch = (short)p.getLocation().getPitch();
		FileIO.LocationIO(true, "config", Type, World, X, Y, Z, Yaw, Pitch);
		cLog.debug("Player:" + p.getName());
		cLog.debug("World:" + World);
		cLog.debug("X:" + String.valueOf(X));
		cLog.debug("Y:" + String.valueOf(Y));
		cLog.debug("Z:" + String.valueOf(Z));
		cLog.debug("Yaw:" + String.valueOf(Yaw));
		cLog.debug("Pitch:" + String.valueOf(Pitch));
	}

	public void JoinInv(Player player) {
		for (int i = 1; i <= 9; i++) {
			String Item = getConfig().getString("Inv." + i + ".Item");
			int ItemStackNumber = getConfig().getInt("Inv." + i + ".ItemStackNumber");
			String ItemName = getConfig().getString("Inv." + i + ".ItemName");
			List<String> ItemLore = getConfig().getStringList("Inv." + i + ".ItemLore");
			Material ItemMaterial = Material.getMaterial(Item);
			cLog.debug("Item:"+Item+" ItemStackNumber:"+ItemStackNumber+" ItemName:"+ItemName+" ItemLore:"+ItemLore+" ItemMaterial:"+ItemMaterial);
			if ((Item == null || ItemStackNumber == 0 || ItemMaterial == null)&&LoadConfig.JoinInv_null_erro) {
				cLog.info("config.yml - Inv's \""+i+"\" is Null. But this is not a error.");
				continue;
			}
			ItemStack ItemStack = new ItemStack(ItemMaterial, ItemStackNumber);
			if (ItemName != null)
			{
//				ItemName = ChatColor.translateAlternateColorCodes('&',ItemName);
				ItemName = ChangeMsg(ItemName, player);
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setDisplayName(ItemName);
				ItemStack.setItemMeta(ItemMeta);
			}
			if (ItemLore != null)
			{
				List<String> a = new ArrayList<>();
				for (String ItemLore_ : ItemLore)
				{
					a.add(ChatColor.translateAlternateColorCodes('&',ItemLore_));
				}
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setLore(a);
				ItemStack.setItemMeta(ItemMeta);
			}
			int SlotNumber = i - 1;
			if (LoadConfig.DebugMode)
			{
				cLog.Message(player,"SlotNumber: " + SlotNumber);
				if (ItemStack.getItemMeta().getLore() == null)
				{
					cLog.Message(player, "Lore == null");
				} else {
					cLog.Message(player, ItemStack.getItemMeta().getLore().toString());
				}
			}
			player.getInventory().setItem(SlotNumber, ItemStack);
		}
	}

	public void JoinMsg(CommandSender sender) {
		if (!(sender instanceof Player)) return; //user_msgがPlayerから以外の場合は切る。
		String pS = sender.getName();
		if (LoadConfig.JoinMessage_users(pS).isEmpty() || LoadConfig.JoinMessage_users(pS) == null) {
			//user_msgが存在しない時
			for (Player online_player : getServer().getOnlinePlayers()) {
				//共通メッセージをやる所
				if (!LoadConfig.JoinMessage_to_came_user && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_contents) {
					msg = ChangeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.JoinMessage_to_came_user && online_player == sender) continue;
				//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_users(pS)) {
					msg = ChangeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void LeaveMsg(CommandSender sender) {
		if (!(sender instanceof Player)) return; //user_msgがPlayerから以外の場合は切る。
		String pS = sender.getName();
		if (LoadConfig.LeaveMessage_users(pS).isEmpty() || LoadConfig.LeaveMessage_users(pS) == null) {
			//user_msgが存在しない時
			for (Player online_player : getServer().getOnlinePlayers()) {
				//共通メッセージをやる所
				if (!LoadConfig.LeaveMessage_to_came_user && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.LeaveMessage_contents) {
					msg = ChangeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.LeaveMessage_to_came_user && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.LeaveMessage_users(pS)) {
					msg = ChangeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void TellMsg (CommandSender sender, List<String> Msgs) {
		for (String msg : Msgs) {
			msg = ChangeMsg(msg, sender);
			sender.sendMessage(msg);
		}
	}

	public String ChangeMsg(String msg, CommandSender sender) //Msgの中のテンプレがあったら変更
	{
		msg = msg.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player) {
			Player player = (Player)sender;
			msg = msg.replaceAll("%World%", player.getWorld().getName());
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}

	public boolean isFirstJoin(Player p) {
		if (!LoadConfig.FirstJoin_use_YAML && !p.hasPlayedBefore()) {//Firstの時
			cLog.Message(p,"isPlayerFirstJoin1",3);
			return true;
		}
		if (LoadConfig.FirstJoin_use_YAML) {//YAMLを使用する時
			List<String> players = loadfile.getStringList("Players");
			for (String s : players) {
				if (s.equals(p.getName())) return false;
			}
			cLog.Message(p,"isPlayerFirstJoin2",3);
			players.add(p.getName());//Firstのプレーヤーを追加
			loadfile.set("Players", players);
			try {
				loadfile.save(file_isPlayerFirstJoin);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		cLog.Message(p,"isPlayerFirstJoin == false",3);
		return false;
	}
	public void FirstJoinMsg(CommandSender sender) {
		if (!(sender instanceof Player)) return; //user_msgがPlayerから以外の場合は切る。
		String pS = sender.getName();
		if (LoadConfig.FirstJoinMessage_users(pS).isEmpty() || LoadConfig.JoinMessage_users(pS) == null) {
			//user_msgが存在しない時
			for (Player online_player : getServer().getOnlinePlayers()) {
				//共通メッセージをやる所
				if (!LoadConfig.FirstJoinMessage_to_came_user && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.FirstJoinMessage_contents) {
					msg = ChangeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.JoinMessage_to_came_user && online_player == sender) continue;
				//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_users(pS)) {
					msg = ChangeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

//    public void FirstJoinMsg(CommandSender sender) {
//        if (!(sender instanceof Player)) return; //Playerのみ | user_msgがPlayerから以外の場合は切る。
//        String pS = sender.getName();
//        if (LoadConfig.FirstJoinMessage_users(pS).isEmpty()) {//設定されたメッセージが無い時(微妙仕様)
//            //共通(not user_msg)メッセージをやる所
//            if (LoadConfig.FirstJoinMessage_contents == null) {
//                if (LoadConfig.DebugMode) {
//                    cLog.Message(sender,"config.yml - Player is Null.",3);
//                }
//                return;
//            }
//            for (Player online_player : getServer().getOnlinePlayers()) {
//                if (!LoadConfig.FirstJoinMessage_to_came_user) //
//                {
//                    if (online_player == sender) {
//                        continue;
//                    }
//                    //鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
//                }
//                for (String msg : LoadConfig.FirstJoinMessage_contents) {
//                    msg = ChangeMsg(msg, sender);
//                    online_player.sendMessage(msg);
//                }
//            }
//        } else {//設定されたメッセージがある時(微妙仕様)
//            //user_msgをやる所
//            for (Player online_player : getServer().getOnlinePlayers()) {
//                if (!LoadConfig.FirstJoinMessage_to_came_user) //来た人にメッセージをやるか。
//                {
//                    if (online_player == sender) {
//                        continue;
//                    }
//                    //鯖にいるプレーヤーが来た人と同じになったらその人を飛ばす。
//                }
//                for (String msg : LoadConfig.FirstJoinMessage_users(pS)) {
//                    msg = ChangeMsg(msg, sender);
//                    online_player.sendMessage(msg);
//                }
//            }
//        }
//    }

//	public void FirstJoinTell(CommandSender sender)
//	{
//		for (String msg : LoadConfig.FirstJoinMessage_tell_contents)
//		{
//			msg = ChangeMsg(msg, sender);
//			sender.sendMessage(msg);
//		}
//	}

	public void sendCommandList(CommandSender sender) {
		cLog.Message(sender, "Command List!!");
		cLog.Message(sender, "TP you:/spawn | TP <Player>:/spawn <Player>");
		cLog.Message(sender, "Set spawn:/setspawn");
		cLog.Message(sender, "Long: /JoinLeaveAction | Short: /jla");
		cLog.Message(sender, "/jla reload");
		cLog.Message(sender, "/jla test <testing_thing_name>");
	}

	@Override
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
			Player player = (Player) sender;
			setLoc(player, "SpawnLoc");
			saveConfig();
			reloadConfig();
			cLog.Message(sender, "Spawn was set.");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) return false;
				if (!sender.hasPermission("JoinLeaveAction.cmd.spawn.me")) {
					cLog.Message(sender, "You have to send from Player.", 2);
					return false;
				}
				Player p = (Player) sender;
				cLog.Message(sender, this.loc.toString(), 3);
				if (LoadConfig.SpawnLoc().getWorld() == null) {
					return false;
				}
				p.teleport(this.loc);
				if (LoadConfig.SpawnTPMsg_me) {
					String SpawnTPedMsg_me = LoadConfig.SpawnTPMsg_me_contents;
					if (SpawnTPedMsg_me != null) {
						SpawnTPedMsg_me = ChangeMsg(SpawnTPedMsg_me, sender);
						cLog.Message(sender, SpawnTPedMsg_me);
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
					cLog.Message(sender, "That player dose not exsist.", 2);
					return false;
				}
				cLog.Message(sender, loc.toString(), 3);
				if (LoadConfig.SpawnLoc().getWorld() == null) {
					return false;
				}
				player.teleport(this.loc);
				if (LoadConfig.SpawnTPMsg_other) {
					String SpawnTPedMsg_other = LoadConfig.SpawnTPMsg_other_contents;
					if (SpawnTPedMsg_other != null) {
						SpawnTPedMsg_other = ChangeMsg(SpawnTPedMsg_other, sender);
						cLog.Message(sender, SpawnTPedMsg_other);
					}
				}
				return true;
			}
		} else if ((cmd.getName().equalsIgnoreCase("JoinLeaveAction")) || (cmd.getName().equalsIgnoreCase("jla"))) {
			if (!sender.hasPermission("JoinLeaveAction.cmd") && (sender instanceof Player)) {
				cLog.Message(sender, "You don't have Permission.(JoinLeaveAction.cmd)", 3);
				return false;
			}
			if (args.length == 0) {
				//jla(引数なし)。
				sendCommandList(sender);
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
//					getConfigFile();
					cLog.Message(sender, "config.yml is reload!");
					return true;
				}
				if (args[0].equalsIgnoreCase("test") && (sender instanceof Player)) {
					if (!sender.hasPermission("JoinLeaveAction.cmd.test")) {
						cLog.Message(sender, "You don't have Permission to use test.", 2);
						return false;
					}
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("JoinMsg")) {
							JoinMsg(sender);
							TellMsg(sender, LoadConfig.JoinMessage_contents);
							return true;
						}
						if (args[1].equalsIgnoreCase("LeaveMsg")) {
							LeaveMsg(sender);
							return true;
						}
						if (args[1].equalsIgnoreCase("Inv")) {
							Player player = (Player) sender;
							player.getInventory().clear();
							JoinInv(player);
							return true;

						}
					}
					cLog.Message(sender, "JoinMsg, LeaveMsg, Inv", 2);
					sendCommandList(sender);
					return false;
				}
				if (args[0].equalsIgnoreCase("help")) {
					sendCommandList(sender);
					return true;
				}
				cLog.Message(sender, "Command is erro.", 2);
				sendCommandList(sender);
				return false;
			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		cLog.debug("Start: onPlayerJoin | by Player:" + event.getPlayer().getName());
		if (LoadConfig.JoinMessage)
		{
			event.setJoinMessage("");
			CommandSender p = event.getPlayer();
			JoinMsg(p);
			TellMsg(p, LoadConfig.JoinMessage_tell_contents);
		}
		if (LoadConfig.JoinSpawn)
		{
			Player p = event.getPlayer();
			if (LoadConfig.SpawnLoc().getWorld() == null) return;
			cLog.debug(LoadConfig.SpawnLoc().toString());
			p.teleport(LoadConfig.SpawnLoc());
		}
		if (LoadConfig.JoinInv)
		{
			Player p = event.getPlayer();
			p.getInventory().clear();
			JoinInv(p);
		}
		if (LoadConfig.JoinMessage)
		{
			if (isFirstJoin(event.getPlayer()))
			{
				CommandSender p = event.getPlayer();
				cLog.debug("You are FistJoiner!");
				FirstJoinMsg(p);
				TellMsg(p, LoadConfig.FirstJoinMessage_contents);
			}
		}
		cLog.debug("Finish: onPlayerJoin | by Player:" + event.getPlayer().getName());
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		cLog.debug("Start: onPlayerLeave | by Player:" + event.getPlayer().getName());
		if (LoadConfig.LeaveMessage)
		{
			CommandSender p = event.getPlayer();
			event.setQuitMessage("");
			LeaveMsg(p);
		}
		cLog.debug("Finish: onPlayerLeave | by Player:" + event.getPlayer().getName());
	}
}

//TODO
//設定ファイル関係をまとめること。
//命名法則がクソ
