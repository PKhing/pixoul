package entity.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.GameController;
import controller.InterruptController;
import effects.base.EntityEffect;
import logic.Cell;
import logic.Direction;
import logic.GameMap;
import utils.MessageTextUtil;

/**
 * Base class to represent all entity
 */
public abstract class Entity {
	public final int MOVE_SPEED = 1;

	/*
	 * Name of this entity
	 */
	private String name;

	/**
	 * 
	 */
	private int health;

	private int attack;

	private int maxHealth;

	private int defense;

	private int posX;

	private int posY;

	private int direction;

	private double critRate;

	private double critPercent;

	private boolean isMoving;

	private boolean isAttacked;

	private List<EntityEffect> effectList;

	public Entity(String name, int maxHealth, int attack, int defense, int posY, int posX, int direction,
			double critRate, double critPercent) {
		setName(name);
		setHealth(maxHealth);
		setMaxHealth(maxHealth);
		setAttack(attack);
		setDefense(defense);
		setPosX(posX);
		setPosY(posY);
		setDirection(direction);
		setCritRate(critRate);
		setCritPercent(critPercent);
		setMaxHealth(maxHealth);
		setMoving(false);
		setAttacked(false);
		setEffectList(new CopyOnWriteArrayList<>());

	}

	public abstract int getSymbol();

	public boolean move(int direction) {
		if (!(this instanceof Moveable)) {
			return false;
		}

		int newPosY = getPosY() + Direction.getMoveY(direction, MOVE_SPEED);
		int newPosX = getPosX() + Direction.getMoveX(direction, MOVE_SPEED);

		setDirection(direction);

		Cell newPosCell = GameController.getGameMap().get(newPosY, newPosX);

		if ((newPosCell.getType() != Cell.WALL) && !(newPosCell.getEntity() instanceof Entity)) {
			GameController.getGameMap().get(getPosY(), getPosX()).setEntity(null);
			GameController.getGameMap().get(newPosY, newPosX).setEntity(this);
			setPosY(newPosY);
			setPosX(newPosX);
			this.setMoving(true);
			return true;
		}

		return false;
	}

	public void setPositionOnMap(int posY, int posX) {
		if (GameController.getGameMap() != null) {
			if (GameController.getGameMap().get(this.getPosY(), this.getPosX()).getEntity() == this) {
				GameController.getGameMap().get(this.getPosY(), this.getPosX()).setEntity(null);
			}
			GameController.getGameMap().get(posY, posX).setEntity(this);
		}

		setInternalPostion(posY, posX);
	}

	public void setInternalPostion(int posY, int posX) {
		setPosX(posX);
		setPosY(posY);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = Math.max(0, health);
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = Math.max(1, attack);
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = Math.max(1, defense);
	}

	public double getCritRate() {
		return critRate;
	}

	public void setCritRate(double critRate) {
		this.critRate = Math.max(1, critRate);
	}

	public double getCritPercent() {
		return critPercent;
	}

	public void setCritPercent(double critPercent) {
		this.critPercent = Math.max(Math.min(1, critPercent), 0);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Math.max(1, maxHealth);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EntityEffect> getEffectList() {
		return effectList;
	}

	public void setEffectList(List<EntityEffect> effectList) {
		this.effectList = effectList;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isAttacked() {
		return isAttacked;
	}

	public void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}

	protected boolean isAttackable(Entity x) {
		int diffX = Math.abs(x.getPosX() - getPosX());
		int diffY = Math.abs(x.getPosY() - getPosY());

		boolean isBothOne = diffX == 1 && diffY == 1;

		if (isBothOne) {
			GameMap thisMap = GameController.getGameMap();
			if ((thisMap.get(x.getPosY(), getPosX()).getType() != Cell.WALL)
					|| (thisMap.get(getPosY(), x.getPosX()).getType() != Cell.WALL)) {
				return !InterruptController.isTransition();

			}
		} else if (diffX <= 1 && diffY <= 1) {
			return !InterruptController.isTransition();
		}

		return false;
	}

	protected void remove() {
		GameController.getGameMap().get(this.getPosY(), this.getPosX()).setEntity(null);
		GameController.getGameMap().getMonsterList().remove(this);
		MessageTextUtil.textWhenSlained(this);
	}

}
