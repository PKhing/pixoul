package effects;

import entity.base.Entity;

public class InstantHealth extends EntityEffect {

	public InstantHealth(Entity entity, String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		entity.getEffectList().add(this);
		
		if (isPermanant()) {
			int newMaxHealth = entity.getMaxHealth() + getValue();
			entity.setMaxHealth(newMaxHealth);
		}

		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
		onWearOff(entity);
	}

	@Override
	public void onWearOff(Entity entity) {
		entity.getEffectList().remove(this);
	}

}
