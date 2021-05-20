package effects;

import controller.InterruptController;
import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

/**
 * The Immobilize class is {@link EntityEffect} that will make the player can
 * not move for a duration but player can still attack to monster
 *
 * @implNote This effect can not make it permanent
 */
public class Immobilize extends EntityEffect {

	/**
	 * The constructor of the class
	 * 
	 * @param name        The name of the effect
	 * @param value       The value of the effect
	 * @param duration    The duration of the effect
	 * @param isPermanent The effect property that permanent or not
	 */
	public Immobilize(String name, int value, int duration, boolean isPermanent) {
		// Force isPermanent parameter to false
		super(name, value, duration, false);
	}

	/**
	 * Set the {@link InterruptController#isImmobilize isImmobilize} to true and add
	 * effect to {@link Entity}
	 * 
	 * @see {@link InterruptController#isImmobilize isImmobilize}
	 */
	@Override
	public void onAdd(Entity entity) {
		if (isDuplicate(entity)) {
			return;
		}
		InterruptController.setImmobilize(true);
		addEffect(entity);
	}

	/**
	 * Set the {@link InterruptController#isImmobilize isImmobilize} back to false
	 * and remove effect from {@link Entity}
	 * 
	 * @see {@link InterruptController#isImmobilize isImmobilize}
	 */
	@Override
	public void onWearOff(Entity entity) {
		InterruptController.setImmobilize(false);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.IMMOBILIZED;
	}

}
