package entity;

import java.util.ArrayList;

import entity.base.Entity;
import entity.base.Moveable;
import items.base.Armor;
import items.base.Potion;
import items.base.Weapon;
import logic.Cell;
import logic.Direction;
import logic.GameMap;

public class Player extends Entity implements Moveable {
	private ArrayList<Potion> potionList;
	private Armor equippedArmor;
	private Weapon equippedWeapon;
	
	public Player(int posY, int posX, GameMap gameMap) {
		super(1, 1, 10, 1, posX, posY, Direction.DOWN, 1, 1, 1);
		gameMap.get(posY, posX).setEntity(this);
		setPotionList(new ArrayList<>());
		setEquippedArmor(null);
		setEquippedWeapon(null);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(GameMap gameMap, int direction) {
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
		
		Cell newPosCell = gameMap.get(newPosY, newPosX);
		
		if (newPosCell.getType() != Cell.WALL && !(newPosCell.getEntity() instanceof Entity)) {
			gameMap.get(getPosY(), getPosX()).setEntity(null);
			gameMap.get(newPosY, newPosX).setEntity(this);
			setPosY(newPosY);
			setPosX(newPosX);
		}
	}
	
	public void equip(Weapon w) {
		w.onEquip(this);
	}
	
	public void deEquip(Weapon w) {
		w.onDeequip(this);
	}
	
	public void usePotion(Potion p) {
		p.onUsed(this);
	}

	public ArrayList<Potion> getPotionList() {
		return potionList;
	}

	public void setPotionList(ArrayList<Potion> potionList) {
		this.potionList = potionList;
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
}
