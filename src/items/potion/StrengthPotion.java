package items.potion;

import entity.Player;
import items.base.Potion;

public class StrengthPotion extends Potion {
	private int atk;

	public StrengthPotion(String name, String description, int duration) {
		super(name, description, duration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() + getAtk());
		player.getPotionList().add(this);
	}

	@Override
	public void onDeequip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() - getAtk());
		player.getPotionList().remove(this);
	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		atk = Math.max(0, atk);
		this.atk = atk;
	}
}
