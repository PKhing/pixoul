package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import effects.base.IConsecutiveEffect;
import entity.base.Entity;

/**
 * The Poison class is {@link EntityEffect} that active for every move, it will
 * decrease the {@link Entity} health.
 */
public class Poison extends EntityEffect implements IConsecutiveEffect {

	/**
	 * The constructor of the class.
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public Poison(String name, int value, int duration, boolean isPermanent) {
		super(name, value, duration, isPermanent);
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
	 * Decrease entity health for each move if this effect is still active.
	 */
	@Override
	public void effect(Entity entity) {
		int newHealth = entity.getHealth() - getValue();
		entity.setHealth(newHealth);
		entity.setAttacked(true);
	}

	/**
	 * Remove effect from entity.
	 */
	@Override
	public void onWearOff(Entity entity) {
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.POISON;
	}

}
