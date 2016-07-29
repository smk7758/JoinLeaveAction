package com.github.smk7758.jla;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class LoadConfig
{
	Main plugin;
	FileIO FileIO;
	public LoadConfig(Main instance) {
		this.plugin = instance;
	}

	String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + Bukkit.getName() + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	static String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + Bukkit.getName() + ChatColor.WHITE + "] " + ChatColor.WHITE;

	public boolean DebugMode = FileIO.DebugIO(false,true);
	public boolean FirstJoin = FileIO.BooleanIO(false,"FirstJoin", "config", true);
	public boolean FirstJoin_use_YAML = FileIO.BooleanIO(false,"config", "FirstJoin_use_YAML", true);
	public Location SpawnLoc() {
		short i = (short)0; //埋め文字
		return FileIO.LocationIO(false, "config", "SpawnLoc", null, i, i, i, i, i);
	}
	public boolean SpawnTPMsg_me = FileIO.BooleanIO(false, "config", "SpawnTPMsg_me", true);
	public String SpawnTPMsg_me_contents = FileIO.StringIO(1, "config", "SpawnTPMsg_me_contents", null);
	public boolean SpawnTPMsg_other =  FileIO.BooleanIO(false, "config", "SpawnTPMsg_me_other", true);
	public String SpawnTPMsg_other_contents = FileIO.StringIO(1, "config", "SpawnTPMsg_other_contents", null);
	public boolean JoinSpawn =  FileIO.BooleanIO(false, "config", "JoinSpawn", true);
	public boolean FirstJoinSpawn =  FileIO.BooleanIO(false, "config", "FirstJoinSpawn", true);
	public Location FirstJoinLoc() {
		short i = (short)0; //埋め文字
		return FileIO.LocationIO(false, "config", "FirstLoc", null, i, i, i, i, i);
	}
	public boolean FirstJoinOnly = FileIO.BooleanIO(false, "config", "FirstJoinOnly", true);
	public boolean JoinInv = FileIO.BooleanIO(false, "config", "JoinInv", true);
//JoinInv_contentsはパス(増大してい行く奴)
	public HashMap<String, Object> JoinInv_contents(int i) {
		return FileIO.ItemIO(false, "config", "JoinInv_contents." + i, null);
	}
	public boolean JoinInv_null_erro = FileIO.BooleanIO(false, "config", "JoinInv_null_erro", true);
	public boolean LeaveInv_clear = FileIO.BooleanIO(false, "config", "LeaveInv_clear", true);
	public boolean FirstJoinMessage = FileIO.BooleanIO(false, "config", "FirstJoinMessage", true);
	public List<String> FirstJoinMessage_contents = FileIO.StringListIO(1, "config", "FirstJoinMessage_contents", null);
	public List<String> FirstJoinMessage_users(String player) {
		return FileIO.StringListIO(1, "config", "FirstJoinMessage_users." + player, null);
	}
//FirstJoinMessage_usersはプレーヤー名が必要なため。
	public boolean FirstJoinMessage_tell = FileIO.BooleanIO(false, "config", "FirstJoinMessage_tell", true);
	public List<String> FirstJoinMessage_tell_contents = FileIO.StringListIO(1, "config", "FirstJoinMessage_tell_contents", null);
	public boolean JoinMessage = FileIO.BooleanIO(false, "config", "JoinMessage", true);
	public List<String> JoinMessage_contents = FileIO.StringListIO(1, "config", "JoinMessage_contents", null);
	public List<String> JoinMessage_users(String player) {
		return FileIO.StringListIO(1, "config", "JoinMessage_users." + player, null);
	}
	public boolean FirstJoinMessage_to_came_user = FileIO.BooleanIO(false, "config", "FirstJoinMessage_to_come_user", true);
//JoinMessage_usersはプレーヤー名が必要なため。
	public boolean JoinMessage_tell = FileIO.BooleanIO(false, "config", "JoinMessage_tell", true);
	public List<String> JoinMessage_tell_contents = FileIO.StringListIO(1, "config", "JoinMessage_tell_contents", null);
	public boolean JoinMessage_to_came_user = FileIO.BooleanIO(false, "config", "JoinMessage_to_came_user", true);
	public boolean LeaveMessage = FileIO.BooleanIO(false, "config", "LeaveMessage", true);
	public List<String> LeaveMessage_contents = FileIO.StringListIO(1, "config", "LeaveMessage_contents", null);
	public List<String> LeaveMessage_users(String player) {
		return FileIO.StringListIO(1, "config", "LeaveMessage_users." + player, null);
	}
//LeaveMessage_usersはプレーヤー名が必要なため。
	public boolean LeaveMessage_to_came_user = FileIO.BooleanIO(false, "config", "LeaveMEssage_to_came_user", true);
}
