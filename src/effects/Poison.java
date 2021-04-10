package effects;

import entity.base.Entity;

public class Poison extends EntityEffect implements IConsecutiveEffect{

	public Poison(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdd(Entity entity) {
		entity.getEffectList().add(this);
	}
	
	@Override
	public void effect(Entity entity) {
		int newHealth = entity.getHealth() - getValue();
		entity.setHealth(newHealth);
	}
	
	@Override
	public void onWearOff(Entity entity) {
		remove(entity);
	}

	@Override
	public String getEffectName() {
		// TODO Auto-generated method stub
		return null;
	}

}
