package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

/**
 * The Strength class is {@link EntityEffect} that increase the
 * {@link Entity#attack attack}
 */
public class Strength extends EntityEffect {

	/**
	 * The constructor of the class
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public Strength(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	/**
	 * If isPermanent is true then increase the {@link Entity#attack} and remove
	 * effect from {@link Entity} entity otherwise only increase
	 * {@link Entity#attack}
	 */
	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}

		addEffect(entity);

		int newAttack = entity.getAttack() + getValue();
		entity.setAttack(newAttack);

		if (isPermanent()) {
			removeEffect(entity);
		}
	}

	/**
	 * Decrease the {@link Entity#attack attack} and remove effect from
	 * {@link Entity}
	 */
	@Override
	public void onWearOff(Entity entity) {
		int newAttack = entity.getAttack() - getValue();
		entity.setAttack(newAttack);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.STRENGTH;
	}

}
