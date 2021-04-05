package items.base;

public abstract class Weapon extends Item {
	private int attack;
	private int attackSpeed;

	public Weapon(String name, String description, int attack, int attackSpeed) {
		super(name, description);
		setAtk(attack);
		setAtkSpeed(attackSpeed);
	}

	public int getAtk() {
		return attack;
	}

	public void setAtk(int attack) {
		this.attack = attack;
	}

	public int getAtkSpeed() {
		return attackSpeed;
	}

	public void setAtkSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
}
