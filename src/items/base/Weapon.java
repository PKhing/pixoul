package items.base;

public abstract class Weapon extends Item {
	private int attack;

	public Weapon(String name, String description, int attack) {
		super(name, description);
		setAttack(attack);
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
}
