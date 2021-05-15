package entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Moveable;
import items.base.Armor;
import items.base.Item;
import items.base.Potion;
import items.base.Weapon;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;
import logic.GameLogic;
import logic.Sprites;
import scene.GameScene;
import utils.GameConfig;
import utils.MessageTextUtil;

/**
 * The Player class is used to represent the player. It stores status, equipped
 * armor, equipped weapon, and items that the player has.
 *
 */
public class Player extends Entity implements Moveable, Attackable {
	/**
	 * {@link Armor The armor} that the player currently equips.
	 */
	private Armor equippedArmor;
	/**
	 * {@link Weapon The weapon} that the player currently equips.
	 */
	private Weapon equippedWeapon;
	/**
	 * {@link Item The items} that the player currently has.
	 */
	private ArrayList<Item> itemList = new ArrayList<Item>();
	/**
	 * Line of sight of the player.
	 */
	private int lineOfSight;

	/**
	 * The constructor for this class.
	 */
	public Player() {
		super("Player", 25, 3, 3, 0, 0, Direction.DOWN, 1.5, 0.5);
		setLineOfSight(GameConfig.LINE_OF_SIGHT);
		setEquippedArmor(null);
		setEquippedWeapon(null);
	}

	@Override
	public int getSymbol() {
		return Sprites.PLAYER;
	}

	@Override
	public boolean attack(Entity target) {
		if (!isAttackable(target)) {
			return false;
		}
		int atkValue = GameLogic.calculateAttackValue(this, target);
		MessageTextUtil.textWhenAttack(this, target, atkValue);
		target.setHealth(target.getHealth() - atkValue);
		return true;
	}

	/**
	 * Equips the specified {@link Item item}.
	 * 
	 * @param item {@link Item The item} to be equipped
	 */
	public void equipItem(Item item) {
		item.onEquip(this);
		getItemList().remove(item);
		if (item instanceof Potion)
			GameScene.getEffectPane().update();
	}

	/**
	 * Unequips the specified {@link Item item}.
	 * 
	 * @param item {@link Item The item} to be unequipped
	 */
	public void unEquipItem(Item item) {
		item.onUnequip(this);
		getItemList().add(item);
	}

	/**
	 * Finds all cells that the player can see.
	 * 
	 * @param startIdxY Start index in the Y-axis
	 * @param endIdxY   End index in the Y-axis
	 * @param startIdxX Start index in the X-axis
	 * @param endIdxX   End index in the X-axis
	 * @return List of all visible cells
	 */
	public ArrayList<Pair<Integer, Integer>> getAllVisibleField(int startIdxY, int endIdxY, int startIdxX,
			int endIdxX) {
		int posY = this.getPosY();
		int posX = this.getPosX();

		ArrayList<Pair<Integer, Integer>> allPos = new ArrayList<Pair<Integer, Integer>>();
		Queue<Pair<Integer, Pair<Integer, Integer>>> queue = new LinkedList<>();

		final int directionArr[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { -1, -1 }, { 1, 1 }, { 1, -1 },
				{ -1, 1 } };
		final int directionSz = 8;

		queue.add(new Pair<>(0, new Pair<>(posY, posX)));

		while (queue.size() != 0) {
			int level = queue.peek().getKey();
			int nowY = queue.peek().getValue().getKey();
			int nowX = queue.peek().getValue().getValue();

			queue.remove();

			if (level > getLineOfSight()) {
				continue;
			}

			if ((nowY > endIdxY) || (nowY < startIdxY) || (nowX > endIdxX) || (nowX < startIdxX)) {
				continue;
			}

			boolean found = false;

			for (Pair<Integer, Integer> each : allPos) {
				if ((each.getKey() == nowY) && (each.getValue() == nowX)) {
					found = true;
					break;
				}
			}

			if (found) {
				continue;
			}

			allPos.add(new Pair<>(nowY, nowX));

			if (GameController.getGameMap().get(nowY, nowX).getType() == Cell.WALL) {
				continue;
			}

			for (int i = 0; i < directionSz; i++) {
				int newX = directionArr[i][0] + nowX;
				int newY = directionArr[i][1] + nowY;
				if ((directionArr[i][0] == 0) || (directionArr[i][1] == 0)) {
					queue.add(new Pair<>(level + 1, new Pair<>(newY, newX)));
				} else {
					int cellTypeY = GameController.getGameMap().get(newY, nowX).getType();
					int cellTypeX = GameController.getGameMap().get(nowY, newX).getType();

					if ((cellTypeY == Cell.WALL) && (cellTypeX == Cell.WALL)) {
						continue;
					} else {
						queue.add(new Pair<>(level + 1, new Pair<>(newY, newX)));
					}
				}
			}
		}
		return allPos;
	}

	/**
	 * Getter for equipped armor.
	 * 
	 * @return The player's equipped armor
	 */
	public Armor getEquippedArmor() {
		return equippedArmor;
	}

	/**
	 * Setter for equipped armor.
	 * 
	 * @param equippedArmor The armor to be set
	 */
	public void setEquippedArmor(Armor equippedArmor) {
		this.equippedArmor = equippedArmor;
	}

	/**
	 * Getter for equipped weapon.
	 * 
	 * @return The player's equipped weapon
	 */
	public Weapon getEquippedWeapon() {
		return equippedWeapon;
	}

	/**
	 * Setter for equipped weapon.
	 * 
	 * @param equippedWeapon The weapon to be set
	 */
	public void setEquippedWeapon(Weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	/**
	 * Getter for item list.
	 * 
	 * @return The player's item list
	 */
	public ArrayList<Item> getItemList() {
		return itemList;
	}

	/**
	 * Setter for item list.
	 * 
	 * @param itemList The item list to be set
	 */
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * Getter for line of sight.
	 * 
	 * @return The player's line of sight
	 */
	public int getLineOfSight() {
		return lineOfSight;
	}

	/**
	 * Setter for line of sight.
	 * 
	 * @param lineOfSight The line of sight to be set
	 */
	public void setLineOfSight(int lineOfSight) {
		this.lineOfSight = Math.max(1, lineOfSight);
	}
}
