package items.potion;

import entity.Player;
import items.base.Potion;

public class StrengthPotion extends Potion {
	private int attack;

	public StrengthPotion(String name, String description, int attack, int duration, boolean isPermanant) {
		super(name, description, duration, isPermanant);
		setAttack(attack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() + getAttack());
		if(isPermanant()) {
			setDuration(0);
		}
		player.getPotionList().add(this);
	}

	@Override
	public void onUnequip(Player player) {
		// TODO Auto-generated method stub
		if(!isPermanant()) {
			player.setAttack(player.getAttack() - getAttack());	
		}
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
