package effects;

import controller.InterruptController;
import entity.base.Entity;

public class Immobilize extends EntityEffect {

	public Immobilize(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdd(Entity entity) {
		if(isDuplicate(entity)) {
			return;
		}
		InterruptController.setImmobilize(true);
		addEffect(entity);
	}
	
	@Override
	public void onWearOff(Entity entity) {
		InterruptController.setImmobilize(false);
		removeEffect(entity);
	}

	@Override
	public String getEffectName() {
		// TODO Auto-generated method stub
		return EffectName.IMMOBILIZED;
	}
	
}
