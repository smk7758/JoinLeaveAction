package com.github.smk7758.jla;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

public class LoadConfig {
	private Main plugin;
	public LoadConfig(Main instance) {
		plugin = instance;
	}

	/** デバッグモードを有効にをするか*/
	public boolean DebugMode() {
		return plugin.FileIO.DebugIO(false, true);
	}
	/** 初めてのログインの機能を有効にするか*/
	public boolean FirstJoin() {
		return plugin.FileIO.BooleanIO(false,"FirstJoin", "config", true);
	}
	/** FirstJoinの判定にYAMLファイルを用いるか */
	public boolean FirstJoin_use_YAML() {
		return plugin.FileIO.BooleanIO(false,"config", "FirstJoin_use_YAML", true);
	}
	/** SpawnLoc(JoinSpawn,/spawnの設定) */
	public Location SpawnLoc() {
		short i = (short)0; //埋め文字
		return plugin.FileIO.LocationIO(false, "config", "SpawnLoc", null, i, i, i, i, i);
	}
	/** /spawnした後に表示するメッセージの機能を有効にするか。不要説。 */
	public boolean SpawnTPMsg_me() {
		return plugin.FileIO.BooleanIO(false, "config", "SpawnTPMsg_me", true);
	}
	/** /spawnした後に表示するメッセージ。 */
	public String SpawnTPMsg_me_contents() {
		return plugin.FileIO.StringIO(false, "config", "SpawnTPMsg_me_contents", null);
	}
	/** /spawn <Player>した後に表示するメッセージを有効にするか。不要説。*/
	public boolean SpawnTPMsg_other() {
		return plugin.FileIO.BooleanIO(false, "config", "SpawnTPMsg_me_other", true);
	}
	/** /spawn <Player>した後に表示するメッセージ。%Other%は実行した人。 */
	public String SpawnTPMsg_other_contents() {
		return plugin.FileIO.StringIO(false, "config", "SpawnTPMsg_other_contents", null);
	}
	/** ログイン時にスポーンポイントにTPするか。 */
	public boolean JoinSpawn() {
		return plugin.FileIO.BooleanIO(false, "config", "JoinSpawn", true);
	}

	public boolean FirstJoinSpawn() {
		return plugin.FileIO.BooleanIO(false, "config", "FirstJoinSpawn", true);
	}

	public Location FirstJoinLoc() {
		short i = (short)0; //埋め文字
		return plugin.FileIO.LocationIO(false, "config", "FirstLoc", null, i, i, i, i, i);
	}

	public boolean FirstJoinOnly() {
		return plugin.FileIO.BooleanIO(false, "config", "FirstJoinOnly", true);
	}

	public boolean JoinInv() {
		return plugin.FileIO.BooleanIO(false, "config", "JoinInv", true);
	}

	public HashMap<String, Object> JoinInv_contents(int i) {
		return plugin.FileIO.ItemIO(false, "config", "JoinInv_contents." + i, null);
	}

	public boolean JoinInv_null_erro() {
		return plugin.FileIO.BooleanIO(false, "config", "JoinInv_null_erro", true);
	}

	public boolean LeaveInv_clear() {
		return plugin.FileIO.BooleanIO(false, "config", "LeaveInv_clear", true);
	}

	public boolean FirstJoinMessage() {
		return plugin.FileIO.BooleanIO(false, "config", "FirstJoinMessage", true);
	}

	public List<String> FirstJoinMessage_contents() {
		return plugin.FileIO.StringListIO(false, "config", "FirstJoinMessage_contents", null);
	}

	public List<String> FirstJoinMessage_users(String player) {
		return plugin.FileIO.StringListIO(false, "config", "FirstJoinMessage_users." + player, null);
	}

	public boolean FirstJoinMessage_tell() {
		return plugin.FileIO.BooleanIO(false, "config", "FirstJoinMessage_tell", true);
	}

	public List<String> FirstJoinMessage_tell_contents() {
		return plugin.FileIO.StringListIO(false, "config", "FirstJoinMessage_tell_contents", null);
	}

	public boolean JoinMessage() {
		return plugin.FileIO.BooleanIO(false, "config", "JoinMessage", true);
	}

	public List<String> JoinMessage_contents() {
		return plugin.FileIO.StringListIO(false, "config", "JoinMessage_contents", null);
	}

	public List<String> JoinMessage_users(String player) {
		return plugin.FileIO.StringListIO(false, "config", "JoinMessage_users." + player, null);
	}

	public boolean FirstJoinMessage_to_came_user() {
		return plugin.FileIO.BooleanIO(false, "config", "FirstJoinMessage_to_come_user", true);
	}

	public boolean JoinMessage_tell() {
		return plugin.FileIO.BooleanIO(false, "config", "JoinMessage_tell", true);
	}

	public List<String> JoinMessage_tell_contents() {
		return plugin.FileIO.StringListIO(false, "config", "JoinMessage_tell_contents", null);
	}

	public boolean JoinMessage_to_came_user() {
		return plugin.FileIO.BooleanIO(false, "config", "JoinMessage_to_came_user", true);
	}

	public boolean LeaveMessage() {
		return plugin.FileIO.BooleanIO(false, "config", "LeaveMessage", true);
	}

	public List<String> LeaveMessage_contents() {
		return plugin.FileIO.StringListIO(false, "config", "LeaveMessage_contents", null);
	}

	public List<String> LeaveMessage_users(String player) {
		return plugin.FileIO.StringListIO(false, "config", "LeaveMessage_users." + player, null);
	}

	public boolean LeaveMessage_to_came_user() {
		return plugin.FileIO.BooleanIO(false, "config", "LeaveMEssage_to_came_user", true);
	}
}
