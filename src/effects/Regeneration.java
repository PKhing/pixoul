package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import effects.base.IConsecutiveEffect;
import entity.base.Entity;

/**
 * The Regeneration class is {@link EntityEffect} that active for every move, it
 * will increase the {@link Entity} health.
 * 
 * <p>Note that this effect can not make it permanent</p>
 */
public class Regeneration extends EntityEffect implements IConsecutiveEffect {

	/**
	 * The constructor of the class.
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public Regeneration(String name, int value, int duration, boolean isPermanent) {
		super(name, value, duration, false);
	}

	/**
	 * Add effect to entity.
	 */
	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}
		addEffect(entity);
	}

	/**
	 * Increase entity health for each move if this effect is still active.
	 */
	@Override
	public void effect(Entity entity) {
		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
	}

	/**
	 * Remove effect from entity.
	 */
	@Override
	public void onWearOff(Entity entity) {
		entity.getEffectList().remove(this);
	}

	@Override
	public String getEffectName() {
		return EffectName.REGENERATION;
	}
}
