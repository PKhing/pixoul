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
	
	public void update() {
		if(getDuration() <= 0) return;
		setDuration(getDuration() - 1);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
