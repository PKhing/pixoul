package items.potion;

import entity.Player;
import items.base.Potion;

public class ShieldPotion extends Potion {
	private int shield;

	public ShieldPotion(String name, String description, int shield, int duration) {
		super(name, description, duration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getDefense() + getShield());
		player.getPotionList().add(this);
	}

	@Override
	public void onDeequip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getDefense() - getShield());
		player.getPotionList().remove(this);

	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}
}
