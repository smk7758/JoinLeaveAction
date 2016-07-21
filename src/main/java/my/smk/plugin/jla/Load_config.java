package my.smk.plugin.jla;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import my.smk.plugin.jla.Main;

public class Load_config
{
	Load_config plugin;
	Main Main;
	String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + Bukkit.getName() + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + Bukkit.getName() + ChatColor.WHITE + "] " + ChatColor.WHITE;

	public boolean Debug_mode = Main.plugin.getConfigFile().getBoolean("Debug_mode");
	public boolean FirstJoin = Main.plugin.getConfigFile().getBoolean("FirstJoin");
	public boolean FirstJoin_use_YAML = Main.plugin.getConfigFile().getBoolean("FirstJoin_use_YAML");
//	public boolean FirstJoin_use_UUID = Main.plugin.getConfigFile().getBoolean("FirstJoin_use_UUID");
	public Location SpawnLoc()
	{
		Location loc;
		World pl_world = Bukkit.getServer().getWorld(Main.plugin.getConfigFile().getString("SpawnLoc.World"));
		double pl_x = Main.plugin.getConfigFile().getDouble("SpawnLoc.x");
		double pl_y = Main.plugin.getConfigFile().getDouble("SpawnLoc.y");
		double pl_z = Main.plugin.getConfigFile().getDouble("SpawnLoc.z");
		float pl_Yaw = Main.plugin.getConfigFile().getInt("SpaawnLoc.Yaw");
		float pl_Pitch = Main.plugin.getConfigFile().getInt("SpawnLoc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(ErroPrefix + "Main.plugin.getConfigFile() - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public boolean SpawnTPMsg_me = Main.plugin.getConfigFile().getBoolean("SpawnTPMsg_me");
	public String SpawnTPMsg_me_contents = Main.plugin.getConfigFile().getString("SpawnTPMsg_me_contents");
	public boolean SpawnTPMsg_other = Main.plugin.getConfigFile().getBoolean("SpawnTPMsg_other");
	public String SpawnTPMsg_other_contents = Main.plugin.getConfigFile().getString("SpawnTPMsg_other_contents");
	public boolean JoinSpawn = Main.plugin.getConfigFile().getBoolean("JoinSpawn");
	public boolean FirstJoinSpawn = Main.plugin.getConfigFile().getBoolean("FirstJoinSpawn");
	public Location FirstJoinLoc()
	{
		Location loc;
		World pl_world = Bukkit.getServer().getWorld(Main.plugin.getConfigFile().getString("Loc.World"));
		double pl_x = Main.plugin.getConfigFile().getDouble("Loc.x");
		double pl_y = Main.plugin.getConfigFile().getDouble("Loc.y");
		double pl_z = Main.plugin.getConfigFile().getDouble("Loc.z");
		float pl_Yaw = Main.plugin.getConfigFile().getInt("Loc.Yaw");
		float pl_Pitch = Main.plugin.getConfigFile().getInt("Loc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(ErroPrefix + "Main.plugin.getConfigFile() - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public boolean FirstJoinOnly = Main.plugin.getConfigFile().getBoolean("FirstJoinOnly");
	public boolean JoinInv = Main.plugin.getConfigFile().getBoolean("JoinInv");
//JoinInv_contentsはパス(増大してい行く奴)
	public boolean JoinInv_null_erro = Main.plugin.getConfigFile().getBoolean("JoinInv_null_erro");
	public boolean LeaveInv_clear = Main.plugin.getConfigFile().getBoolean("LeaveInv_clear");
	public boolean FirstJoinMessage = Main.plugin.getConfigFile().getBoolean("FirstJoinMessage");
	public List<String> FirstJoinMessage_contents = Main.plugin.getConfigFile().getStringList("FirstJoinMessage_contents");
	public List<String> FirstJoinMessage_users(String player)
	{
		return Main.plugin.getConfigFile().getStringList("FirstJoinMessage_users." + player);
	}
//FirstJoinMessage_usersはプレーヤー名が必要なため。
	public boolean FirstJoinMessage_tell = Main.plugin.getConfigFile().getBoolean("FirstJoinMessage_tell");
	public List<String> FirstJoinMessage_tell_contents = Main.plugin.getConfigFile().getStringList("FirstJoinMessage_tell_contents");
	public boolean JoinMessage = Main.plugin.getConfigFile().getBoolean("JoinMessage");
	public List<String> JoinMessage_contents = Main.plugin.getConfigFile().getStringList("JoinMessage_contents");
	public List<String> JoinMessage_users(String player)
	{
		return Main.plugin.getConfigFile().getStringList("JoinMessage_users." + player);
	}
	public boolean FirstJoinMessage_to_came_user = Main.plugin.getConfigFile().getBoolean("FirstJoinMessage_to_came_user");
//JoinMessage_usersはプレーヤー名が必要なため。
	public boolean JoinMessage_tell = Main.plugin.getConfigFile().getBoolean("JoinMessage_tell");
	public List<String> JoinMessage_tell_contents = Main.plugin.getConfigFile().getStringList("JoinMessage_tell_contents");
	public boolean JoinMessage_to_came_user = Main.plugin.getConfigFile().getBoolean("JoinMessage_to_came_user");
	public boolean LeaveMessage = Main.plugin.getConfigFile().getBoolean("LeaveMessage");
	public List<String> LeaveMessage_contents = Main.plugin.getConfigFile().getStringList("LeaveMessage_contents");
	public List<String> LeaveMessage_users(String player)
	{
		return Main.plugin.getConfigFile().getStringList("LeaveMessage_users." + player);
	}
//LeaveMessage_usersはプレーヤー名が必要なため。
	public boolean LeaveMessage_to_came_user = Main.plugin.getConfigFile().getBoolean("LeaveMessage_to_came_user");
}
