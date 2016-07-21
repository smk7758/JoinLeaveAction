package jp.kotmw.loginbonus.FileDatas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import my.smk.plugin.jla.Main;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginFiles {
//	public static String filepath = Main.plugin.filepath;
	public static String filepath = "";
	/**
	 * �t�@�C���̕ۑ�
	 *
	 * @param fileconfiguration �t�@�C���R���t�B�O���w��
	 * @param file �t�@�C���w��
	 * @param save �㏑�������邩���Z�b�g���邩
	 */
	public static void SettingFiles(FileConfiguration fileconfiguration, File file) {
		try {
			fileconfiguration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * plugins/<�v���O�C����>/
	 * �ȉ��̊K�w�ɂ���f�B���N�g���̒��ɂ���YAML�t�@�C�����擾
	 *
	 * @param dirname �f�B���N�g����
	 * @param name �t�@�C����
	 *
	 */
	public static File DirFile(String dirname, String name) {
		return new File(filepath + dirname + File.separator + name +".yml");

	}

	/**
	 * config.yml�Ɠ����K�w�ɐ�������t�@�C��
	 *
	 * @param name �t�@�C����
	 *
	 */
	public static File ConfigFile(String name) {
		return new File(filepath + name + ".yml");
	}

	/**
	 * �g���q�𔲂����t�@�C�������擾
	 *
	 * @param name �g���q���܂߂��t�@�C����
	 *
	 */
	private static String getName(String name) {
		if (name == null)
			return null;
		int point = name.lastIndexOf(".");
		if (point != -1)
			return name.substring(0, point);
		return name;
	}

	/**
	 * �w�肵���f�B���N�g���ɂ���t�@�C���̃��X�g���擾
	 * (�t�H���_�͊܂܂Ȃ�)
	 *
	 * @param dir �t�@�C���f�B���N�g��
	 *
	 */
	public static List<String> getFileList(File dir) {
		List<String> names = new ArrayList<>();
		for(File file : Arrays.asList(dir.listFiles())) {
			if(file.isDirectory())
				continue;
			names.add(getName(file.getName()));
		}
		return names;
	}
}
