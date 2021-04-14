package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entity.base.Monster;
import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;

public class CSVUtil {
	private static final String baseUrl = "res/csv/";
	
	public static ArrayList<String[]> readCSV(String filename) {
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(filename));
			ArrayList<String[]> output = new ArrayList<>();
			String row = null;
			while ((row = csvReader.readLine()) != null) {
			    String[] data = row.split(",");
			    output.add(data);
			}
			csvReader.close();
			return output;
		} catch(IOException e) {
			System.out.println("%s not found".formatted(filename));
			return null;
		}
	}
	
	public static ArrayList<Monster> readMonsterCSV() {
		ArrayList<String[]> monsterData = readCSV(baseUrl + "monster.csv");
		ArrayList<Monster> output = new ArrayList<>();
		
		return output;
	}
	
	public static ArrayList<Potion> readPotionCSV() {
		ArrayList<String[]> potionData = readCSV(baseUrl + "potion.csv");
		ArrayList<Potion> output = new ArrayList<>();
		
		return output;
	}
	
	public static ArrayList<Armor> readArmorCSV() {
		ArrayList<String[]> armorData = readCSV(baseUrl + "armor.csv");
		ArrayList<Armor> output = new ArrayList<>();
		
		return output;
	}
	
	public static ArrayList<Weapon> readWeaponCSV() {
		ArrayList<String[]> weaponData = readCSV(baseUrl + "weapon.csv");
		ArrayList<Weapon> output = new ArrayList<>();
		
		return output;
	}
}
