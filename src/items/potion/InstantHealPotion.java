package items.potion;

import effects.EntityEffect;
import effects.InstantHealth;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class InstantHealPotion extends Potion {
	public InstantHealPotion(String name, String description, int value, int duration, boolean isPermanant, int spriteIndex) {
		super(name, description, value, 0, isPermanant, spriteIndex);
	}
	
	public InstantHealPotion clone() {
		return new InstantHealPotion(getName(), getDescription(), getPotionValue(), getDuration(), isPermanant(), getSpriteIndex());
	}

	@Override
	public void onEquip(Player player) {
		if(getName().contains("Max Healing Potion")) {
			setPotionValue(player.getMaxHealth() - player.getHealth());
		}
		EntityEffect effect = new InstantHealth(getName(), getPotionValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.HEALING_POTION;
	}

}
