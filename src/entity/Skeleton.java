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
 * The Skeleton class represents skeleton monsters. They can attack the player.
 * They can be summoned by a {@link DarkMage dark mage}.
 */
public class Skeleton extends Monster implements Moveable, Attackable {

	/**
	 * The constructor for this class.
	 * 
	 * @param maxHealth         Max amount of health point of this monster
	 * @param attack            Attack value of this monster
	 * @param defense           Defense value of this monster
	 * @param posY              Position of this monster in the Y-axis
	 * @param posX              Position of this monster in the X-axis
	 * @param direction         {@link Direction} of this monster
	 * @param critRate          Critical rate of this monster
	 * @param critDamagePercent Critical damage percent of this monster
	 */
	public Skeleton(int maxHealth, int attack, int defense, int posY, int posX, int direction, double critRate,
			double critDamagePercent) {
		super("Skelaton", maxHealth, attack, defense, posY, posX, direction, critRate, critDamagePercent);
		setPositionOnMap(posY, posX);
	}

	@Override
	public int getSymbol() {
		return Sprites.SKELETON;
	}

	/**
	 * Updates this skeleton. If this skeleton already died, remove it from the
	 * game. If this skeleton can attack the player, it will attack the player. If
	 * this skeleton is near the player, it will move.
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
