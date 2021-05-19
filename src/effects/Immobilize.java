package effects;

import controller.InterruptController;
import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

public class Immobilize extends EntityEffect {

	public Immobilize(String name, int value, int duration, boolean isPermanent) {
		super(name, value, duration, isPermanent);
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
