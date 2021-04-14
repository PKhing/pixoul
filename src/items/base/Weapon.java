package items.base;

public abstract class Weapon extends Item {
	private int attack;
	
	public Weapon(String name, String description, int attack) {
		super(name, description);
		setAtk(attack);
	}

	public int getAtk() {
		return attack;
	}

	public void setAtk(int attack) {
		this.attack = attack;
	}

}
