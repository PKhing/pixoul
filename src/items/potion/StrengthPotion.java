package items.potion;

import entity.Player;
import items.base.Potion;

public class StrengthPotion extends Potion {
	private int attack;

	public StrengthPotion(String name, String description, int attack, int duration) {
		super(name, description, duration);
		setAttack(attack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() + getAttack());
		player.getPotionList().add(this);
	}

	@Override
	public void onUnequip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() - getAttack());
		player.getPotionList().remove(this);
	}

	@Override
	public int getSymbol() {
		return 0;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = Math.max(0, attack);
	}
}
