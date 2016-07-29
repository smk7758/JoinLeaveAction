package com.github.smk7758.jla;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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
	//static FileConfiguration conf = null;
	public String folder = getDataFolder() + File.separator;

	public ConsoleLog cLog = new ConsoleLog(this);
	public FileIO FileIO = new FileIO(this);
	public LoadConfig LoadConfig = new LoadConfig(this);
	public Commander Commander = new Commander(this);
	public boolean DebugMode = false;

	//File file_isPlayerFirstJoin = new File(folder + "isPlayerFirstJoin.yml");
	//FileConfiguration yaml = new YamlConfiguration();
	//public FileConfiguration loadfile = YamlConfiguration.loadConfiguration(file_isPlayerFirstJoin);
	//Location loc;
	//static Server server = null;
	//public String filepath = getDataFolder() + File.separator;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		//server = getServer();
		saveConfig();
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

//	public FileConfiguration getConfigFile() {
//		FileConfiguration conf = new YamlConfiguration();
//		File file = new File(folder, "config.yml");
//		try {
//		conf.load(file);
//		} catch (IOException | InvalidConfigurationException e) {
//			e.printStackTrace();
//		}
//		return conf;
//	}

//	public void setConfig() {
//		getConfig().options().copyDefaults(true);
//		saveConfig();
//		reloadConfig();
//	}

	public void setLoc(Player p, String type) {
		World world = p.getWorld();
		Short x = (short)p.getLocation().getX();
		Short y = (short)p.getLocation().getY();
		Short z = (short)p.getLocation().getZ();
		Short yaw = (short)p.getLocation().getYaw();
		Short pitch = (short)p.getLocation().getPitch();
		FileIO.LocationIO(true, "config", type, world, x, y, z, yaw, pitch);
		cLog.Message(p, "Player:" + p.getName(), 3);
		cLog.Message(p, "World:" + world, 3);
		cLog.Message(p, "X:" + String.valueOf(x), 3);
		cLog.Message(p, "Y:" + String.valueOf(y), 3);
		cLog.Message(p, "Z:" + String.valueOf(z), 3);
		cLog.Message(p, "Yaw:" + String.valueOf(yaw), 3);
		cLog.Message(p, "Pitch:" + String.valueOf(pitch), 3);
	}

	public void Teleport(Player p, Location loc) {
		cLog.Message(p, loc.toString(), 3);
		if (loc.getWorld() == null) {
			cLog.warn("Teleport did not work because the World is null.");
			return;
		}
		p.teleport(loc);
	}

	public void JoinInv(Player p) {//見てない
		for (int i = 1; i <= 9; i++) {
			String Item = (String)LoadConfig.JoinInv_contents(i).get("Item");
			int ItemStackNumber = (int)LoadConfig.JoinInv_contents(i).get("ItemStackNumber");
			String ItemName = (String)LoadConfig.JoinInv_contents(i).get("ItemName");
			List<String> ItemLore = (List<String>)LoadConfig.JoinInv_contents(i).get("ItemLore"); //取得時に検査済み
			Material ItemMaterial = Material.getMaterial(Item);
			int SlotNumber = i--; //0の時がInvの左下となるため--する。
			cLog.Message(p, "SlotNumber(InFor/config): "+i+" SlotNumber(Inv): "+SlotNumber,3);
			cLog.Message(p, "Item:"+Item+" ItemStackNumber:"+ItemStackNumber+" ItemName:"+ItemName+" ItemLore:"+ItemLore+" ItemMaterial:"+ItemMaterial, 3);
			if (Item == null || ItemStackNumber == 0 || ItemMaterial == null) {
				if (LoadConfig.JoinInv_null_erro) {
					cLog.info("config.yml - Inv's \""+i+"\" is Null. But this is not a error.");
					continue;
				}
				cLog.Message(p, "config.yml - Inv's \""+i+"\" is Null. But this is not a error.", 3);
				continue;
			}
			ItemStack ItemStack = new ItemStack(ItemMaterial, ItemStackNumber);
			if (ItemName != null) { //ItemNameを設定する
				ItemName = ChangeMsg(ItemName, p);
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setDisplayName(ItemName);
				ItemStack.setItemMeta(ItemMeta);
			}
			if (ItemLore != null) { //ItemLoreを設定する
				List<String> a = new ArrayList<>();
				for (String ItemLore_ : ItemLore) {
					a.add(ChangeMsg(ItemLore_, p));
				}
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setLore(a);
				ItemStack.setItemMeta(ItemMeta);
			}
			p.getInventory().setItem(SlotNumber, ItemStack);
			//何であるかわからない
//			if (LoadConfig.DebugMode)
//			{
//				cLog.Message(p,"SlotNumber: " + SlotNumber, 3);
//				if (ItemStack.getItemMeta().getLore() == null)
//				{
//					cLog.Message(p, "Lore == null");
//				} else {
//					cLog.Message(p, ItemStack.getItemMeta().getLore().toString());
//				}
//			}
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

	public void TellMsg (CommandSender sender, List<String> msgs) {
		for (String msg : msgs) {
			msg = ChangeMsg(msg, sender);
			sender.sendMessage(msg);
		}
	}

	public String ChangeMsg(String msg, CommandSender sender) {//Msgの中のテンプレがあったら変更
		msg = msg.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player) {
			Player p = (Player)sender;
			msg = msg.replaceAll("%World%", p.getWorld().getName());
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}

	public boolean isFirstJoin(Player p) {
	//ifFisrtJoin.yamlをFileIOへ
	if (!LoadConfig.FirstJoin_use_YAML && !p.hasPlayedBefore()) {//Firstの時
		cLog.Message(p, "isPlayerFirstJoin1", 3);
		return true;
	}
	if (LoadConfig.FirstJoin_use_YAML) {//YAMLを使用する時
		List<String> players = FileIO.StringListIO(1, "isFirstJoin", "Players", null);
		for (String s : players) {
			if (s.equals(p.getName())) return false;
		}
		cLog.Message(p, "isFirstJoin2", 3);
		players.add(p.getName());//Firstのプレーヤーを追加
		FileIO.StringListIO(0, "isFirstJoin", "Players", players);
	}
	cLog.Message(p,"isPlayerFirstJoin == false",3);
	return false;
}

//	public boolean isFirstJoin(Player p) {
//		//ifFisrtJoin.yamlをFileIOへ
//		if (!LoadConfig.FirstJoin_use_YAML && !p.hasPlayedBefore()) {//Firstの時
//			cLog.debug("isPlayerFirstJoin1");
//			return true;
//		}
//		if (LoadConfig.FirstJoin_use_YAML) {//YAMLを使用する時
//			List<String> players = loadfile.getStringList("Players");
//			for (String s : players) {
//				if (s.equals(p.getName())) return false;
//			}
//			cLog.Message(p,"isPlayerFirstJoin2",3);
//			players.add(p.getName());//Firstのプレーヤーを追加
//			loadfile.set("Players", players);
//			try {
//				loadfile.save(file_isFirstJoin);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return true;
//		}
//		cLog.Message(p,"isPlayerFirstJoin == false",3);
//		return false;
//	}

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

// =====以下置き換え前====
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
// =====ここまで置き換え前=====

	public void sendCommandList(CommandSender sender) {
		cLog.Message(sender, "Command List!!");
		cLog.Message(sender, "TP you:/spawn | TP <Player>:/spawn <Player>");
		cLog.Message(sender, "Set spawn:/setspawn");
		cLog.Message(sender, "Long: /JoinLeaveAction | Short: /jla");
		cLog.Message(sender, "/jla reload");
		cLog.Message(sender, "/jla test <testing_thing_name>");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		cLog.Message(p, "Start: onPlayerJoin | by Player:" + event.getPlayer().getName(), 3);
		if (LoadConfig.JoinMessage)
		{
			event.setJoinMessage("");
			JoinMsg(p);
			TellMsg(p, LoadConfig.JoinMessage_tell_contents);
		}
		if (LoadConfig.JoinSpawn)
		{
			if (LoadConfig.SpawnLoc().getWorld() == null) return;
			cLog.Message(p, LoadConfig.SpawnLoc().toString(), 3);
			p.teleport(LoadConfig.SpawnLoc());
		}
		if (LoadConfig.JoinInv)
		{
			p.getInventory().clear();
			JoinInv(p);
		}
		if (LoadConfig.JoinMessage)
		{
			if (isFirstJoin(event.getPlayer()))
			{
				cLog.Message(p, "You are FistJoiner!", 3);
				FirstJoinMsg(p);
				TellMsg(p, LoadConfig.FirstJoinMessage_contents);
			}
		}
		cLog.Message(p, "Finish: onPlayerJoin | by Player:" + event.getPlayer().getName(), 3);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		cLog.Message(p, "Start: onPlayerLeave | by Player:" + event.getPlayer().getName(), 3);
		if (LoadConfig.LeaveMessage)
		{
			event.setQuitMessage("");
			LeaveMsg(p);
		}
		cLog.Message(p, "Finish: onPlayerLeave | by Player:" + event.getPlayer().getName(), 3);
	}
}

//TODO
//設定ファイル関係をまとめること。
//命名法則:
//基本的に小文字から始めるけどまぁ適当に。
//Player playerは単純なものをpとし、他は"名前"または"役割" + _playerとする。
//Message関連は基本的にmsg。List<String>はmsgsとする。
