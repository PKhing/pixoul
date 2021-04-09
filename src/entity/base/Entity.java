package entity.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.GameController;
import effects.EntityEffect;
import logic.Renderable;
import utils.MessageTextUtil;

public abstract class Entity implements Renderable{
	private String name;

	private int health;

	private int attack;

	private int maxHealth;

	private int defense;

	private int posX;

	private int posY;

	private int direction;

	private double critRate;

	private double critPercent;
	
	private int moveSpeed;

	private List<EntityEffect> effectList;

	public Entity(String name, int attack, int maxHealth, int defense, int posY, int posX, int direction,
			double critRate, double critPercent, int moveSpeed) {
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
		setMoveSpeed(moveSpeed);
		setMaxHealth(maxHealth);
		setEffectList(new CopyOnWriteArrayList<>());
	}

	public void remove() {
		GameController.getGameMap().get(this.getPosY(), this.getPosX()).setEntity(null);
		GameController.getGameMap().getMonsterList().remove(this);
		MessageTextUtil.textWhenSlained(this);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
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

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = Math.max(1, moveSpeed);
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
		this.maxHealth = maxHealth;
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
	public int getPriority() {
		return 2;
	}

}
