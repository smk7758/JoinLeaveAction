package com.github.smk7758.jla;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

public class LoadConfig {
	private Main plugin;
	public LoadConfig(Main instance) {
		plugin = instance;
	}

	public boolean DebugMode = plugin.FileIO.DebugIO(false, true);
	public boolean FirstJoin = plugin.FileIO.BooleanIO(false,"FirstJoin", "config", true);
	public boolean FirstJoin_use_YAML = plugin.FileIO.BooleanIO(false,"config", "FirstJoin_use_YAML", true);
	public Location SpawnLoc() {
		short i = (short)0; //埋め文字
		return plugin.FileIO.LocationIO(false, "config", "SpawnLoc", null, i, i, i, i, i);
	}
	public boolean SpawnTPMsg_me = plugin.FileIO.BooleanIO(false, "config", "SpawnTPMsg_me", true);
	public String SpawnTPMsg_me_contents = plugin.FileIO.StringIO(1, "config", "SpawnTPMsg_me_contents", null);
	public boolean SpawnTPMsg_other =  plugin.FileIO.BooleanIO(false, "config", "SpawnTPMsg_me_other", true);
	public String SpawnTPMsg_other_contents = plugin.FileIO.StringIO(1, "config", "SpawnTPMsg_other_contents", null);
	public boolean JoinSpawn =  plugin.FileIO.BooleanIO(false, "config", "JoinSpawn", true);
	public boolean FirstJoinSpawn =  plugin.FileIO.BooleanIO(false, "config", "FirstJoinSpawn", true);
	public Location FirstJoinLoc() {
		short i = (short)0; //埋め文字
		return plugin.FileIO.LocationIO(false, "config", "FirstLoc", null, i, i, i, i, i);
	}
	public boolean FirstJoinOnly = plugin.FileIO.BooleanIO(false, "config", "FirstJoinOnly", true);
	public boolean JoinInv = plugin.FileIO.BooleanIO(false, "config", "JoinInv", true);
	public HashMap<String, Object> JoinInv_contents(int i) {
		return plugin.FileIO.ItemIO(false, "config", "JoinInv_contents." + i, null);
	}
	public boolean JoinInv_null_erro = plugin.FileIO.BooleanIO(false, "config", "JoinInv_null_erro", true);
	public boolean LeaveInv_clear = plugin.FileIO.BooleanIO(false, "config", "LeaveInv_clear", true);
	public boolean FirstJoinMessage = plugin.FileIO.BooleanIO(false, "config", "FirstJoinMessage", true);
	public List<String> FirstJoinMessage_contents = plugin.FileIO.StringListIO(1, "config", "FirstJoinMessage_contents", null);
	public List<String> FirstJoinMessage_users(String player) {
		return plugin.FileIO.StringListIO(1, "config", "FirstJoinMessage_users." + player, null);
	}
	public boolean FirstJoinMessage_tell = plugin.FileIO.BooleanIO(false, "config", "FirstJoinMessage_tell", true);
	public List<String> FirstJoinMessage_tell_contents = plugin.FileIO.StringListIO(1, "config", "FirstJoinMessage_tell_contents", null);
	public boolean JoinMessage = plugin.FileIO.BooleanIO(false, "config", "JoinMessage", true);
	public List<String> JoinMessage_contents = plugin.FileIO.StringListIO(1, "config", "JoinMessage_contents", null);
	public List<String> JoinMessage_users(String player) {
		return plugin.FileIO.StringListIO(1, "config", "JoinMessage_users." + player, null);
	}
	public boolean FirstJoinMessage_to_came_user = plugin.FileIO.BooleanIO(false, "config", "FirstJoinMessage_to_come_user", true);
	public boolean JoinMessage_tell = plugin.FileIO.BooleanIO(false, "config", "JoinMessage_tell", true);
	public List<String> JoinMessage_tell_contents = plugin.FileIO.StringListIO(1, "config", "JoinMessage_tell_contents", null);
	public boolean JoinMessage_to_came_user = plugin.FileIO.BooleanIO(false, "config", "JoinMessage_to_came_user", true);
	public boolean LeaveMessage = plugin.FileIO.BooleanIO(false, "config", "LeaveMessage", true);
	public List<String> LeaveMessage_contents = plugin.FileIO.StringListIO(1, "config", "LeaveMessage_contents", null);
	public List<String> LeaveMessage_users(String player) {
		return plugin.FileIO.StringListIO(1, "config", "LeaveMessage_users." + player, null);
	}
	public boolean LeaveMessage_to_came_user = plugin.FileIO.BooleanIO(false, "config", "LeaveMEssage_to_came_user", true);
}
