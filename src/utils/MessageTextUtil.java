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
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenSlained(Entity entity) {
		String displayText = "%s has been slained.".formatted(entity.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenUsedPotion(Potion potion) {
		String displayText = "Player has used %s".formatted(potion.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenEquipWeapon(Weapon weapon) {
		String displayText = "Player is using %s now".formatted(weapon.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenUnequipWeapon(Weapon weapon) {
		String displayText = "Player has not used weapon now.".formatted(weapon.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenSwitchWeapon(Weapon weapon) {
		String displayText = "Player has switched weapon to %s.".formatted(weapon.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenEquipArmor(Armor armor) {
		String displayText = "Player is wearing %s now.".formatted(armor.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenSwitchArmor(Armor armor) {
		String displayText = "Player has switched armor to %s.".formatted(armor.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenUnequipArmor(Armor armor) {
		String displayText = "Player has taken off armor";
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenPickUpItem(Item item) {
		String displayText = "Player has picked up %s".formatted(item.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
	
	public static void textWhenDropItem(Item item) {
		String displayText = "Player has dropped %s and it is gone forever".formatted(item.getName());
		GameScene.getMessagePane().addMessage(displayText);
	}
}
