package entity.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.GameController;
import effects.EntityEffect;
import utils.MessageTextUtil;

public abstract class Entity {
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
		this.setName(name);
		this.health = maxHealth;
		this.attack = attack;
		this.defense = defense;
		this.posX = posX;
		this.posY = posY;
		this.direction = direction;
		this.critRate = critRate;
		this.critPercent = critPercent;
		this.moveSpeed = moveSpeed;
		this.maxHealth = maxHealth;
		this.effectList = new CopyOnWriteArrayList<>();
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
		this.defense = defense;
	}

	public double getCritRate() {
		return critRate;
	}

	public void setCritRate(double critRate) {
		this.critRate = critRate;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public double getCritPercent() {
		return critPercent;
	}

	public void setCritPercent(double critPercent) {
		this.critPercent = critPercent;
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

}
