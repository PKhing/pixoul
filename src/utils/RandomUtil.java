package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entity.Player;
import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;

public class RandomUtil {
	private static Random rand = new Random();
	
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
	
	public static void randomPotionList(Player player, int floor) {
		// TODO PotionList Generator
	}
	
	public static void randomMonsterList(Player player, int floor) {
		// TODO MonsterList Generator
	}
	
	public static void randomWeaponList(Player player, int floor) {
		// TODO WeaponList Generator
	}
	
	public static void randomArmorList(Player player, int floor) {
		// TODO ArmorList Generator
	}
}
