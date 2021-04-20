package effects;

import entity.base.Entity;
import utils.MessageTextUtil;

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
	public abstract void onWearOff(Entity entity);
	public abstract String getEffectName();

	public boolean isDuplicate(Entity entity) {
		for (EntityEffect effect : entity.getEffectList()) {
			if ((effect.getName() == getName()) && (effect.getEffectName() == getEffectName())) {
				effect.setDuration(effect.getDuration() + getDuration());
				return true;
			}
		}
		return false;
	}

	public boolean onUpdate(Entity entity) {
		setDuration(getDuration() - 1);
		if (getDuration() <= 0) {
			return false;
		}
		return true;
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

	@Override
	public String toString() {
		String effectName = MessageTextUtil.shortenWord(getEffectName());
		String usedName = MessageTextUtil.shortenWord(name);
		return "%s [%s]: %d".formatted(effectName, usedName, duration);
	}

	protected void removeEffect(Entity entity) {
		entity.getEffectList().remove(this);
	}

	protected void addEffect(Entity entity) {
		entity.getEffectList().add(this);
	}

}
