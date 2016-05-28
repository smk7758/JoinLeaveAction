package my.smk.plugin.jla;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class Load_config {
	Main main = new Main();
	String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + main.getDescription().getName() + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + main.getDescription().getName() + ChatColor.WHITE + "] " + ChatColor.WHITE;

	public boolean Debug_mode = main.conf.getBoolean("Debug_mode");
	public boolean FirstJoin = main.conf.getBoolean("FirstJoin");
	public boolean FirstJoin_use_YAML = main.conf.getBoolean("FirstJoin_use_YAML");
//	public boolean FirstJoin_use_UUID = main.conf.getBoolean("FirstJoin_use_UUID");
	public Location SpawnLoc()
	{
		Location loc;
		World pl_world = main.getServer().getWorld(main.conf.getString("SpawnLoc.World"));
		double pl_x = main.conf.getDouble("SpawnLoc.x");
		double pl_y = main.conf.getDouble("SpawnLoc.y");
		double pl_z = main.conf.getDouble("SpawnLoc.z");
		float pl_Yaw = main.conf.getInt("SpaawnLoc.Yaw");
		float pl_Pitch = main.conf.getInt("SpawnLoc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "conf - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public boolean SpawnTPMsg_me = main.conf.getBoolean("SpawnTPMsg_me");
	public String SpawnTPMsg_me_contents = main.conf.getString("SpawnTPMsg_me_contents");
	public boolean SpawnTPMsg_other = main.conf.getBoolean("SpawnTPMsg_other");
	public String SpawnTPMsg_other_contents = main.conf.getString("SpawnTPMsg_other_contents");
	public boolean JoinSpawn = main.conf.getBoolean("JoinSpawn");
	public boolean FirstJoinSpawn = main.conf.getBoolean("FirstJoinSpawn");
	public Location FirstJoinLoc()
	{
		Location loc;
		World pl_world = main.getServer().getWorld(main.conf.getString("Loc.World"));
		double pl_x = main.conf.getDouble("Loc.x");
		double pl_y = main.conf.getDouble("Loc.y");
		double pl_z = main.conf.getDouble("Loc.z");
		float pl_Yaw = main.conf.getInt("Loc.Yaw");
		float pl_Pitch = main.conf.getInt("Loc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(this.ErroPrefix + "conf - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public boolean FirstJoinOnly = main.conf.getBoolean("FirstJoinOnly");
	public boolean JoinInv = main.conf.getBoolean("JoinInv");
//JoinInv_contentsはパス(増大してい行く奴)
	public boolean JoinInv_null_erro = main.conf.getBoolean("JoinInv_null_erro");
	public boolean LeaveInv_clear = main.conf.getBoolean("LeaveInv_clear");
	public boolean FirstJoinMessage = main.conf.getBoolean("FirstJoinMessage");
	public List<String> FirstJoinMessage_contents = main.conf.getStringList("FirstJoinMessage_contents");
	public List<String> FirstJoinMessage_users(String player)
	{
		return main.conf.getStringList("FirstJoinMessage_users." + player);
	}
//FirstJoinMessage_usersはプレーヤー名が必要なため。
	public boolean FirstJoinMessage_tell = main.conf.getBoolean("FirstJoinMessage_tell");
	public List<String> FirstJoinMessage_tell_contents = main.conf.getStringList("FirstJoinMessage_tell_contents");
	public boolean JoinMessage = main.conf.getBoolean("JoinMessage");
	public List<String> JoinMessage_contents = main.conf.getStringList("JoinMessage_contents");
	public List<String> JoinMessage_users(String player)
	{
		return main.conf.getStringList("JoinMessage_users." + player);
	}
	public boolean FirstJoinMessage_to_came_user = main.conf.getBoolean("FirstJoinMessage_to_came_user");
//JoinMessage_usersはプレーヤー名が必要なため。
	public boolean JoinMessage_tell = main.conf.getBoolean("JoinMessage_tell");
	public List<String> JoinMessage_tell_contents = main.conf.getStringList("JoinMessage_tell_contents");
	public boolean JoinMessage_to_came_user = main.conf.getBoolean("JoinMessage_to_came_user");
	public boolean LeaveMessage = main.conf.getBoolean("LeaveMessage");
	public List<String> LeaveMessage_contents = main.conf.getStringList("LeaveMessage_contents");
	public List<String> LeaveMessage_users(String player)
	{
		return main.conf.getStringList("LeaveMessage_users." + player);
	}
//LeaveMessage_usersはプレーヤー名が必要なため。
	public boolean LeaveMessage_to_came_user = main.conf.getBoolean("LeaveMessage_to_came_user");
}
