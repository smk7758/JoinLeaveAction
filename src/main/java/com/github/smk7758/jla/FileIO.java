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
 * @author kazu0617<kazuyagi19990617@hotmail.co.jp>
 */
public class FileIO {
	// private Main plugin;
	// public FileIO(Main instance) {
	// plugin = instance;
	// }
	// private static final FileIO instance = new FileIO();
	// private FileIO() {
	// }
	// public static FileIO getInstance() {
	// return instance;
	// }
	private Main plugin;

	public FileIO(Main instance) {
		plugin = instance;
	}

	/**
	 * DebugModeのIO 読み込む時のenableは埋め文字
	 *
	 * @param save セーブをするか(true = Saveモード,false = Getモード)
	 * @param enable セーブ時の値
	 */
	public boolean DebugIO(boolean save, boolean enable) {
		File file = new File(plugin.folder + "config.yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します。");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return false;
		}
		if (save) {
			yaml.set("DebugMode", enable);
			SettingFiles(yaml, file, true);
			return true;
		}
		if (!save) {
			enable = yaml.getBoolean("DebugMode");
			return enable;
		}
		return false;
	}

	// todo ##のリストごとにファイルを生成し直す。
	// セーブロードをしやすくすることを念頭に置いたうえで、構築する。

	/**
	 * BooleanのIO 読み込む時のtypeは埋め文字
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
		} catch (InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します。");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return false;
		}
		if (save) {
			// Saveモード。
			yaml.set(path, type);
			SettingFiles(yaml, file, true);
			return true;
		} else {
			type = yaml.getBoolean(path);
			return type;
		}
	}

	/**
	 * StringのIO 読み込む時のtypeは埋め文字(null)
	 *
	 * @param mode セーブをするか(0 = Saveモード, 1 = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param type セーブ時の値
	 */
	public String StringIO(boolean save, String FileName, String path, String type) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します。");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if (save) {// Saveモード。
			yaml.set(path, type);
			SettingFiles(yaml, file, true);
			return null;
		} else {
			type = yaml.getString(path);
			return type;
		}
	}

	/**
	 * List<String>のIO 読み込む時のtypeは埋め文字(null)
	 *
	 * @param mode セーブをするか(0 = Saveモード, 1 = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param type セーブ時の値
	 */
	public List<String> StringListIO(boolean save, String FileName, String path, List<String> type) { // StringIO(セーブするか, ファイル, パス, セーブ時の値)
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します。");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if (save) {// Saveモード。
			yaml.set(path, type);
			SettingFiles(yaml, file, true);
			return null;
		} else {
			type = yaml.getStringList(path);
			return type;
		}
	}

	/**
	 * LocationのIO 読み込む時のworldは埋め文字(null) 読み込む時のx y z yam pitchは埋め文字((short)0)
	 *
	 * @param save セーブをするか(true = Saveモード,false = Getモード)
	 * @param FileName IOのファイル名
	 * @param path 値へのパス
	 * @param world セーブ時の値
	 * @param x,y,z,yaw,pitch セーブ時の値
	 */
	public Location LocationIO(boolean save, String FileName, String path, String world, double x, double y, double z, float yaw, float pitch) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		try {
			yaml.load(file);
		} catch (InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します。");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if (save) {// Saveモード。
			yaml.set(path + ".World", world);
			yaml.set(path + ".X", x);
			yaml.set(path + ".Y", y);
			yaml.set(path + ".Z", z);
			yaml.set(path + ".Yaw", yaw);
			yaml.set(path + ".Pitch", pitch);
			SettingFiles(yaml, file, true);
			return null;
		} else {
			World pl_world = Bukkit.getServer().getWorld(yaml.getString(path + ".World"));
			double pl_x = yaml.getDouble(path + ".X");
			double pl_y = yaml.getDouble(path + ".Y");
			double pl_z = yaml.getDouble(path + ".Z");
			float pl_Yaw = (float) yaml.getDouble(path + ".Yaw"); // ?
			float pl_Pitch = (float) yaml.getDouble(path + ".Pitch"); // ?なぜint?
			return new Location(pl_world, pl_x, pl_y, pl_z, pl_Yaw, pl_Pitch);
		}
	}

	/**
	 * ItemのIO 読み込む時のtypeは埋め文字(null)
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
		} catch (InvalidConfigurationException ex) {
			plugin.cLog.info("該当するファイルがありませんでした。新規作成します。");
			SettingFiles(yaml, file, true);
		} catch (IOException ex) {
			plugin.cLog.info("ロードに失敗しました。");
			return null;
		}
		if (save) {// Saveモード。
			yaml.set(path + ".Item", ItemList.get("Item"));
			yaml.set(path + ".ItemStackNumber", ItemList.get("ItemStackNumber"));
			if (ItemList.containsKey("ItemName")) yaml.set(path + ".ItemName", ItemList.get("ItemName"));
			if (ItemList.containsKey("ItemLore") && ItemList.get("ItemLore") instanceof List) yaml.set(path + ".ItemLore", ItemList.get("ItemLore"));
			SettingFiles(yaml, file, true);
			return null;
		} else {// Getモード
			HashMap<String, Object> items = new HashMap<>();
			items.put("Item", yaml.get(path + ".Item"));
			items.put("ItemStackNumber", yaml.get(path + ".ItemStackNumber"));
			items.put("ItemName", yaml.get(path + ".ItemName"));
			items.put("ItemLore", yaml.get(path + ".ItemLore"));
			return items;
		}
	}

	public void makeSettingFiles(String FileName) {
		File file = new File(plugin.folder + FileName + ".yml");
		FileConfiguration yaml = new YamlConfiguration();
		SettingFiles(yaml, file, false);
	}

	/**
	 * ファイルの保存 新規作成だけで、保存しない場合はsaveをfalseに 保存&新規作成の場合はtrueでもfalseでも 上書きの時は必ずtrueに
	 *
	 * @param fileconfiguration ファイルコンフィグを指定
	 * @param file ファイル指定
	 * @param save 上書きをするかリセットするか(trueで上書き)
	 */
	public void SettingFiles(FileConfiguration fileconfiguration, File file, boolean save) {
		if (!file.exists() || save) {
			try {
				fileconfiguration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
