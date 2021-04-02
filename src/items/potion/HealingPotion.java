package items.potion;

import entity.Player;
import items.base.IConsecutiveEffect;
import items.base.Potion;

public class HealingPotion extends Potion implements IConsecutiveEffect {
	private int value;

	public HealingPotion(String name, String description, int value, int duration) {
		super(name, description, duration);
		setValue(value);
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.getPotionList().add(this);
	}

	@Override
	public void effect(Player player) {
		int newHealth = Math.min(player.getMaxHealth(), player.getHealth() + getValue());
		player.setHealth(newHealth);
	}

	@Override
	public void onDeequip(Player player) {
		return;
	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
