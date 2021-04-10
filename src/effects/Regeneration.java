package effects;

import entity.base.Entity;

public class Regeneration extends EntityEffect implements IConsecutiveEffect {

	public Regeneration(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, false);
	}

	@Override
	public void onAdd(Entity entity) {
		entity.getEffectList().add(this);
	}

	@Override
	public void effect(Entity entity) {
		int newHealth = Math.min(entity.getMaxHealth(), entity.getHealth() + getValue());
		entity.setHealth(newHealth);
	}
	
	@Override
	public void onWearOff(Entity entity) {
		entity.getEffectList().remove(this);
	}
}
