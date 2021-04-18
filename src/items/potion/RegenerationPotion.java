package items.potion;

import effects.EntityEffect;
import effects.Regeneration;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class RegenerationPotion extends Potion {
	public RegenerationPotion(String name, String description, int healValue, int duration, boolean isPermanant) {
		super(name, description, healValue, duration, false);
	}

	public RegenerationPotion clone() {
		return new RegenerationPotion(getName(), getDescription(), getPotionValue(), getDuration(), isPermanant());
	}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return Sprites.HEALING_POTION;
	}

	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Regeneration(getName(), getPotionValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

}
