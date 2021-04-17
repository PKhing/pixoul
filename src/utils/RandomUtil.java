package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entity.DarkMage;
import entity.HauntedMaid;
import entity.Player;
import entity.PumpkinHead;
import entity.Reaper;
import entity.Skeleton;
import entity.Soul;
import entity.base.Monster;
import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;
import logic.Direction;

public class RandomUtil {
	private static Random rand = new Random();
	private static ArrayList<Potion> potionPool;
	private static ArrayList<Armor> armorPool;
	private static ArrayList<Weapon> weaponPool;
	private static ArrayList<MonsterLevelFilter> monsterFilter;
	private static int filterIndex = 0;

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
		monsterFilter = CSVUtil.readMonsterFilterCSV();
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
		int numberOfAllMonster = random(MIN_MONSTER, MAX_MONSTER);

		ArrayList<Monster> monsterList = new ArrayList<>();
		if (level > monsterFilter.get(filterIndex).getLevel()) {
			filterIndex = Math.min(filterIndex + 1, monsterFilter.size() - 1);
		}

		MonsterLevelFilter levelFilter = monsterFilter.get(filterIndex);
		int playerAttack = player.getAttack();
		int playerDefense = player.getDefense();

		int monsterMinHealth = playerAttack * 3 + level * 5;
		int monsterMaxHealth = playerAttack * 5 + level * 5;

		int monsterMinAttack = (int) (playerDefense * 0.5 + level * 2);
		int monsterMaxAttack = (int) (playerDefense * 1.5 + level * 2);

		int monsterMinDefense = level + 3;
		int monsterMaxDefense = level + 7;

		int darkMageAmount = random(0, numberOfAllMonster / 4);
		if (levelFilter.isDarkMageAppear()) {
			for (int i = 0; i < darkMageAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				int poisonDamage = random(1, level + 2);
				monsterList
						.add(new DarkMage(randomHealth, randomDefense, 0, 0, Direction.RIGHT, poisonDamage, level + 1));
			}
			numberOfAllMonster -= darkMageAmount;
		}

		int soulAmount = random(0, numberOfAllMonster / 4);
		if (levelFilter.isSoulAppear()) {
			for (int i = 0; i < soulAmount; i++) {
				monsterList.add(new Soul(0, 0));
			}
			numberOfAllMonster -= soulAmount;
		}

		int reaperAmount = random(0, numberOfAllMonster / 4);
		if (levelFilter.isReaperAppear()) {
			for (int i = 0; i < reaperAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomAttack = random(monsterMinAttack, monsterMaxAttack);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				monsterList
						.add(new Reaper(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.5, 0.5, 1));
			}
			numberOfAllMonster -= reaperAmount;
		}

		int hauntedMaidAmount = random(0, numberOfAllMonster / 2);
		if (levelFilter.isHauntedMaidAppear()) {
			for (int i = 0; i < hauntedMaidAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomAttack = random(monsterMinAttack, monsterMaxAttack);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				monsterList.add(
						new HauntedMaid(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 2.0, 0.25, 1));
			}
			numberOfAllMonster -= hauntedMaidAmount;
		}

		int pumpkinHeadAmount = random(0, numberOfAllMonster / 2);
		if (levelFilter.isPumpkinHeadAppear()) {
			for (int i = 0; i < pumpkinHeadAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomAttack = random(monsterMinAttack, monsterMaxAttack);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				monsterList.add(
						new PumpkinHead(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.4, 0.10, 1));
			}
			numberOfAllMonster -= pumpkinHeadAmount;
		}

		int skeletonAmount = random(0, numberOfAllMonster / 2);
		if (levelFilter.isSkeletonAppear()) {
			for (int i = 0; i < skeletonAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomAttack = random(monsterMinAttack, monsterMaxAttack);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				monsterList.add(
						new Skeleton(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.25, 0.10, 1));
			}
		}

		return monsterList;
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

		return armorList;
	}
}
