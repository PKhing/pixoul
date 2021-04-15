package items.potion;

import effects.EntityEffect;
import effects.Strength;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class StrengthPotion extends Potion {
	private int attack;

	public StrengthPotion(String name, String description, int attack, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setAttack(attack);
	}

	public StrengthPotion clone() {
		return new StrengthPotion(getName(), getDescription(), getAttack(), getDuration(), isPermanant());
	}
	
	@Override
	public void onEquip(Player player) {
		EntityEffect effect = new Strength(getName(), getAttack(), getDuration(), isPermanant());
		effect.onAdd(player);
	}

	@Override
	public int getSymbol() {
		return Sprites.STRENGTH_POTION;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = Math.max(0, attack);
	}
}
