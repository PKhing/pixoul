package items.weapon;

import entity.Player;
import items.base.Weapon;
import logic.Sprites;

public class Spear extends Weapon {

	public Spear(String name, String description, int attack, int spriteIndex) {
		super(name, description, attack, spriteIndex);
	}

	public Spear clone() {
		return new Spear(getName(), getDescription(), getAttack(), getSpriteIndex());
	}
	
	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() + getAttack());
		player.setEquippedWeapon(this);
	}

	@Override
	public void onUnequip(Player player) {
		// TODO Auto-generated method stub
		player.setAttack(player.getAttack() - getAttack());
		player.setEquippedWeapon(null);
	}

	@Override
	public int getSymbol() {
		// TODO Auto-generated method stub
		return Sprites.SPEAR;
	}

}
