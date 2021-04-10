package entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import scene.GameScene;
import utils.GameConfig;
import utils.MessageTextUtil;

public class Player extends Entity implements Moveable, Attackable {
	private Armor equippedArmor;
	private Weapon equippedWeapon;
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private int lineOfSight;
	
	public Player() {
		super("Player", 5, 10, 1, 0, 0, Direction.DOWN, 1.5, 1, 1);
		setLineOfSight(GameConfig.LINE_OF_SIGHT);
		setEquippedArmor(null);
		setEquippedWeapon(null);
	}

	public void setInitialPos(int posY, int posX) {
		GameController.getGameMap().get(posY, posX).setEntity(this);
		setPosX(posX);
		setPosY(posY);
	}
	
	@Override
	public boolean move(int direction) {
		int newPosY = getPosY();
		int newPosX = getPosX();
		if (direction == Direction.UP)
			newPosY -= getMoveSpeed();
		if (direction == Direction.DOWN)
			newPosY += getMoveSpeed();
		if (direction == Direction.LEFT)
			newPosX -= getMoveSpeed();
		if (direction == Direction.RIGHT)
			newPosX += getMoveSpeed();

		setDirection(direction);

		Cell newPosCell = GameController.getGameMap().get(newPosY, newPosX);

		if (newPosCell.getType() != Cell.WALL && !(newPosCell.getEntity() instanceof Entity)) {
			GameController.getGameMap().get(getPosY(), getPosX()).setEntity(null);
			GameController.getGameMap().get(newPosY, newPosX).setEntity(this);
			setPosY(newPosY);
			setPosX(newPosX);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean attack(Entity target) {
		int atkValue = GameLogic.calculateAttackValue(this, target);
		MessageTextUtil.textWhenAttack(this, target, atkValue);
		target.setHealth(target.getHealth() - atkValue);
		return true;
	}
	
	public void equipItem(Item item) {
		item.onEquip(this);
		getItemList().remove(item);
		if(item instanceof Potion) GameScene.getEffectPane().update();
	}

	public void unEquipItem(Item item) {
		item.onUnequip(this);
		getItemList().add(item);
	}

	public ArrayList<Pair<Integer, Integer>> getAllVisibleField(int startIdxY, int endIdxY, int startIdxX, int endIdxX) {
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
			
			if(nowY > endIdxY || nowY < startIdxY || nowX > endIdxX || nowX < startIdxX) {
				continue;
			}

			boolean found = false;

			for (Pair<Integer, Integer> each : allPos) {
				if (each.getKey() == nowY && each.getValue() == nowX) {
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
				if (directionArr[i][0] == 0 || directionArr[i][1] == 0) {
					queue.add(new Pair<>(level + 1, new Pair<>(newY, newX)));
				} else {
					int cellTypeY = GameController.getGameMap().get(newY, nowX).getType();
					int cellTypeX = GameController.getGameMap().get(nowY, newX).getType();

					if (cellTypeY == Cell.WALL && cellTypeX == Cell.WALL) {
						continue;
					} else {
						queue.add(new Pair<>(level + 1, new Pair<>(newY, newX)));
					}
				}
			}
		}
		return allPos;
	}
	
	public Armor getEquippedArmor() {
		return equippedArmor;
	}

	public void setEquippedArmor(Armor equippedArmor) {
		this.equippedArmor = equippedArmor;
	}

	public Weapon getEquippedWeapon() {
		return equippedWeapon;
	}

	public void setEquippedWeapon(Weapon equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}
	
	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public int getLineOfSight() {
		return lineOfSight;
	}

	public void setLineOfSight(int lineOfSight) {
		this.lineOfSight = Math.max(1, lineOfSight);
	}
}
