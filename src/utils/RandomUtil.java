package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entity.Player;
import entity.base.Monster;
import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;

public class RandomUtil {
	private static Random rand = new Random();
	private static ArrayList<Potion> potionPool;
	private static ArrayList<Armor> armorPool;
	private static ArrayList<Weapon> weaponPool;
	
	private static final int MIN_POTION = 4;
	private static final int MAX_POTION = 10;

	private static final int MIN_MONSTER = 20;
	private static final int MAX_MONSTER = 40;
	
	private static final int MIN_WEAPON = 3;
	private static final int MAX_WEAPON = 5;
	
	private static final int MIN_ARMOR = 10;
	private static final int MAX_ARMOR = 10;
	
	static {
		potionPool = CSVUtil.readPotionCSV();
		armorPool = CSVUtil.readArmorCSV();
		weaponPool = CSVUtil.readWeaponCSV();
	}
	
	public static int random(int st, int ed) {
		return st + rand.nextInt(ed - st + 1);
	}

	public static void shuffle(Integer[] intArray) {
		List<Integer> intList = Arrays.asList(intArray);
		Collections.shuffle(intList);
		
		intList.toArray(intArray);
	}

	public static void shuffle(int[][] array) {
		List<int[]> list = Arrays.asList(array);
		Collections.shuffle(list);
		list.toArray(array);
	}
	
	public static ArrayList<Potion> randomPotionList(Player player, int level) {
		// TODO PotionList Generator
		int numberOfPotion = random(MIN_POTION, MAX_POTION);
		Collections.shuffle(potionPool);
		
		ArrayList<Potion> potionList = new ArrayList<>();
		
		return potionList;
	}
	
	public static ArrayList<Monster> randomMonsterList(Player player, int level) {
		// TODO MonsterList Generator
		int numberOfMonster = random(MIN_MONSTER, MAX_MONSTER);
		
		ArrayList<Monster> monsterList = new ArrayList<>();
		
		return null;
	}
	
	public static ArrayList<Weapon> randomWeaponList(Player player, int level) {
		// TODO WeaponList Generator
		int numberOfWeapon = random(MIN_WEAPON, MAX_WEAPON);
		Collections.shuffle(weaponPool);
		
		ArrayList<Weapon> weaponList = new ArrayList<>();
		
		return weaponList;
	}
	
	public static ArrayList<Armor> randomArmorList(Player player, int level) {
		// TODO ArmorList Generator
		int numberOfArmor = random(MIN_ARMOR, MAX_ARMOR);
		ArrayList<Armor> armorList = new ArrayList<>();
		
		Collections.shuffle(armorPool);
		
		Armor x = (Armor) armorPool.get(0).clone();
		
		return armorPool;
	}
}
