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
import items.potion.VisionPotion;
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
		int nowIdx = 0;
		ArrayList<Potion> potionList = new ArrayList<>();

		Collections.shuffle(potionPool);

		while(numberOfPotion > 0 && potionPool.size() > 0) {
			if(nowIdx >= potionPool.size()) {
				nowIdx -= potionPool.size();
			}
			
			Potion newPotion = (Potion) (potionPool.get(nowIdx).clone());
			
			if(!(newPotion instanceof VisionPotion)) {
				int lowerBound = (newPotion.getPotionValue() + level) / 2;
				int upperBound = newPotion.getPotionValue() + level;
				
				newPotion.setPotionValue(random(lowerBound, upperBound));	
			}
			potionList.add(newPotion);
			
			nowIdx += 1;
			numberOfPotion -= 1;
		}
		
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

		int monsterMinHealth = level * 4;
		int monsterMaxHealth = level * 8;

		int monsterMinAttack = 1;
		int monsterMaxAttack = level + 10;

		int monsterMinDefense = 0;
		int monsterMaxDefense = level + 3;

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
						.add(new Reaper(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.5, 0.5));
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
						new HauntedMaid(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 2.0, 0.25));
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
						new PumpkinHead(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.4, 0.10));
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
						new Skeleton(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.25, 0.10));
			}
		}

		return monsterList;
	}

	public static ArrayList<Weapon> randomWeaponList(Player player, int level) {
		// TODO WeaponList Generator
		int numberOfWeapon = random(MIN_WEAPON, MAX_WEAPON);
		int nowIdx = 0;
		ArrayList<Weapon> weaponList = new ArrayList<>();
		
		Collections.shuffle(weaponPool);

		while(numberOfWeapon > 0 && weaponPool.size() > 0) {
			if(nowIdx >= weaponPool.size()) {
				nowIdx -= weaponPool.size();
			}
			
			Weapon newWeapon = (Weapon) (weaponPool.get(nowIdx).clone());
			
			int lowerBound = (newWeapon.getAttack() + level) / 2;
			int upperBound = newWeapon.getAttack() + level;
			
			newWeapon.setAttack(random(lowerBound, upperBound));
			weaponList.add(newWeapon);
			
			nowIdx += 1;
			numberOfWeapon -= 1;
		}
		
		return weaponList;
	}

	public static ArrayList<Armor> randomArmorList(Player player, int level) {
		// TODO ArmorList Generator
		int numberOfArmor = random(MIN_ARMOR, MAX_ARMOR);
		int nowIdx = 0;
		ArrayList<Armor> armorList = new ArrayList<>();

		Collections.shuffle(armorPool);
		
		while(numberOfArmor > 0 && armorPool.size() > 0) {
			if(nowIdx >= armorPool.size()) {
				nowIdx -= armorPool.size();
			}
			
			Armor newArmor = (Armor) (armorPool.get(nowIdx).clone());
			
			int lowerBound = (newArmor.getDefense() + level) / 2;
			int upperBound = newArmor.getDefense() + level;
			
			newArmor.setDefense(random(lowerBound, upperBound));
			armorList.add(newArmor);
			
			nowIdx += 1;
			numberOfArmor -= 1;
		}
		
		return armorList;
	}
}
