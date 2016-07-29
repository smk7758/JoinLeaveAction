/*
 * The MIT License
 *
 * Copyright 2016 kazu0617<kazuyagi19990617@hotmail.co.jp>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.smk7758.jla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author kazu0617<kazuyagi19990617@hotmail.co.jp>
 */
class FileIO {
	Main plugin;
	public FileIO(Main instance)
	{
		this.plugin = instance;
	}

	public boolean DebugIO(boolean Save, boolean enable){ //DebugIO(セーブするか, セーブ時の値)
		File file = new File(plugin.folder + "config.yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return false;
		}
		if(Save){
			yaml.set("DebugMode", enable);
			SettingFiles(yaml, file, true);
			return true;
		}
		if(!Save){
			enable = yaml.getBoolean("DebugMode");
			return enable;
		}
		return false;
	}
	//todo ##のリストごとにファイルを生成し直す。
	//セーブロードをしやすくすることを念頭に置いたうえで、構築する。

	public boolean BooleanIO(boolean Save, String FileName, String Path, boolean Type){ //Boolean(セーブするか, ファイル, パス, セーブ時の値) | FirstJoinのOutput専門。
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return false;
		}
		if(Save){
				//Saveモード。
				yaml.set(Path, Type);
				return true;
		} else {
				Type = yaml.getBoolean(Path);
				return Type;
		}
	}

	public String StringIO(int Mode, String FileName, String Path, String Type){ //StringIO(セーブするか, ファイル, パス, セーブ時の値)
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		switch (Mode) { //Modeは上記Saveと同様Booleanではだめなのか。
			case 0:
				//Saveモード。
				yaml.set(Path, Type);
				return null;
			case 1:
				Type = yaml.getString(Path);
				return Type;
			default:
				return null;
		}
	}

	public List<String> StringListIO(int Mode, String FileName, String Path, List<String> Type){ //StringIO(セーブするか, ファイル, パス, セーブ時の値)
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		switch (Mode) { //Modeは上記Saveと同様Booleanではだめなのか。
			case 0:
				//Saveモード。
				yaml.set(Path, Type);
				return null;
			case 1:
				Type = yaml.getStringList(Path);
				return Type;
			default:
				return null;
		}
	}
	/*
	public HashMap LocationIO(boolean Save, String FileName, String Path, World world, short X, short Y, short Z, short Yaw, short Pitch){
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if (Save) {//Saveモード。
			yaml.set(Path+".World",world);
			yaml.set(Path+".x",X);
			yaml.set(Path+".y",Y);
			yaml.set(Path+".z",Z);
			yaml.set(Path+".Yaw",Yaw);
			yaml.set(Path+".Pitch",Pitch);
			return null;
		} else {
			HashMap<String,Object> tmp = new HashMap<>();
			tmp.put("World", yaml.get(Path+".World"));
			tmp.put("X", (short) yaml.getDouble(Path+".x"));
			tmp.put("Y", (short) yaml.get(Path+".y"));
			tmp.put("Z", (short) yaml.get(Path+".z"));
			tmp.put("Yaw", (short) yaml.get(Path+".Yaw"));
			tmp.put("Pitch", (short) yaml.get(Path+".Pitch"));
			return tmp;
		}
	}*/
	public Location LocationIO(boolean Save, String FileName, String Path, World world, short X, short Y, short Z, short Yaw, short Pitch){
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if (Save) {//Saveモード。
			yaml.set(Path+".World",world);
			yaml.set(Path+".X",X);
			yaml.set(Path+".Y",Y);
			yaml.set(Path+".Z",Z);
			yaml.set(Path+".Yaw",Yaw);
			yaml.set(Path+".Pitch",Pitch);
			return null;
		} else {
			World pl_world = Bukkit.getServer().getWorld(yaml.getString("SpawnLoc.World"));
			double pl_x = yaml.getDouble("SpawnLoc.x");
			double pl_y = yaml.getDouble("SpawnLoc.y");
			double pl_z = yaml.getDouble("SpawnLoc.z");
			float pl_Yaw = yaml.getInt("SpaawnLoc.Yaw");
			float pl_Pitch = yaml.getInt("SpawnLoc.Pitch");
			if (pl_world == null)
			{
				plugin.cLog.warn(Path+".World is Null.");
			}
			Location loc = new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
			return loc;
		}
	}

	public HashMap<Object, Object> ItemIO(boolean Save, String FileName, String Path, HashMap<String, Object> ItemList) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if(Save) {//Saveモード。
			yaml.set(Path+".Item",ItemList.get("Item"));
			yaml.set(Path+".ItemStackNumber", ItemList.get("ItemStackNumber"));
			if(yaml.contains("ItemName")) yaml.set(Path+".ItemName", ItemList.get("ItemName"));
			if(yaml.contains("ItemLore")) yaml.set(Path+".ItemLore", ItemList.get("ItemLore"));
			return null;
		} else {
			ItemList.put("Item", yaml.get(Path+".Item"));
			ItemList.put("ItemStackNumber", yaml.get(Path+".ItemStackNumber"));
			if(yaml.getBoolean(Path+".setItemName")) ItemList.put("ItemName", yaml.get(Path+".ItemName"));
			if(yaml.getBoolean(Path+".setItemLore")) ItemList.put("ItemLore", yaml.get(Path+".ItemLore"));
			return null;
		}
	}
	/**
	 * ファイルの保存
	 *
	 * 新規作成だけで、保存しない場合はsaveをfalseに
	 * 保存&新規作成の場合はtrueでもfalseでも
	 * 上書きの時は必ずtrueに
	 *
	 * @param fileconfiguration ファイルコンフィグを指定
	 * @param file ファイル指定
	 * @param save 上書きをするかリセットするか(trueで上書き)
	 */
	public void SettingFiles(FileConfiguration fileconfiguration, File file, boolean save)
	{
		if(!file.exists() || save)
		{
			try {
				fileconfiguration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
