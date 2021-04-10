package effects;

import entity.Player;
import entity.base.Entity;
import utils.GameConfig;

public class Blindness extends EntityEffect {

	public Blindness(String name, int value, int duration, boolean isPermanant) {
		super(name, value, duration, isPermanant);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAdd(Entity entity) {
		if(entity instanceof Player) {
			entity.getEffectList().add(this);
			int newLineOfSight = ((Player) entity).getLineOfSight() - getValue();
			((Player) entity).setLineOfSight(newLineOfSight);
		}
	}
	
	@Override
	public void onWearOff(Entity entity) {
		if(entity instanceof Player) {
			entity.getEffectList().add(this);
			int newLineOfSight = ((Player) entity).getLineOfSight() + getValue();
			((Player) entity).setLineOfSight(newLineOfSight);
		}
		remove(entity);
	}
	
	@Override
	public String getEffectName() {
		return EffectName.BLINDNESS;
	}

}
