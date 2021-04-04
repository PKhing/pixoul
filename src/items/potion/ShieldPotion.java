package items.potion;

import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class ShieldPotion extends Potion {
	private int shield;

	public ShieldPotion(String name, String description, int shield, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setShield(shield);
	}

	@Override
	public void onEquip(Player player) {
		player.setDefense(player.getDefense() + getShield());
		if(!isPermanant()) {
			setDuration(0);
		}
		player.getPotionList().add(this);
	}

	@Override
	public void onUnequip(Player player) {
		if(!isPermanant()) {
			player.setDefense(player.getDefense() - getShield());
		}
		player.getPotionList().remove(this);
	}

	@Override
	public int getSymbol() {
		return Sprites.SHIELD_POTION;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = Math.max(0, shield);
	}
}
