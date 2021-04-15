package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;

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
			System.out.println("%s not found".formatted(filename));
			e.printStackTrace();
			return null;
		}
	}

	public static boolean[][] readMonsterFilterCSV() {
		// TODO Read MonsterFloor CSV File
		
		String[][] monsterFloorData = readCSV(baseUrl + "MonsterFloor.csv");
		
		int sz = monsterFloorData.length;
		
		return null;
	}

	public static ArrayList<Potion> readPotionCSV() {
		// TODO Read Potion CSV File
		
		String[][] potionData = readCSV(baseUrl + "PotionData.csv");
		ArrayList<Potion> output = new ArrayList<>();

		return output;
	}

	public static ArrayList<Armor> readArmorCSV() {
		// TODO Read Armor CSV File
		
		String[][] armorData = readCSV(baseUrl + "ArmorData.csv");
		ArrayList<Armor> output = new ArrayList<>();

		return output;
	}

	public static ArrayList<Weapon> readWeaponCSV() {
		// TODO Read Weapon CSV File
		
		String[][] weaponData = readCSV(baseUrl + "WeaponData.csv");
		ArrayList<Weapon> output = new ArrayList<>();

		int sz = weaponData.length;

		return output;
	}
}
