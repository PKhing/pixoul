package items.potion;

import effects.InstantHealth;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class InstantHealPotion extends Potion {
	private int value;

	public InstantHealPotion(String name, String description, int value, boolean isPermanant) {
		super(name, description, 0, isPermanant);
		setValue(value);
	}

	@Override
	public void onEquip(Player player) {
		new InstantHealth(getName(), getValue(), getDuration(), isPermanant()).onAdd(player);
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
