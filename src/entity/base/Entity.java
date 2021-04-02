package entity.base;

public abstract class Entity {
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

	public Entity(int attack, int maxHealth, int defense, int posY, int posX, int direction,
			double critRate, double critPercent, int moveSpeed) {
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
	}

	public abstract void remove();

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

}
