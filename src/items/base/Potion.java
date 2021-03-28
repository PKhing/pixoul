package items.base;

import entity.Player;

public abstract class Potion extends Item {
	private int duration;
	
	public Potion(String name, String description, int duration) {
		super(name, description);
		setDuration(duration);
	}
	
	public abstract void used(Player player);

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
