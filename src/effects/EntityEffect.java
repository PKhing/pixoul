package effects;

import entity.Player;
import entity.base.Entity;

public abstract class EntityEffect {
	private String name;

	private int duration;

	private boolean isPermanant;

	private int value;

	public EntityEffect(String name, int value, int duration, boolean isPermanant) {
		super();
		this.name = name;
		this.value = value;
		this.duration = duration;
		this.isPermanant = isPermanant;
	}

	public abstract void onAdd(Entity entity);

	public boolean onUpdate(Entity entity) {
		if (getDuration() <= 0) {
			return false;
		}
		setDuration(getDuration() - 1);
		return true;
	}

	public void onWearOff(Entity entity) {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	protected void remove(Entity entity) {
		entity.getEffectList().remove(this);
	}

}
