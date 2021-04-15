package items.potion;

import effects.EntityEffect;
import effects.Protection;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class ShieldPotion extends Potion {
	private int shield;

	public ShieldPotion(String name, String description, int shield, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setShield(shield);
	}
	
	public ShieldPotion clone() {
		return new ShieldPotion(getName(), getDescription(), getShield(), getDuration(), isPermanant());
	}
	
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Protection(getName(), getShield(), getDuration(), isPermanant());
		effect.onAdd(player);
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
