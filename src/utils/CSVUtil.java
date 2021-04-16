package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParsePosition;
import java.util.ArrayList;

import exception.UnknownItemTypeException;
import items.armor.GoldenArmor;
import items.armor.IronArmor;
import items.armor.WoodenArmor;
import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;
import items.potion.InstantHealPotion;
import items.potion.RegenerationPotion;
import items.potion.ShieldPotion;
import items.potion.StrengthPotion;
import items.potion.VisionPotion;
import items.weapon.Knife;
import items.weapon.Spear;
import items.weapon.Sword;

public class CSVUtil {
	private static final String baseUrl = "csv/";

	public static String[][] readCSV(String filename) {
		try {
			InputStream inputStream = ClassLoader.getSystemResourceAsStream(filename);

			assert inputStream != null;

			InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			BufferedReader in = new BufferedReader(streamReader);

			ArrayList<String[]> output = new ArrayList<>();
			String row = null;

			while ((row = in.readLine()) != null) {
				String[] data = row.split(",");
				output.add(data);
			}

			in.close();
			output.remove(output.size() - 1);
			return output.toArray(new String[output.size()][]);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// (LEVEL),(DARK_MAGE),(HAUNTED_MAID),(PUMPKIN_HEAD),(REAPER),(SKELETON),(SOUL)
	public static ArrayList<MonsterLevelFilter> readMonsterFilterCSV() {
		// TODO Read MonsterFloor CSV File
		String[][] monsterLevelData = readCSV(baseUrl + "MonsterFloor.csv");

		ArrayList<MonsterLevelFilter> filterList = new ArrayList<>();
		int sz = monsterLevelData.length;

		for (int i = 0; i < sz; i++) {
			int level = Integer.parseInt(monsterLevelData[i][0]);
			boolean darkMage = Boolean.parseBoolean(monsterLevelData[i][1]);
			boolean hauntedMaid = Boolean.parseBoolean(monsterLevelData[i][2]);
			boolean pumpkinHead = Boolean.parseBoolean(monsterLevelData[i][3]);
			boolean reaper = Boolean.parseBoolean(monsterLevelData[i][4]);
			boolean skeleton = Boolean.parseBoolean(monsterLevelData[i][5]);
			boolean soul = Boolean.parseBoolean(monsterLevelData[i][6]);

			filterList.add(new MonsterLevelFilter(level, darkMage, hauntedMaid, pumpkinHead, reaper, skeleton, soul));
		}

		return filterList;
	}

	public static ArrayList<Potion> readPotionCSV() {
		String[][] potionData = readCSV(baseUrl + "PotionData.csv");
		ArrayList<Potion> output = new ArrayList<>();

		int sz = potionData.length;

		for (int i = 0; i < sz; i++) {
			String type = potionData[i][0];
			String name = potionData[i][1];
			String description = potionData[i][2];

			int value = Integer.parseInt(potionData[i][3]);
			int duration = Integer.parseInt(potionData[i][4]);
			boolean isPermanant = Boolean.parseBoolean(potionData[i][5]);

			try {
				Potion parsePotionResult = Potion.parsePotion(type, name, description, value, duration, isPermanant);
				output.add(parsePotionResult);
			} catch (UnknownItemTypeException e) {
				e.printStackTrace();
			}
		}

		return output;
	}

	public static ArrayList<Armor> readArmorCSV() {
		String[][] armorData = readCSV(baseUrl + "ArmorData.csv");
		ArrayList<Armor> output = new ArrayList<>();

		int sz = armorData.length;

		for (int i = 0; i < sz; i++) {
			String type = armorData[i][0];
			String name = armorData[i][1];
			String description = armorData[i][2];

			int defense = Integer.parseInt(armorData[i][3]);

			try {
				Armor parseArmorResult = Armor.parseArmor(type, name, description, defense);
				output.add(parseArmorResult);
			} catch (UnknownItemTypeException e) {
				e.printStackTrace();
			}
		}

		return output;
	}

	public static ArrayList<Weapon> readWeaponCSV() {
		String[][] weaponData = readCSV(baseUrl + "WeaponData.csv");
		ArrayList<Weapon> output = new ArrayList<>();

		int sz = weaponData.length;

		for (int i = 0; i < sz; i++) {
			String type = weaponData[i][0];
			String name = weaponData[i][1];
			String description = weaponData[i][2];

			int attack = Integer.parseInt(weaponData[i][3]);

			try {
				Weapon parseWeaponResult = Weapon.parseWeapon(type, name, description, attack);
				output.add(parseWeaponResult);
			} catch (UnknownItemTypeException e) {
				e.printStackTrace();
			}

		}
		return output;
	}

}
