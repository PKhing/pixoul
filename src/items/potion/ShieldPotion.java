package items.potion;

import entity.Player;
import items.base.Potion;

public class ShieldPotion extends Potion {
	private int shield;

	public ShieldPotion(String name, String description, int shield, int duration) {
		super(name, description, duration);
		setShield(shield);
	}

	@Override
	public void onEquip(Player player) {
		player.setDefense(player.getDefense() + getShield());
		player.getPotionList().add(this);
	}

	@Override
	public void onUnequip(Player player) {
		player.setDefense(player.getDefense() - getShield());
		player.getPotionList().remove(this);

	}

	@Override
	public int getSymbol() {
		return 1;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = Math.max(0, shield);
	}
}
