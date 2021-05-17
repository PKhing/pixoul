package effects.base;

import entity.base.Entity;
import utils.MessageTextUtil;

/**
 * The abstract class base of all effects
 */
public abstract class EntityEffect {

	/**
	 * Represent the name of effect
	 */
	private String name;

	/**
	 * Represent how many move that effect lasts long
	 */
	private int duration;

	/**
	 * Represent the property of the effect that it is permanent or not when usage
	 */
	private boolean isPermanant;

	/**
	 * Represent the efficient that effect have. For example Healing Potion which
	 * having value is 4, it means that Healing Potion will heal player health for 4
	 * units
	 */
	private int value;

	/**
	 * The constructor of the class
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanant The effect property that permanent or not
	 */
	public EntityEffect(String name, int value, int duration, boolean isPermanant) {
		this.name = name;
		this.value = value;
		this.duration = duration;
		this.isPermanant = isPermanant;
	}

	/**
	 * The handler method when effect is added to entity
	 * 
	 * @param entity the target that effect is added to
	 */
	public abstract void onAdd(Entity entity);

	/**
	 * The handler method when effect is removed to entity by condition
	 * 
	 * <ul>
	 * <li>Duration is less than or equal zero</li>
	 * <li>This effect have permanent property</li>
	 * </ul>
	 * 
	 * @param entity the target that effect is added to
	 */
	public abstract void onWearOff(Entity entity);

	/**
	 * Get the name of this effect
	 * 
	 * @return the name of effect
	 */
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

	/**
	 * Update the duration of the effect
	 * 
	 * @return true if this effect is still active (duration more than zero) otherwise false
	 */
	public boolean onUpdate() {
		setDuration(getDuration() - 1);
		if (getDuration() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Getter for {@link #name}
	 * 
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for {@link #name}
	 * 
	 * @param new value of {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for {@link #duration}
	 * 
	 * @return {@link #duration}
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Setter for {@link #duration}
	 * 
	 * @param duration new value of {@link #duration}r
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Getter for {@link #isPermanant}
	 * 
	 * @return {@link #isPermanant}
	 */
	public boolean isPermanant() {
		return isPermanant;
	}

	/**
	 * Setter for {@link #isPermanant}
	 * 
	 * @param isPermanant new value of {@link #isPermanant}
	 */
	public void setPermanant(boolean isPermanant) {
		this.isPermanant = isPermanant;
	}

	/**
	 * Getter for {@link #value}
	 * 
	 * @return {@link #value}
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter of {@link #value}
	 * 
	 * @param value new value of {@link #value}
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String effectName = MessageTextUtil.shortenWord(getEffectName());
		String usedName = MessageTextUtil.shortenWord(name);
		return "%s [%s]: %d".formatted(effectName, usedName, duration);
	}

	/**
	 * Utility method that called when effect needs to remove from entity
	 * 
	 * @param entity the target entity which removed effect from
	 */
	protected void removeEffect(Entity entity) {
		entity.getEffectList().remove(this);
	}

	/**
	 * Utility method that called when effect needs to add to entity
	 * 
	 * @param entity the target entity which added effect to
	 */
	protected void addEffect(Entity entity) {
		entity.getEffectList().add(this);
	}

}
