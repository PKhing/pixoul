package items.base;

import entity.Player;

public abstract class Potion extends Item {
	private int duration;

	public Potion(String name, String description, int duration) {
		super(name, description);
		setDuration(duration);
	}

	public abstract void onUsed(Player player);

	public abstract void onWearOff(Player player);

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

}
