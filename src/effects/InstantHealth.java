package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

/**
 * The InstantHealth class is {@link EntityEffect} that will instantly increase
 * the {@link Entity} health.
 *
 */
public class InstantHealth extends EntityEffect {

	/**
	 * The constructor of the class.
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public InstantHealth(String name, int value, int duration, boolean isPermanent) {
		super(name, value, duration, isPermanent);
	}

	/**
	 * If isPermanent is true then increase both {@link Entity#health currentHealth}
	 * and {@link Entity#maxHealth maxHealth} of entity otherwise increase only
	 * {@link Entity#health currentHealth}.
	 */
	@Override
	public void onAdd(Entity entity) {
		if (isPermanent()) {
			int newMaxHealth = entity.getMaxHealth() + getValue();
			entity.setMaxHealth(newMaxHealth);
		}

		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
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
		return EffectName.INSTANT_HEALTH;
	}

}
