package my.smk.plugin.jla;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import my.smk.plugin.jla.Main;

public class Load_config
{
	String ChatPrefix = ChatColor.YELLOW + "[" + ChatColor.GREEN + Bukkit.getName() + ChatColor.YELLOW + "] " + ChatColor.WHITE;
	static String ErroPrefix = ChatColor.WHITE + "[" + ChatColor.RED + Bukkit.getName() + ChatColor.WHITE + "] " + ChatColor.WHITE;

	public static boolean Debug_mode = Main.getConfigFile().getBoolean("Debug_mode");
	public static boolean FirstJoin = Main.getConfigFile().getBoolean("FirstJoin");
	public static boolean FirstJoin_use_YAML = Main.getConfigFile().getBoolean("FirstJoin_use_YAML");
//	public static boolean FirstJoin_use_UUID = Main.getConfigFile().getBoolean("FirstJoin_use_UUID");
	public static Location SpawnLoc()
	{
		Location loc;
		World pl_world = Bukkit.getServer().getWorld(Main.getConfigFile().getString("SpawnLoc.World"));
		double pl_x = Main.getConfigFile().getDouble("SpawnLoc.x");
		double pl_y = Main.getConfigFile().getDouble("SpawnLoc.y");
		double pl_z = Main.getConfigFile().getDouble("SpawnLoc.z");
		float pl_Yaw = Main.getConfigFile().getInt("SpaawnLoc.Yaw");
		float pl_Pitch = Main.getConfigFile().getInt("SpawnLoc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(ErroPrefix + "Main.getConfigFile() - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public static boolean SpawnTPMsg_me = Main.getConfigFile().getBoolean("SpawnTPMsg_me");
	public static String SpawnTPMsg_me_contents = Main.getConfigFile().getString("SpawnTPMsg_me_contents");
	public static boolean SpawnTPMsg_other = Main.getConfigFile().getBoolean("SpawnTPMsg_other");
	public static String SpawnTPMsg_other_contents = Main.getConfigFile().getString("SpawnTPMsg_other_contents");
	public static boolean JoinSpawn = Main.getConfigFile().getBoolean("JoinSpawn");
	public static boolean FirstJoinSpawn = Main.getConfigFile().getBoolean("FirstJoinSpawn");
	public static Location FirstJoinLoc()
	{
		Location loc;
		World pl_world = Bukkit.getServer().getWorld(Main.getConfigFile().getString("Loc.World"));
		double pl_x = Main.getConfigFile().getDouble("Loc.x");
		double pl_y = Main.getConfigFile().getDouble("Loc.y");
		double pl_z = Main.getConfigFile().getDouble("Loc.z");
		float pl_Yaw = Main.getConfigFile().getInt("Loc.Yaw");
		float pl_Pitch = Main.getConfigFile().getInt("Loc.Pitch");
		if (pl_world == null)
		{
			Bukkit.getConsoleSender().sendMessage(ErroPrefix + "Main.getConfigFile() - World is Null.");
		}
		loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		return loc;
	}
	public static boolean FirstJoinOnly = Main.getConfigFile().getBoolean("FirstJoinOnly");
	public static boolean JoinInv = Main.getConfigFile().getBoolean("JoinInv");
//JoinInv_contentsはパス(増大してい行く奴)
	public static boolean JoinInv_null_erro = Main.getConfigFile().getBoolean("JoinInv_null_erro");
	public static boolean LeaveInv_clear = Main.getConfigFile().getBoolean("LeaveInv_clear");
	public static boolean FirstJoinMessage = Main.getConfigFile().getBoolean("FirstJoinMessage");
	public static List<String> FirstJoinMessage_contents = Main.getConfigFile().getStringList("FirstJoinMessage_contents");
	public static List<String> FirstJoinMessage_users(String player)
	{
		return Main.getConfigFile().getStringList("FirstJoinMessage_users." + player);
	}
//FirstJoinMessage_usersはプレーヤー名が必要なため。
	public static boolean FirstJoinMessage_tell = Main.getConfigFile().getBoolean("FirstJoinMessage_tell");
	public static List<String> FirstJoinMessage_tell_contents = Main.getConfigFile().getStringList("FirstJoinMessage_tell_contents");
	public static boolean JoinMessage = Main.getConfigFile().getBoolean("JoinMessage");
	public static List<String> JoinMessage_contents = Main.getConfigFile().getStringList("JoinMessage_contents");
	public static List<String> JoinMessage_users(String player)
	{
		return Main.getConfigFile().getStringList("JoinMessage_users." + player);
	}
	public static boolean FirstJoinMessage_to_came_user = Main.getConfigFile().getBoolean("FirstJoinMessage_to_came_user");
//JoinMessage_usersはプレーヤー名が必要なため。
	public static boolean JoinMessage_tell = Main.getConfigFile().getBoolean("JoinMessage_tell");
	public static List<String> JoinMessage_tell_contents = Main.getConfigFile().getStringList("JoinMessage_tell_contents");
	public static boolean JoinMessage_to_came_user = Main.getConfigFile().getBoolean("JoinMessage_to_came_user");
	public static boolean LeaveMessage = Main.getConfigFile().getBoolean("LeaveMessage");
	public static List<String> LeaveMessage_contents = Main.getConfigFile().getStringList("LeaveMessage_contents");
	public static List<String> LeaveMessage_users(String player)
	{
		return Main.getConfigFile().getStringList("LeaveMessage_users." + player);
	}
//LeaveMessage_usersはプレーヤー名が必要なため。
	public static boolean LeaveMessage_to_came_user = Main.getConfigFile().getBoolean("LeaveMessage_to_came_user");
}
