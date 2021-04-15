package items.potion;

import effects.EntityEffect;
import effects.InstantHealth;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class InstantHealPotion extends Potion {
	private int value;

	public InstantHealPotion(String name, String description, int value, int duration, boolean isPermanant) {
		super(name, description, 0, isPermanant);
		setValue(value);
	}
	
	public InstantHealPotion clone() {
		return new InstantHealPotion(getName(), getDescription(), getValue(), getDuration(), isPermanant());
	}

	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new InstantHealth(getName(), getValue(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.HEALING_POTION;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
