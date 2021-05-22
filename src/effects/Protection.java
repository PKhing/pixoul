package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

/**
 * The Protection class is {@link EntityEffect} that increase the
 * {@link Entity#defense defense}.
 */
public class Protection extends EntityEffect {

	/**
	 * The constructor of the class.
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public Protection(String name, int value, int duration, boolean isPermanent) {
		super(name, value, duration, isPermanent);
	}

	/**
	 * If isPermanent is true then increase the {@link Entity#defense} and remove
	 * effect from {@link Entity} entity otherwise only increase.
	 * {@link Entity#defense}
	 */
	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}

		addEffect(entity);

		int newDefense = entity.getDefense() + getValue();
		entity.setDefense(newDefense);

		if (isPermanent()) {
			removeEffect(entity);
		}
	}

	/**
	 * Decrease the {@link Entity#defense defense} and remove effect from
	 * {@link Entity}.
	 */
	@Override
	public void onWearOff(Entity entity) {
		int newDefense = entity.getDefense() - getValue();
		entity.setDefense(newDefense);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.PROTECTION;
	}

}
