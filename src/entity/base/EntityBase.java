package entity.base;

import java.util.ArrayList;

public abstract class EntityBase {
	private int health;
	
	private int attack;
	
	private int defense;
	
	private double critRate;
	
	private double critPercent;
	
	private int moveSpeed;

	public EntityBase() {
		
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
		this.attack = Math.max(0, attack);
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = Math.max(0, defense);
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
		this.moveSpeed = Math.max(1, moveSpeed);
	}

	public double getCritPercent() {
		return critPercent;
	}

	public void setCritPercent(double critPercent) {
		this.critPercent = Math.max(0, critPercent);
	}
}
