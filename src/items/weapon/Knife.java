package items.weapon;

import entity.Player;
import items.base.Weapon;
import logic.Sprites;

public class Knife extends Weapon {

	public Knife(String name, String description, int attack, int spriteIndex) {
		super(name, description, attack, spriteIndex);
	}

	public Knife clone() {
		return new Knife(getName(), getDescription(), getAttack(), getSpriteIndex());
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
		return Sprites.KNIFE;
	}

}
