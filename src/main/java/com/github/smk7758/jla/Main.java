package com.github.smk7758.jla;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
	public FileIO FileIO = new FileIO(this);
	public ConsoleLog cLog = new ConsoleLog(this);
	public LoadConfig LoadConfig = new LoadConfig(this);

	public String PluginName = getDescription().getName();
	public String folder = getDataFolder() + File.separator;
	public boolean DebugMode = false;
	public String PluginPrefix = "[" + ChatColor.GREEN + PluginName + ChatColor.RESET +"] ";
	public String cPrefix = "["+ PluginName +"] ";
	public String pInfo =  "[" + ChatColor.RED+ "Info" + ChatColor.RESET+"] ";
	public String pError = "[" + ChatColor.RED+ "ERROR" + ChatColor.RESET+"] ";

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("setspawn").setExecutor(new CommandExcuter(this));
		getCommand("spawn").setExecutor(new CommandExcuter(this));
		getCommand("JoinLeaveAction").setExecutor(new CommandExcuter(this));
		saveDefaultConfig();
		saveConfig();
//		FileIO.makeSettingFiles("config");
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

	public void setLoc(Player player, String type) {
		String world = player.getWorld().getName();
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		float yaw = player.getLocation().getYaw();
		float pitch = player.getLocation().getPitch();
		FileIO.LocationIO(true, "config", type, world, x, y, z, yaw, pitch);
		cLog.sendMessage(player, "Player:" + player.getName(), 3);
		cLog.sendMessage(player, "World:" + world, 3);
		cLog.sendMessage(player, "X:" + x, 3);
		cLog.sendMessage(player, "Y:" + y, 3);
		cLog.sendMessage(player, "Z:" + z, 3);
		cLog.sendMessage(player, "Yaw:" + yaw, 3);
		cLog.sendMessage(player, "Pitch:" + pitch, 3);
	}

	public void Teleport(Player player, Location loc) {
		cLog.sendMessage(player, loc.toString(), 3);
		if (loc.getWorld() == null) {
			cLog.warn("Teleport did not work because the World is null.");
			cLog.sendMessage(player, "Teleport did not work because the World is null.", 2);
			return;
		}
		player.teleport(loc);
	}

	public void JoinInv(Player player) {
		for (int i = 0; i <= 8; i++) {
			String Item = (String)LoadConfig.JoinInv_contents(i).get("Item");
			int ItemStackNumber = (Integer) LoadConfig.JoinInv_contents(i).get("ItemStackNumber");
			String ItemName = (String)LoadConfig.JoinInv_contents(i).get("ItemName");
			@SuppressWarnings("unchecked")
			List<String> ItemLore = (List<String>)LoadConfig.JoinInv_contents(i).get("ItemLore"); //取得時に検査済み

			Material ItemMaterial = Material.getMaterial(Item);
			cLog.sendMessage(player, "SlotNumber(InFor/config): "+i,3);
			cLog.sendMessage(player, "Item:"+Item+" ItemStackNumber:"+ItemStackNumber+" ItemName:"+ItemName+" ItemLore:"+ItemLore+" ItemMaterial:"+ItemMaterial, 3);
			if (Item == null || ItemStackNumber == 0 || ItemMaterial == null) {
				if (LoadConfig.JoinInv_null_erro()) {
					cLog.info("config.yml - Inv's \""+i+"\" is Null. But this is not a error.");
					continue;
				}
				cLog.sendMessage(player, "config.yml - Inv's \""+i+"\" is Null. But this is not a error.", 3);
				continue;
			}
			ItemStack ItemStack = new ItemStack(ItemMaterial, ItemStackNumber);
			if (ItemName != null) { //ItemNameを設定する
				ItemName = changeMsg(ItemName, player);
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setDisplayName(ItemName);
				ItemStack.setItemMeta(ItemMeta);
			}
			if (ItemLore != null) { //ItemLoreを設定する
				List<String> ItemLore_List = new ArrayList<String>();
				for (String ItemLore_String : ItemLore) {
					ItemLore_List.add(changeMsg(ItemLore_String, player));
				}
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setLore(ItemLore_List);
				ItemStack.setItemMeta(ItemMeta);
			}
			player.getInventory().setItem(i, ItemStack);
		}
	}

	public void JoinMsg(CommandSender sender) {
		if (!(sender instanceof Player)) return; //user_msgがPlayerから以外の場合は切る。
		String pS = sender.getName();
		if (LoadConfig.JoinMessage_users(pS).isEmpty() || LoadConfig.JoinMessage_users(pS) == null) {
			//user_msgが存在しない時
			for (Player online_player : getServer().getOnlinePlayers()) {
				//共通メッセージをやる所
				if (!LoadConfig.JoinMessage_to_came_user() && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_contents()) {
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.JoinMessage_to_came_user() && online_player == sender) continue;
				//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_users(pS)) {
					msg = changeMsg(msg, sender);
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
				if (!LoadConfig.LeaveMessage_to_came_user() && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.LeaveMessage_contents()) {
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.LeaveMessage_to_came_user() && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.LeaveMessage_users(pS)) {
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void tellMsg (CommandSender sender, List<String> msgs) {
		for (String msg : msgs) {
			msg = changeMsg(msg, sender);
			sender.sendMessage(msg);
		}
	}

	public String changeMsg(String msg, CommandSender sender) {//Msgの中のテンプレがあったら変更
		msg = msg.replaceAll("%Player%", sender.getName());
		if (sender instanceof Player) {
			Player player = (Player)sender;
			msg = msg.replaceAll("%World%", player.getWorld().getName());
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}

	public boolean isFirstJoin(Player player) {
	//ifFisrtJoin.yamlをFileIOへ
	if (!LoadConfig.FirstJoin_use_YAML() && !player.hasPlayedBefore()) {//Firstの時
		cLog.sendMessage(player, "isPlayerFirstJoin1", 3);
		return true;
	}
	if (LoadConfig.FirstJoin_use_YAML()) {//YAMLを使用する時
		List<String> players = FileIO.StringListIO(false, "isFirstJoin", "Players", null);
		for (String s : players) {
			if (s.equals(player.getName())) return false;
		}
		cLog.sendMessage(player, "isFirstJoin2", 3);
		players.add(player.getName());//Firstのプレーヤーを追加
		FileIO.StringListIO(true, "isFirstJoin", "Players", players);
	}
	cLog.sendMessage(player,"isPlayerFirstJoin == false",3);
	return false;
}

	public void FirstJoinMsg(CommandSender sender) {
		if (!(sender instanceof Player)) return; //user_msgがPlayerから以外の場合は切る。
		String pS = sender.getName();
		if (LoadConfig.FirstJoinMessage_users(pS).isEmpty() || LoadConfig.JoinMessage_users(pS) == null) {
			//user_msgが存在しない時
			for (Player online_player : getServer().getOnlinePlayers()) {
				//共通メッセージをやる所
				if (!LoadConfig.FirstJoinMessage_to_came_user() && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.FirstJoinMessage_contents()) {
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.JoinMessage_to_came_user() && online_player == sender) continue;
				//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_users(pS)) {
					msg = changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

// =====以下置き換え前====
//	public boolean isFirstJoin(Player p) {
//	//ifFisrtJoin.yamlをFileIOへ
//	if (!LoadConfig.FirstJoin_use_YAML && !p.hasPlayedBefore()) {//Firstの時
//		cLog.debug("isPlayerFirstJoin1");
//		return true;
//	}
//	if (LoadConfig.FirstJoin_use_YAML) {//YAMLを使用する時
//		List<String> players = loadfile.getStringList("Players");
//		for (String s : players) {
//			if (s.equals(p.getName())) return false;
//		}
//		cLog.Message(p,"isPlayerFirstJoin2",3);
//		players.add(p.getName());//Firstのプレーヤーを追加
//		loadfile.set("Players", players);
//		try {
//			loadfile.save(file_isFirstJoin);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
//	cLog.Message(p,"isPlayerFirstJoin == false",3);
//	return false;
//}
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
// =====以上置き換え前=====

	public void sendCommandList(CommandSender sender) {
		cLog.sendMessage(sender, "Command List!!");
		cLog.sendMessage(sender, "TP you:/spawn | TP <Player>:/spawn <Player>");
		cLog.sendMessage(sender, "Set spawn:/setspawn");
		cLog.sendMessage(sender, "Long: /JoinLeaveAction | Short: /jla");
		cLog.sendMessage(sender, "/jla reload");
		cLog.sendMessage(sender, "/jla test <testing_thing_name>");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		cLog.sendMessage(player, "Start: onPlayerJoin | by Player:" + event.getPlayer().getName(), 3);
		if (LoadConfig.JoinMessage())
		{
			event.setJoinMessage("");
			JoinMsg(player);
			tellMsg(player, LoadConfig.JoinMessage_tell_contents());
		}
		if (LoadConfig.JoinSpawn())
		{
			if (LoadConfig.SpawnLoc().getWorld() == null) return;
			cLog.sendMessage(player, LoadConfig.SpawnLoc().toString(), 3);
			player.teleport(LoadConfig.SpawnLoc());
		}
		if (LoadConfig.JoinInv())
		{
			player.getInventory().clear();
			JoinInv(player);
		}
		if (LoadConfig.JoinMessage())
		{
			if (isFirstJoin(event.getPlayer()))
			{
				cLog.sendMessage(player, "You are FistJoiner!", 3);
				FirstJoinMsg(player);
				tellMsg(player, LoadConfig.FirstJoinMessage_contents());
			}
		}
		cLog.sendMessage(player, "Finish: onPlayerJoin | by Player:" + event.getPlayer().getName(), 3);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		cLog.sendMessage(player, "Start: onPlayerLeave | by Player:" + event.getPlayer().getName(), 3);
		if (LoadConfig.LeaveMessage())
		{
			event.setQuitMessage("");
			LeaveMsg(player);
		}
		cLog.sendMessage(player, "Finish: onPlayerLeave | by Player:" + event.getPlayer().getName(), 3);
	}
}

//TODO
//設定ファイル関係をまとめること。
//命名法則:
//基本的に小文字から始めるけどまぁ適当に。
//Player playerは単純なものをpとし、他は"名前"または"役割" + _playerとする。
//Message関連は基本的にmsg。List<String>はmsgsとする。
