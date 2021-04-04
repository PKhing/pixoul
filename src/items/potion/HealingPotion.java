package items.potion;

import entity.Player;
import items.base.IConsecutiveEffect;
import items.base.Potion;

public class HealingPotion extends Potion implements IConsecutiveEffect {
	private int value;

	public HealingPotion(String name, String description, int value, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setValue(value);
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		if(isPermanant()) {
			setDuration(0);
		}
		player.getPotionList().add(this);
	}

	@Override
	public void effect(Player player) {
		int newHealth = 0;
		if(!isPermanant()) {
			newHealth = Math.min(player.getMaxHealth(), player.getHealth() + getValue());
		} else {
			player.setMaxHealth(player.getMaxHealth() + getValue());
			newHealth = Math.min(player.getMaxHealth(), player.getHealth() + getValue());
		}
		player.setHealth(newHealth);
	}

	@Override
	public void onUnequip(Player player) {
		return;
	}

	@Override
	public int getSymbol() {
		return 2;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
