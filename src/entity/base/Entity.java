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
 * Base class to represent all entities.
 */
public abstract class Entity {

	/**
	 * Default speed of entity
	 */
	public final int MOVE_SPEED = 1;

	/*
	 * Name of this entity.
	 */
	private String name;

	/**
	 * Health point of this entity.
	 */
	private int health;

	/**
	 * Attack value of this entity.
	 */
	private int attack;

	/**
	 * Max amount of health point of this entity.
	 */
	private int maxHealth;

	/**
	 * Defense value of this entity.
	 */
	private int defense;

	/**
	 * Position of this entity in the X-axis.
	 */
	private int posX;

	/**
	 * Position of this entity in the Y-axis.
	 */
	private int posY;

	/**
	 * {@link Direction} of this monster.
	 */
	private int direction;

	/**
	 * Critical rate of this entity.
	 */
	private double critRate;

	/**
	 * Critical damage percent of this entity.
	 */
	private double critDamagePercent;

	/**
	 * Determines whether this entity is moving or not.
	 */
	private boolean isMoving;

	/**
	 * Determines whether this entity is attacked or not.
	 */
	private boolean isAttacked;

	/**
	 * List of {@link EntityEffect effects} that this entity has.
	 */
	private List<EntityEffect> effectList;

	/**
	 * The constructor for this class.
	 * 
	 * @param name              Name of this entity
	 * @param maxHealth         Max amount of health point of this entity
	 * @param attack            Attack value of this entity
	 * @param defense           Defense value of this entity
	 * @param posY              Position of this entity in the Y-axis
	 * @param posX              Position of this entity in the X-axis
	 * @param direction         {@link Direction} of this monster
	 * @param critRate          Critical rate of this entity
	 * @param critDamagePercent Critical damage percent of this entity
	 */
	public Entity(String name, int maxHealth, int attack, int defense, int posY, int posX, int direction,
			double critRate, double critDamagePercent) {
		setName(name);
		setHealth(maxHealth);
		setMaxHealth(maxHealth);
		setAttack(attack);
		setDefense(defense);
		setPosX(posX);
		setPosY(posY);
		setDirection(direction);
		setCritRate(critRate);
		setCritDamagePercent(critDamagePercent);
		setMaxHealth(maxHealth);
		setMoving(false);
		setAttacked(false);
		setEffectList(new CopyOnWriteArrayList<>());

	}

	/**
	 * Getter for sprite symbol of this entity.
	 * 
	 * @return This entity's sprite symbol
	 */
	public abstract int getSymbol();

	/**
	 * Moves this entity in the specified {@link Direction direction}.
	 * 
	 * @param direction The direction that this entity will move in
	 * @return True if move success; false otherwise
	 */
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

	/**
	 * Sets this monster on {@link GameMap game map} at the specified position.
	 * 
	 * @param posY The position to be set in the Y-axis
	 * @param posX The position to be set in the X-axis
	 */
	public void setPositionOnMap(int posY, int posX) {
		if (GameController.getGameMap() != null) {
			if (GameController.getGameMap().get(this.getPosY(), this.getPosX()).getEntity() == this) {
				GameController.getGameMap().get(this.getPosY(), this.getPosX()).setEntity(null);
			}
			GameController.getGameMap().get(posY, posX).setEntity(this);
		}

		setInternalPostion(posY, posX);
	}

	/**
	 * Sets this monster position.
	 * 
	 * @param posY The position to be set in the Y-axis
	 * @param posX The position to be set in the X-axis
	 */
	public void setInternalPostion(int posY, int posX) {
		setPosX(posX);
		setPosY(posY);
	}

	/**
	 * Getter for health point.
	 * 
	 * @return This entity's health point
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Setter for health point.
	 * 
	 * @param health The health point to be set
	 */
	public void setHealth(int health) {
		this.health = Math.max(0, health);
	}

	/**
	 * Getter for attack value.
	 * 
	 * @return This entity's attack value
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Setter for attack value.
	 * 
	 * @param attack The attack value to be set
	 */
	public void setAttack(int attack) {
		this.attack = Math.max(1, attack);
	}

	/**
	 * Getter for defense value.
	 * 
	 * @return This entity's defense value
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Setter for defense value.
	 * 
	 * @param attack The defense value to be set
	 */
	public void setDefense(int defense) {
		this.defense = Math.max(1, defense);
	}

	/**
	 * Getter for critical rate.
	 * 
	 * @return This entity's critical rate
	 */
	public double getCritRate() {
		return critRate;
	}

	/**
	 * Setter for critical rate.
	 * 
	 * @param attack The critical rate to be set
	 */
	public void setCritRate(double critRate) {
		this.critRate = Math.max(1, critRate);
	}

	/**
	 * Getter for critical damage percent.
	 * 
	 * @return This entity's critical damage percent
	 */
	public double getCritDamagePercent() {
		return critDamagePercent;
	}

	/**
	 * Setter for critical damage percent.
	 * 
	 * @param attack The critical damage percent to be set
	 */
	public void setCritDamagePercent(double critDamagePercent) {
		this.critDamagePercent = Math.max(Math.min(1, critDamagePercent), 0);
	}

	/**
	 * Getter for position in the X-axis.
	 * 
	 * @return This entity's position in the X-axis
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Setter for position in X-axis.
	 * 
	 * @param attack The position to be set in X-axis
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Getter for position in the Y-axis.
	 * 
	 * @return This entity's position in the Y-axis
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Setter for position in Y-axis.
	 * 
	 * @param attack The position to be set in Y-axis
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * Getter for {@link Direction direction}.
	 * 
	 * @return This entity's {@link Direction direction}
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Setter for {@link Direction direction}.
	 * 
	 * @param direction The {@link Direction direction to be set}
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * Getter for max health point.
	 * 
	 * @return This entity's max health point
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = Math.max(1, maxHealth);
	}

	/**
	 * Getter for name.
	 * 
	 * @return This entity's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * 
	 * @param name The name to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for {@link EntityEffect effect} list.
	 * 
	 * @return This entity's {@link EntityEffect effect} list
	 */
	public List<EntityEffect> getEffectList() {
		return effectList;
	}

	/**
	 * Setter for {@link EntityEffect effect} list.
	 * 
	 * @param effectList The {@link EntityEffect effect} list to be set
	 */
	public void setEffectList(List<EntityEffect> effectList) {
		this.effectList = effectList;
	}

	/**
	 * Getter for isMoving.
	 * 
	 * @return True if this entity is moving; false otherwise
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Setter for isMoving.
	 * 
	 * @param isMoving Moving status to be set
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	/**
	 * Getter for isAttacked.
	 * 
	 * @return True if this entity is attacked; false otherwise
	 */
	public boolean isAttacked() {
		return isAttacked;
	}

	/**
	 * Setter for isAttacked.
	 * 
	 * @param isAttacked Attack status to be set
	 */
	public void setAttacked(boolean isAttacked) {
		this.isAttacked = isAttacked;
	}

	/**
	 * Checks if this entity can attack the specified entity or not.
	 * 
	 * @param x The entity to be checked
	 * @return True if this entity can attack the specified entity; false otherwise
	 */
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

	/**
	 * Removes this entity from {@link GameMap game map}.
	 */
	protected void remove() {
		GameController.getGameMap().get(this.getPosY(), this.getPosX()).setEntity(null);
		GameController.getGameMap().getMonsterList().remove(this);
		MessageTextUtil.textWhenSlained(this);
	}

}
