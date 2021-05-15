package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import entity.base.Moveable;
import logic.Direction;
import logic.GameLogic;
import logic.Sprites;
import utils.MessageTextUtil;

/**
 * The HauntedMaid class represents haunted maid monsters. They can attack the
 * player.
 */
public class HauntedMaid extends Monster implements Moveable, Attackable {

	/**
	 * The constructor for this class.
	 * 
	 * @param maxHealth         Max amount of health point of this entity
	 * @param attack            Attack value of this entity
	 * @param defense           Defense value of this entity
	 * @param posY              Position of this entity in the Y-axis
	 * @param posX              Position of this entity in the X-axis
	 * @param direction         {@link Direction} of this monster
	 * @param critRate          Critical rate of this entity
	 * @param critDamagePercent Critical damage percent of this entity
	 */
	public HauntedMaid(int maxHealth, int attack, int defense, int posY, int posX, int direction, double critRate,
			double critDamagePercent) {
		super("Haunted Maid", maxHealth, attack, defense, posY, posX, direction, critRate, critDamagePercent);
		setPositionOnMap(posY, posX);
	}

	@Override
	public int getSymbol() {
		return Sprites.HAUNTED_MAID;
	}

	/**
	 * Updates this haunted maid. If this haunted maid already dies, remove it from
	 * the game. If this haunted maid is can attack the player, it will attack the
	 * player. If this haunted maid is near the player, it will move.
	 */
	@Override
	public void update() {
		if (getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (isAttackable(gamePlayer)) {
			attack(gamePlayer);
		} else {
			int nextDirection = this.getNextDirection();
			if (nextDirection != -1) {
				this.move(nextDirection);
			}
		}
	}

	@Override
	public boolean attack(Entity target) {
		int atkValue = GameLogic.calculateAttackValue(this, target);
		MessageTextUtil.textWhenAttack(this, target, atkValue);
		target.setHealth(target.getHealth() - atkValue);
		target.setAttacked(true);
		return true;
	}

}
