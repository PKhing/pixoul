package utils;

import entity.base.Entity;
import items.base.Armor;
import items.base.Item;
import items.base.Potion;
import items.base.Weapon;
import scene.GameScene;

public class MessageTextUtil {
	private static int wordMaxSize = 20;

	public static void textWhenAttack(Entity from, Entity to, int attackValue) {
		String displayText = "%s has attacked %s for %d damage".formatted(shortenWord(from.getName()),
				shortenWord(to.getName()), attackValue);
		writeMessage(displayText);
	}

	public static void textWhenSlained(Entity entity) {
		String displayText = "%s has been slain.".formatted(shortenWord(entity.getName()));
		writeMessage(displayText);
	}

	public static void textWhenUsedPotion(Potion potion) {
		String displayText = "Player has used %s".formatted(shortenWord(potion.getName()));
		writeMessage(displayText);
	}

	public static void textWhenEquipWeapon(Weapon weapon) {
		String displayText = "Player is using %s now".formatted(shortenWord(weapon.getName()));
		writeMessage(displayText);
	}

	public static void textWhenUnequipWeapon(Weapon weapon) {
		String displayText = "Player has not used weapon now.".formatted(shortenWord(weapon.getName()));
		writeMessage(displayText);
	}

	public static void textWhenSwitchWeapon(Weapon weapon) {
		String displayText = "Player has switched weapon to %s.".formatted(shortenWord(weapon.getName()));
		writeMessage(displayText);
	}

	public static void textWhenEquipArmor(Armor armor) {
		String displayText = "Player is wearing %s now.".formatted(shortenWord(armor.getName()));
		writeMessage(displayText);
	}

	public static void textWhenSwitchArmor(Armor armor) {
		String displayText = "Player has switched armor to %s.".formatted(shortenWord(armor.getName()));
		writeMessage(displayText);
	}

	public static void textWhenUnequipArmor(Armor armor) {
		String displayText = "Player has taken off armor";
		writeMessage(displayText);
	}

	public static void textWhenPickUpItem(Item item) {
		String displayText = "Player has picked up %s".formatted(shortenWord(item.getName()));
		writeMessage(displayText);
	}

	public static void textWhenDropItem(Item item) {
		String displayText = "Player has dropped %s and it is gone forever".formatted(shortenWord(item.getName()));
		writeMessage(displayText);
	}

	public static void textWhenAscending(int level) {
		String displayText = null;
		if (level < 1) {
			displayText = "You can not go up anymore.";
		} else {
			displayText = "You have gone up to floor level %d".formatted(level);
		}
		writeMessage(displayText);
	}

	public static void textWhenDescending(int level) {
		String displayText = "You have gone down to floor level %d".formatted(level);
		writeMessage(displayText);
	}

	public static void textWhenImmobilized() {
		String displayText = "You can not move because you are immobilize now (but still can attack).";
		writeMessage(displayText);
	}
	
	public static void textWhenStayStill(Entity entity) {
		String displayText = "%s does nothing.".formatted(entity.getName());
		writeMessage(displayText);
	}
	
	public static void makeNewMessage(String string) {
		writeMessage(string);
	}

	public static String shortenWord(String text) {
		int sz = Math.min(wordMaxSize, text.length());

		String newText = text.substring(0, sz);
		if (sz != text.length()) {
			newText += "...";
		}

		return newText;
	}

	private static void writeMessage(String text) {
		text = "- " + text;
		GameScene.getMessagePane().addMessage(text);
	}
}
