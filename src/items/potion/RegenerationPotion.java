package items.potion;

import effects.Regeneration;
import effects.base.EntityEffect;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class RegenerationPotion extends Potion {
	public RegenerationPotion(String name, String description, int healValue, int duration, boolean isPermanant, int spriteIndex) {
		super(name, description, healValue, duration, false, spriteIndex);
	}

	public RegenerationPotion clone() {
		return new RegenerationPotion(getName(), getDescription(), getEffectValue(), getDuration(), isPermanant(), getSpriteIndex());
	}
	
	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return Sprites.REGENERATION_POTION;
	}

	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Regeneration(getName(), getEffectValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

}
