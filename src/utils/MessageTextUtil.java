package utils;

import components.MessagePane;
import effects.Immobilize;
import entity.DarkMage;
import entity.base.Entity;
import items.base.Armor;
import items.base.Item;
import items.base.Potion;
import items.base.Weapon;
import scene.GameScene;

/**
 * The utility class that used for generating text which display in
 * {@link MessagePane}
 */
public class MessageTextUtil {

	/**
	 * The maximum character of word
	 */
	private static int wordMaxSize = 30;

	/**
	 * Generate the text when {@link Entity} has attacked to another {@link Entity}
	 * and add to {@link MessagePane}
	 * 
	 * @param from        the attacker {@link Entity}
	 * @param to          the attacked {@link Entity}
	 * @param attackValue the attack value which will appear in text
	 */
	public static void textWhenAttack(Entity from, Entity to, int attackValue) {
		String displayText = "%s has attacked %s for %d damage".formatted(shortenWord(from.getName()),
				shortenWord(to.getName()), attackValue);
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Entity} has been slain and add to
	 * {@link MessagePane}
	 * 
	 * @param entity the {@link Entity} that has been slain
	 */
	public static void textWhenSlained(Entity entity) {
		String displayText = "%s has been slain.".formatted(shortenWord(entity.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has used the {@link Potion} and add to
	 * {@link MessagePane}
	 * 
	 * @param potion the {@link Potion} that {@link Player} has used
	 */
	public static void textWhenUsedPotion(Potion potion) {
		String displayText = "Player has used %s".formatted(shortenWord(potion.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has equipped the {@link Weapon} and add
	 * to {@link MessagePane}
	 * 
	 * @param weapon the {@link Weapon} that {@link Player} has equipped
	 */
	public static void textWhenEquipWeapon(Weapon weapon) {
		String displayText = "Player is using %s now".formatted(shortenWord(weapon.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has unequipped the {@link Weapon} and
	 * add to {@link MessagePane}
	 * 
	 * @param weapon the {@link Weapon} that {@link Player} has unequipped
	 */
	public static void textWhenUnequipWeapon(Weapon weapon) {
		String displayText = "Player has not used weapon now.".formatted(shortenWord(weapon.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} can not unequip {@link Weapon} and add
	 * to {@link MessagePane}
	 * 
	 * @param armor the {@link Weapon} that {@link Player} tries to unequip
	 */
	public static void textWhenCannotUnequipWeapon(Weapon weapon) {
		String displayText = "Player cannot unequip %s because of full inventory".formatted(weapon.getName());
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has switched the {@link Weapon} and add
	 * to {@link MessagePane}
	 * 
	 * @param weapon the {@link Weapon} that {@link Player} has switched to
	 */
	public static void textWhenSwitchWeapon(Weapon weapon) {
		String displayText = "Player has switched weapon to %s.".formatted(shortenWord(weapon.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has equipped the {@link Armor} and add
	 * to {@link MessagePane}
	 * 
	 * @param armor the {@link Armor} that {@link Player} has equipped
	 */
	public static void textWhenEquipArmor(Armor armor) {
		String displayText = "Player is wearing %s now.".formatted(shortenWord(armor.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has switched the {@link Armor} and add
	 * to {@link MessagePane}
	 * 
	 * @param armor the {@link Armor} that {@link Player} has switched to
	 */
	public static void textWhenSwitchArmor(Armor armor) {
		String displayText = "Player has switched armor to %s.".formatted(shortenWord(armor.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has unequipped {@link Armor} and add to
	 * {@link MessagePane}
	 * 
	 * @param armor the {@link Armor} that {@link Player} has unequipped
	 */
	public static void textWhenUnequipArmor(Armor armor) {
		String displayText = "Player has unequipped %s".formatted(armor.getName());
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} can not unequip {@link Armor} and add
	 * to {@link MessagePane}
	 * 
	 * @param armor the {@link Armor} that {@link Player} tries to unequip
	 */
	public static void textWhenCannotUnequipArmor(Armor armor) {
		String displayText = "Player cannot unequip %s because of full inventory".formatted(armor.getName());
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has picked up {@link Item} and add to
	 * {@link MessagePane}
	 * 
	 * @param item the {@link Item} that {@link Player} has picked up
	 */
	public static void textWhenPickUpItem(Item item) {
		String displayText = "Player has picked up %s".formatted(shortenWord(item.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} can not picked up {@link Item} from
	 * {@link Cell} and add to {@link MessagePane}
	 * 
	 * @param item the {@link Item} that {@link Player} tries to picked up
	 */
	public static void textWhenCannotPickedItem(Item item) {
		String displayText = "Player can not picked up %s because of full inventory"
				.formatted(shortenWord(item.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has dropped {@link Item} and add to
	 * {@link MessagePane}
	 * 
	 * @param item the {@link Item} that {@link Player} has dropped
	 */
	public static void textWhenDropItem(Item item) {
		String displayText = "Player has dropped %s and it is gone forever".formatted(shortenWord(item.getName()));
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has gone to upper level and add to
	 * {@link MessagePane}
	 * 
	 * @param level the level which {@link Player} has gone to
	 */
	public static void textWhenAscending(int level) {
		String displayText = null;
		if (level < 1) {
			displayText = "You can not go up anymore.";
		} else {
			displayText = "You have gone up to floor level %d".formatted(level);
		}
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has gone to lower level and add to
	 * {@link MessagePane}
	 * 
	 * @param level the level which {@link Player} has gone to
	 */
	public static void textWhenDescending(int level) {
		String displayText = "You have gone down to floor level %d".formatted(level);
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has {@link Immobilize} status and add
	 * to {@link MessagePane}
	 */
	public static void textWhenImmobilized() {
		String displayText = "You can not move because you are immobilize now (but still can attack).";
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} has stayed still in that move and add
	 * to {@link MessagePane}
	 */
	public static void textWhenStayStill() {
		String displayText = "Player does nothing.";
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link Player} tries to attack {@link Monster} but it
	 * is out of range and add to {@link MessagePane}
	 */
	public static void textWhenPlayerCannotAttack() {
		String displayText = "You can't attack this monster.";
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link DarkMage} is using warp to entity and add to
	 * {@link MessagePane}
	 * 
	 * @param entity the target {@link Entity}
	 */
	public static void textWhenDarkMageWarp(Entity entity) {
		String displayText = "%s has been teleported to somewhere in this level".formatted(entity.getName());
		writeMessage(displayText);
	}

	/**
	 * Generate the text when {@link DarkMage} is using poison to entity and add to
	 * {@link MessagePane}
	 * 
	 * @param entity the target {@link Entity}
	 */
	public static void textWhenDarkMageUsePoison(Entity entity) {
		String displayText = "%s has been poisoned by Dark mage".formatted(entity.getName());
		writeMessage(displayText);
	}

	/**
	 * Utility method that check the word length is longer than {@link #wordMaxSize}
	 * or not if true then slice only first {@link #wordMaxSize} and append "..."
	 * 
	 * @param text the word that will make it shorten
	 * @return the result after shorten
	 */
	public static String shortenWord(String text) {
		int sz = Math.min(wordMaxSize, text.length());

		String newText = text.substring(0, sz);
		if (sz != text.length()) {
			newText += "...";
		}

		return newText;
	}

	/**
	 * Append text to {@link MessagePane}
	 * 
	 * @param text the text that will append to {@link MessagePane}
	 */
	private static void writeMessage(String text) {
		text = "- " + text;
		GameScene.getMessagePane().addMessage(text);
	}
}
