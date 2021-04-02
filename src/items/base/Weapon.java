package items.base;

public abstract class Weapon extends Item {
	private int atk;
	private int atkSpeed;

	public Weapon(String name, String description, int atk, int atkSpeed) {
		super(name, description);
		setAtk(atk);
		setAtkSpeed(atkSpeed);
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getAtkSpeed() {
		return atkSpeed;
	}

	public void setAtkSpeed(int atkSpeed) {
		this.atkSpeed = atkSpeed;
	}
}
