package items.potion;

import effects.EntityEffect;
import effects.Vision;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class VisionPotion extends Potion {
	public VisionPotion(String name, String description, int vision, int duration, boolean isPermanant) {
		super(name, description, vision, duration, isPermanant);
	}
	
	public VisionPotion clone() {
		return new VisionPotion(getName(), getDescription(), getPotionValue(), getDuration(), isPermanant());
	}

	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Vision(getName(), getPotionValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.VISION_POTION;
	}
}
