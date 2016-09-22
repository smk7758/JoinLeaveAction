package com.github.smk7758.jla;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.bukkit.ChatColor;
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
	public Util Util = new Util(this);

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
	}

	@Override
	public void onDisable() {
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
				ItemName = Util.changeMsg(ItemName, player);
				ItemMeta ItemMeta = ItemStack.getItemMeta();
				ItemMeta.setDisplayName(ItemName);
				ItemStack.setItemMeta(ItemMeta);
			}
			if (ItemLore != null) { //ItemLoreを設定する
				List<String> ItemLore_List = new ArrayList<String>();
				for (String ItemLore_String : ItemLore) {
					ItemLore_List.add(Util.changeMsg(ItemLore_String, player));
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
					msg = Util.changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.JoinMessage_to_came_user() && online_player == sender) continue;
				//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_users(pS)) {
					msg = Util.changeMsg(msg, sender);
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
					msg = Util.changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.LeaveMessage_to_came_user() && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.LeaveMessage_users(pS)) {
					msg = Util.changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
	}

	public void FirstJoinMsg(CommandSender sender) {
		if (!(sender instanceof Player)) return; //user_msgがPlayerから以外の場合は切る。
		String name_player = sender.getName();
		if (LoadConfig.FirstJoinMessage_users(name_player).isEmpty() || LoadConfig.JoinMessage_users(name_player) == null) {
			//user_msgが存在しない時
			for (Player online_player : getServer().getOnlinePlayers()) {
				//共通メッセージをやる所
				if (!LoadConfig.FirstJoinMessage_to_came_user() && online_player == sender) continue;
					//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.FirstJoinMessage_contents()) {
					msg = Util.changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		} else {
			//user_msgをやる所(user_msgが存在する)
			for (Player online_player : getServer().getOnlinePlayers()) {
				if (!LoadConfig.JoinMessage_to_came_user() && online_player == sender) continue;
				//来た人にメッセージを送るか」が有効になっていない時、online_player(メッセージ送信を順次行う枠) == 送った人 を飛ばす
				for (String msg : LoadConfig.JoinMessage_users(name_player)) {
					msg = Util.changeMsg(msg, sender);
					online_player.sendMessage(msg);
				}
			}
		}
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
			Util.tellMsg(player, LoadConfig.JoinMessage_tell_contents());
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
				Util.tellMsg(player, LoadConfig.FirstJoinMessage_contents());
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
// 基本的に小文字から始めるけどまぁ適当に。
// Player playerで！、他は"名前"または"役割" + _playerとする。
// Message関連は基本的にmsg。List<String>はmsgsとする。
