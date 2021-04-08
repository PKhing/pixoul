package items.potion;

import effects.AttackBoost;
import effects.DefenseBoost;
import entity.Player;
import items.base.Potion;
import logic.Sprites;

public class StrengthPotion extends Potion {
	private int attack;

	public StrengthPotion(String name, String description, int attack, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setAttack(attack);
	}

	@Override
	public void onEquip(Player player) {
		new AttackBoost(player, getName(), getAttack(), getDuration(), isPermanant()).onAdd(player);
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
