package utils;

import entity.base.Entity;
import items.base.Armor;
import items.base.Item;
import items.base.Potion;
import items.base.Weapon;
import scene.GameScene;

public class MessageTextUtil {
	public static void textWhenAttack(Entity from, Entity to, int attackValue) {
		String displayText = "%s has attacked %s for %d damage".formatted(from.getName(), to.getName(), attackValue);
		writeMessage(displayText);
	}
	
	public static void textWhenSlained(Entity entity) {
		String displayText = "%s has been slained.".formatted(entity.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenUsedPotion(Potion potion) {
		String displayText = "Player has used %s".formatted(potion.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenEquipWeapon(Weapon weapon) {
		String displayText = "Player is using %s now".formatted(weapon.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenUnequipWeapon(Weapon weapon) {
		String displayText = "Player has not used weapon now.".formatted(weapon.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenSwitchWeapon(Weapon weapon) {
		String displayText = "Player has switched weapon to %s.".formatted(weapon.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenEquipArmor(Armor armor) {
		String displayText = "Player is wearing %s now.".formatted(armor.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenSwitchArmor(Armor armor) {
		String displayText = "Player has switched armor to %s.".formatted(armor.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenUnequipArmor(Armor armor) {
		String displayText = "Player has taken off armor";
		writeMessage(displayText);
	}
	
	public static void textWhenPickUpItem(Item item) {
		String displayText = "Player has picked up %s".formatted(item.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenDropItem(Item item) {
		String displayText = "Player has dropped %s and it is gone forever".formatted(item.getName());
		writeMessage(displayText);
	}
	
	public static void textWhenAscending(int level) {
		String displayText = null;
		if(level < 1) {
			displayText = "You can not go up anymore.";
		} else {
			displayText = "You have gone up to floor level %d".formatted(level);
		}
		writeMessage(displayText);
	}
	
	public static void textWhenDescending(int level) {
		String displayText = null;
		displayText = "You have gone down to floor level %d".formatted(level);
		writeMessage(displayText);
	}
	
	private static void writeMessage(String text) {
		GameScene.getMessagePane().addMessage(text);
	}
}
