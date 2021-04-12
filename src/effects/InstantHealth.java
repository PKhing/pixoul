package effects;

import entity.base.Entity;

public class InstantHealth extends EntityEffect {

	public InstantHealth(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		addEffect(entity);

		if (isPermanant()) {
			int newMaxHealth = entity.getMaxHealth() + getValue();
			entity.setMaxHealth(newMaxHealth);
		}

		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		return EffectName.INSTANT_HEALTH;
	}

}
