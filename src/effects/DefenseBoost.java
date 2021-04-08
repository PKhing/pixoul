package effects;

import entity.base.Entity;

public class DefenseBoost extends EntityEffect {

	public DefenseBoost(Entity entity, String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
	}

	@Override
	public void onAdd(Entity entity) {
		entity.getEffectList().add(this);
		
		int newDefense = entity.getDefense() + getValue();
		entity.setDefense(newDefense);

		if (isPermanant()) {
			remove(entity);
		}
	}

	@Override
	public void onWearOff(Entity entity) {
		int newDefense = entity.getDefense() - getValue();
		entity.setDefense(newDefense);
		remove(entity);
	}

}
