package items.base;

public abstract class Potion extends Item {
	private int duration;
	private boolean isPermanant;
	
	public Potion(String name, String description, int duration, boolean isPermanant) {
		super(name, description);
		setDuration(duration);
		setPermanant(isPermanant);
	}

	public boolean update() {
		setDuration(getDuration() - 1);
		if (getDuration() <= 0)
			return false;
		return true;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isPermanant() {
		return isPermanant;
	}

	public void setPermanant(boolean isPermanant) {
		this.isPermanant = isPermanant;
	}

}
