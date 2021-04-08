package items.potion;

import effects.Regeneration;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class RegenerationPotion extends Potion {

	private int healValue;
	
	public RegenerationPotion(String name, String description, int healValue, int duration) {
		super(name, description, duration, false);
		setHealValue(healValue);
	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return Sprites.HEALING_POTION;
	}

	@Override
	public void onEquip(Player player) {
		new Regeneration(player, getName(), getHealValue(), getDuration(), isPermanant()).onAdd(player);;
	}

	public int getHealValue() {
		return healValue;
	}

	public void setHealValue(int healValue) {
		this.healValue = healValue;
	}

}
