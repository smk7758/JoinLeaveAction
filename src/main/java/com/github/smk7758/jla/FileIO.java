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
	ConsoleLog cLog;
	public FileIO(Main instance)
	{
		this.plugin = instance;
	}

	/**
	 * DebugModeのIO
	 *
	 * 読み込む時のenableは埋め文字
	 *
	 * @param save セーブをするか(true = Saveモード,false = Getモード)
	 * @param enable セーブ時の値
	 */
	public boolean DebugIO(boolean save, boolean enable) {
		File file = new File(plugin.folder + "config.yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
			return false;
		}
		if(save){
			yaml.set("DebugMode", enable);
			SettingFiles(yaml, file, true);
			return true;
		}
		if(!save){
			enable = yaml.getBoolean("DebugMode");
			return enable;
		}
		return false;
	}
	//todo ##のリストごとにファイルを生成し直す。
	//セーブロードをしやすくすることを念頭に置いたうえで、構築する。

	/**
	 * BooleanのIO
	 *
	 * 読み込む時のtypeは埋め文字
	 *
	 * @param save セーブをするか(true = Saveモード,false = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param type セーブ時の値
	 */
	public boolean BooleanIO(boolean save, String FileName, String path, boolean type) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
			return false;
		}
		if(save){
				//Saveモード。
				yaml.set(path, type);
				return true;
		} else {
				type = yaml.getBoolean(path);
				return type;
		}
	}

	/**
	 * StringのIO
	 *
	 * 読み込む時のtypeは埋め文字(null)
	 *
	 * @param mode セーブをするか(0 = Saveモード, 1 = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param type セーブ時の値
	 */
	public String StringIO(int mode, String FileName, String path, String type) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
			return null;
		}
		switch (mode) { //Modeは上記Saveと同様Booleanではだめなのか。
			case 0:
				//Saveモード。
				yaml.set(path, type);
				return null;
			case 1:
				type = yaml.getString(path);
				return type;
			default:
				return null;
		}
	}

	/**
	 * List<String>のIO
	 *
	 * 読み込む時のtypeは埋め文字(null)
	 *
	 * @param mode セーブをするか(0 = Saveモード, 1 = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param type セーブ時の値
	 */
	public List<String> StringListIO(int mode, String FileName, String path, List<String> type) { //StringIO(セーブするか, ファイル, パス, セーブ時の値)
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
			return null;
		}
		switch (mode) { //Modeは上記Saveと同様Booleanではだめなのか。
			case 0:
				//Saveモード。
				yaml.set(path, type);
				return null;
			case 1:
				type = yaml.getStringList(path);
				return type;
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
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
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

	/**
	 * LocationのIO
	 *
	 * 読み込む時のworldは埋め文字(null)
	 * 読み込む時のx y z yam pitchは埋め文字((short)0)
	 *
	 * @param save セーブをするか(true = Saveモード,false = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param world セーブ時の値
	 * @param x,y,z,yaw,pitch セーブ時の値
	 */
	public Location LocationIO(boolean save, String FileName, String path, World world, short x, short y, short z, short yaw, short pitch) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
			return null;
		}
		if (save) {//Saveモード。
			yaml.set(path+".World",world);
			yaml.set(path+".X",x);
			yaml.set(path+".Y",y);
			yaml.set(path+".Z",z);
			yaml.set(path+".Yaw",yaw);
			yaml.set(path+".Pitch",pitch);
			return null;
		} else {
			World pl_world = Bukkit.getServer().getWorld(yaml.getString("SpawnLoc.World"));
			double pl_x = yaml.getDouble("SpawnLoc.x");
			double pl_y = yaml.getDouble("SpawnLoc.y");
			double pl_z = yaml.getDouble("SpawnLoc.z");
			float pl_Yaw = yaml.getInt("SpaawnLoc.Yaw");
			float pl_Pitch = yaml.getInt("SpawnLoc.Pitch");
			return new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		}
	}

	/**
	 * ItemのIO
	 *
	 * 読み込む時のtypeは埋め文字(null)
	 *
	 * @param save セーブをするか(true = Saveモード,false = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param ItemList セーブ時の値(HashMap|Item,ItemStackNumber,ItemName,ItemLore)
	 */
	public HashMap<String, Object> ItemIO(boolean save, String FileName, String path, HashMap<String, Object> ItemList) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (FileNotFoundException | InvalidConfigurationException ex) {
			cLog.info("該当するファイルがありませんでした。新規作成します");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			cLog.info("ロードに失敗しました。");
			return null;
		}
		if(save) {//Saveモード。
			yaml.set(path+".Item",ItemList.get("Item"));
			yaml.set(path+".ItemStackNumber", ItemList.get("ItemStackNumber"));
			if (ItemList.containsKey("ItemName")) yaml.set(path+".ItemName", ItemList.get("ItemName"));
			if (ItemList.containsKey("ItemLore") && ItemList.get("ItemLore") instanceof List) yaml.set(path+".ItemLore", ItemList.get("ItemLore"));
			//if(yaml.contains("ItemName")) yaml.set(path+".ItemName", ItemList.get("ItemName"));
			//if(yaml.contains("ItemLore")) yaml.set(path+".ItemLore", ItemList.get("ItemLore"));
			return null;
		} else {//Getモード
			ItemList.put("Item", yaml.get(path+".Item"));
			ItemList.put("ItemStackNumber", yaml.get(path+".ItemStackNumber"));
			if(yaml.contains(path+".ItemName")) yaml.set(path+".ItemName", ItemList.get("ItemName"));
			if(yaml.contains(path+".ItemLore") && yaml.isList(path+".ItemLore")) yaml.set(path+".ItemLore", ItemList.get("ItemLore"));
			//if(yaml.getBoolean(path+".setItemName")) ItemList.put("ItemName", yaml.get(path+".ItemName"));
			//if(yaml.getBoolean(path+".setItemLore")) ItemList.put("ItemLore", yaml.get(path+".ItemLore"));
			return null;
			//ここらへんの仕様が未確定
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
	public void SettingFiles(FileConfiguration fileconfiguration, File file, boolean save) {
		if(!file.exists() || save) {
			try {
				fileconfiguration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
