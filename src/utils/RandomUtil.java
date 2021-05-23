package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import entity.DarkMage;
import entity.HauntedMaid;
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

/**
 * The RandomUtil is the class that provide the random method and random the
 * status of {@link Potion}, {@link Armor} and {@link Weapon} from pool.
 *
 */
public class RandomUtil {

	/**
	 * Represent the {@link Random} instance.
	 */
	private static Random rand = new Random();

	/**
	 * Represent the pool of {@link Potion} that used for random in each level.
	 */
	private static ArrayList<Potion> potionPool;

	/**
	 * Represent the pool of {@link Armor} that used for random in each level.
	 */
	private static ArrayList<Armor> armorPool;

	/**
	 * Represent the pool of {@link Weapon} that used for random in each level.
	 */
	private static ArrayList<Weapon> weaponPool;

	/**
	 * Represent the list of filter which monster can appear in that level.
	 * 
	 * @see MonsterLevelFilter
	 */
	private static ArrayList<MonsterLevelFilter> monsterFilter;

	/**
	 * Represent the present index of {@link #monsterFilter}
	 */
	private static int filterIndex = 0;

	/**
	 * Represent the minimum of {@link Potion} that generated in each level.
	 */
	private static final int MIN_POTION = 4;

	/**
	 * Represent the maximum of {@link Potion} that generated in each level.
	 */
	private static final int MAX_POTION = 10;

	/**
	 * Represent the minimum of {@link Monster} that generated in each level.
	 */
	private static final int MIN_MONSTER = 20;

	/**
	 * Represent the maximum of {@link Monster} that generated in each level.
	 */
	private static final int MAX_MONSTER = 40;

	/**
	 * Represent the minimum of {@link Weapon} that generated in each level.
	 */
	private static final int MIN_WEAPON = 5;

	/**
	 * Represent the maximum of {@link Weapon} that generated in each level.
	 */
	private static final int MAX_WEAPON = 10;

	/**
	 * Represent the minimum of {@link Armor} that generated in each level.
	 */
	private static final int MIN_ARMOR = 5;

	/**
	 * Represent the maximum of {@link Armor} that generated in each level.
	 */
	private static final int MAX_ARMOR = 10;

	/**
	 * Read the data of {@link Potion}, {@link Armor}, {@link Weapon} and
	 * {@link MonsterLevelFilter} from CSV file.
	 */
	static {
		potionPool = CSVUtil.readPotionCSV();
		armorPool = CSVUtil.readArmorCSV();
		weaponPool = CSVUtil.readWeaponCSV();
		monsterFilter = CSVUtil.readMonsterFilterCSV();
	}

	/**
	 * Random the integer in range st and ed (inclusive).
	 * 
	 * @param st the starting of range
	 * @param ed the ending of range
	 * @return the random integer in range
	 */
	public static int random(int st, int ed) {
		if (st > ed)
			return 0;
		return st + rand.nextInt(ed - st + 1);
	}

	/**
	 * The utility method that shuffle the {@link Integer} array.
	 * 
	 * @param intArray the integer array that will be shuffled
	 */
	public static void shuffle(Integer[] intArray) {
		List<Integer> intList = Arrays.asList(intArray);
		Collections.shuffle(intList);

		intList.toArray(intArray);
	}

	/**
	 * The utility method that shuffle the two-dimensional integer array.
	 * 
	 * @param array the two-dimensional integer array that will be shuffled
	 */
	public static void shuffle(int[][] array) {
		List<int[]> list = Arrays.asList(array);
		Collections.shuffle(list);
		list.toArray(array);
	}

	/**
	 * The utility method that will generate the {@link Potion} list that
	 * corresponding to level.
	 * 
	 * @param level the parameter which use as the factor of status
	 * @return the {@link ArrayList} of {@link Potion}
	 */
	public static ArrayList<Potion> randomPotionList(int level) {
		int numberOfPotion = random(MIN_POTION, MAX_POTION);
		int nowIdx = 0;
		ArrayList<Potion> potionList = new ArrayList<>();

		Collections.shuffle(potionPool);

		while (numberOfPotion > 0 && potionPool.size() > 0) {
			if (nowIdx >= potionPool.size()) {
				nowIdx -= potionPool.size();
			}

			Potion newPotion = (Potion) (potionPool.get(nowIdx).clone());

			if (!(newPotion instanceof VisionPotion)) {
				int newValue = newPotion.getEffectValue();
				if (!newPotion.isPermanent()) {
					newValue += level;
				}
				newPotion.setEffectValue(newValue);
			}
			newPotion.setDescription(newPotion.getDescription() + " [%d]".formatted(newPotion.getEffectValue()));
			potionList.add(newPotion);

			nowIdx += 1;
			numberOfPotion -= 1;
		}

		return potionList;
	}

	/**
	 * The utility method that will generate the {@link Monster} list that
	 * corresponding to level.
	 * 
	 * @param level the parameter which use as the factor of status
	 * @return the {@link ArrayList} of {@link Monster}
	 */
	public static ArrayList<Monster> randomMonsterList(int level) {
		int numberOfAllMonster = random(MIN_MONSTER, MAX_MONSTER);

		ArrayList<Monster> monsterList = new ArrayList<>();
		
		int newIndex = Math.min(filterIndex + 1, monsterFilter.size() - 1);
		
		if (level >= monsterFilter.get(newIndex).getLevel()) {
			filterIndex = newIndex;
		}

		MonsterLevelFilter levelFilter = monsterFilter.get(filterIndex);

		int monsterMinHealth = level * 3;
		int monsterMaxHealth = level * 6;

		int monsterMinAttack = 1;
		int monsterMaxAttack = level + 7;

		int monsterMinDefense = 0;
		int monsterMaxDefense = level + 3;

		// Begin to generate Dark Mage
		int darkMageAmount = random(3, numberOfAllMonster / 4);
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

		// Begin to generate Soul
		int soulAmount = random(3, numberOfAllMonster / 4);
		if (levelFilter.isSoulAppear()) {
			for (int i = 0; i < soulAmount; i++) {
				monsterList.add(new Soul(0, 0));
			}
			numberOfAllMonster -= soulAmount;
		}

		// Begin to generate Reaper
		int reaperAmount = random(3, numberOfAllMonster / 4);
		if (levelFilter.isReaperAppear()) {
			for (int i = 0; i < reaperAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomAttack = random(monsterMinAttack, monsterMaxAttack);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				monsterList.add(new Reaper(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.5, 0.5));
			}
			numberOfAllMonster -= reaperAmount;
		}

		// Begin to generate Haunted Maid
		int hauntedMaidAmount = random(5, numberOfAllMonster / 2);
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

		// Begin to generate Pumpkin Head
		int pumpkinHeadAmount = random(5, numberOfAllMonster / 2);
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

		// Begin to generate Skeleton
		int skeletonAmount = random(5, numberOfAllMonster);
		if (levelFilter.isSkeletonAppear()) {
			for (int i = 0; i < skeletonAmount; i++) {
				int randomHealth = random(monsterMinHealth, monsterMaxHealth);
				int randomAttack = random(monsterMinAttack, monsterMaxAttack);
				int randomDefense = random(monsterMinDefense, monsterMaxDefense);
				monsterList
						.add(new Skeleton(randomHealth, randomAttack, randomDefense, 0, 0, Direction.DOWN, 1.25, 0.10));
			}
			numberOfAllMonster -= skeletonAmount;
		}

		return monsterList;
	}

	/**
	 * The utility method that will generate the {@link Weapon} list that
	 * corresponding to level.
	 * 
	 * @param level the parameter which use as the factor of status
	 * @return the {@link ArrayList} of {@link Weapon}
	 */
	public static ArrayList<Weapon> randomWeaponList(int level) {
		int numberOfWeapon = random(MIN_WEAPON, MAX_WEAPON);
		int nowIdx = 0;
		ArrayList<Weapon> weaponList = new ArrayList<>();

		Collections.shuffle(weaponPool);

		while (numberOfWeapon > 0 && weaponPool.size() > 0) {
			if (nowIdx >= weaponPool.size()) {
				nowIdx -= weaponPool.size();
			}

			Weapon newWeapon = (weaponPool.get(nowIdx).clone());

			newWeapon.setAttack(newWeapon.getAttack() + level);

			newWeapon.setDescription(newWeapon.getDescription() + " [%d]".formatted(newWeapon.getAttack()));
			weaponList.add(newWeapon);

			nowIdx += 1;
			numberOfWeapon -= 1;
		}

		return weaponList;
	}

	/**
	 * The utility method that will generate the {@link Armor} list that
	 * corresponding to level.
	 * 
	 * @param level the parameter which use as the factor of status
	 * @return the {@link ArrayList} of {@link Armor}
	 */
	public static ArrayList<Armor> randomArmorList(int level) {
		int numberOfArmor = random(MIN_ARMOR, MAX_ARMOR);
		int nowIdx = 0;
		ArrayList<Armor> armorList = new ArrayList<>();

		Collections.shuffle(armorPool);

		while (numberOfArmor > 0 && armorPool.size() > 0) {
			if (nowIdx >= armorPool.size()) {
				nowIdx -= armorPool.size();
			}

			Armor newArmor = (armorPool.get(nowIdx).clone());

			newArmor.setDefense(newArmor.getDefense() + level);

			newArmor.setDescription(newArmor.getDescription() + " [%d]".formatted(newArmor.getDefense()));
			armorList.add(newArmor);

			nowIdx += 1;
			numberOfArmor -= 1;
		}

		return armorList;
	}

	/**
	 * Reset {@link #filterIndex} when starting a new game.
	 */
	public static void resetFilterIndex() {
		RandomUtil.filterIndex = 0;
	}
}
