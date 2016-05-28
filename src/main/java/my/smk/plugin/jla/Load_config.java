package my.smk.plugin.jla;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class Load_config {
	private Main plugin;


	String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + plugin.getDescription().getName() + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + plugin.getDescription().getName() + ChatColor.WHITE + "] " + ChatColor.WHITE;
	public boolean Debug_mode = plugin.conf.getBoolean("Debug_mode");
	public boolean FirstJoin = plugin.conf.getBoolean("FirstJoin");
	public boolean FirstJoin_use_YAML = plugin.conf.getBoolean("FirstJoin_use_YAML");
//	public boolean FirstJoin_use_UUID = plugin.conf.getBoolean("FirstJoin_use_UUID");
	public Location SpawnLoc()
	{
		Location loc;
		World pl_world = plugin.getServer().getWorld(plugin.conf.getString("SpawnLoc.World"));
		double pl_x = plugin.conf.getDouble("SpawnLoc.x");
		double pl_y = plugin.conf.getDouble("SpawnLoc.y");
		double pl_z = plugin.conf.getDouble("SpawnLoc.z");
		float pl_Yaw = plugin.conf.getInt("SpaawnLoc.Yaw");
		float pl_Pitch = plugin.conf.getInt("SpawnLoc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "conf - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public boolean SpawnTPMsg_me = plugin.conf.getBoolean("SpawnTPMsg_me");
	public String SpawnTPMsg_me_contents = plugin.conf.getString("SpawnTPMsg_me_contents");
	public boolean SpawnTPMsg_other = plugin.conf.getBoolean("SpawnTPMsg_other");
	public String SpawnTPMsg_other_contents = plugin.conf.getString("SpawnTPMsg_other_contents");
	public boolean JoinSpawn = plugin.conf.getBoolean("JoinSpawn");
	public boolean FirstJoinSpawn = plugin.conf.getBoolean("FirstJoinSpawn");
	public Location FirstJoinLoc()
	{
		Location loc;
		World pl_world = plugin.getServer().getWorld(plugin.conf.getString("Loc.World"));
		double pl_x = plugin.conf.getDouble("Loc.x");
		double pl_y = plugin.conf.getDouble("Loc.y");
		double pl_z = plugin.conf.getDouble("Loc.z");
		float pl_Yaw = plugin.conf.getInt("Loc.Yaw");
		float pl_Pitch = plugin.conf.getInt("Loc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "conf - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public boolean FirstJoinOnly = plugin.conf.getBoolean("FirstJoinOnly");
	public boolean JoinInv = plugin.conf.getBoolean("JoinInv");
//JoinInv_contentsはパス(増大してい行く奴)
	public boolean JoinInv_null_erro = plugin.conf.getBoolean("JoinInv_null_erro");
	public boolean LeaveInv_clear = plugin.conf.getBoolean("LeaveInv_clear");
	public boolean FirstJoinMessage = plugin.conf.getBoolean("FirstJoinMessage");
	public List<String> FirstJoinMessage_contents = plugin.conf.getStringList("FirstJoinMessage_contents");
	public List<String> FirstJoinMessage_users(String player)
	{
		return plugin.conf.getStringList("FirstJoinMessage_users." + player);
	}
//FirstJoinMessage_usersはプレーヤー名が必要なため。
	public boolean FirstJoinMessage_tell = plugin.conf.getBoolean("FirstJoinMessage_tell");
	public List<String> FirstJoinMessage_tell_contents = plugin.conf.getStringList("FirstJoinMessage_tell_contents");
	public boolean JoinMessage = plugin.conf.getBoolean("JoinMessage");
	public List<String> JoinMessage_contents = plugin.conf.getStringList("JoinMessage_contents");
	public List<String> JoinMessage_users(String player)
	{
		if (plugin.conf.getStringList("JoinMessage_users." + player) == null)
		{
			Bukkit.getConsoleSender().sendMessage("Load_conf.JoinMsg == null");
			List<String> a = new ArrayList<String>();
			a.add("TEST");
			return a;
		}
		return plugin.conf.getStringList("JoinMessage_users." + player);
	}
	public boolean FirstJoinMessage_to_came_user = plugin.conf.getBoolean("FirstJoinMessage_to_came_user");
//JoinMessage_usersはプレーヤー名が必要なため。
	public boolean JoinMessage_tell = plugin.conf.getBoolean("JoinMessage_tell");
	public List<String> JoinMessage_tell_contents = plugin.conf.getStringList("JoinMessage_tell_contents");
	public boolean JoinMessage_to_came_user = plugin.conf.getBoolean("JoinMessage_to_came_user");
	public boolean LeaveMessage = plugin.conf.getBoolean("LeaveMessage");
	public List<String> LeaveMessage_contents = plugin.conf.getStringList("LeaveMessage_contents");
	public List<String> LeaveMessage_users(String player)
	{
		return plugin.conf.getStringList("LeaveMessage_users." + player);
	}
//LeaveMessage_usersはプレーヤー名が必要なため。
	public boolean LeaveMessage_to_came_user = plugin.conf.getBoolean("LeaveMessage_to_came_user");
}
