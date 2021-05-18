package effects;

import effects.base.EffectName;
import effects.base.EntityEffect;
import entity.base.Entity;

public class InstantHealth extends EntityEffect {

	public InstantHealth(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		addEffect(entity);

		if (isPermanent()) {
			int newMaxHealth = entity.getMaxHealth() + getValue();
			entity.setMaxHealth(newMaxHealth);
		}

		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
		onWearOff(entity);
	}
	
	@Override
	public void onWearOff(Entity entity) {
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.INSTANT_HEALTH;
	}

	

}
