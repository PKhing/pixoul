package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import logic.Direction;
import logic.Sprites;
import utils.MessageTextUtil;
import utils.RandomUtil;

/**
 * The Soul class represents soul monsters. They can not move. They will
 * randomly attack the player by reducing the player's health point to 1.
 */
public class Soul extends Monster implements Attackable {
	
	/**
	 * Percent that this soul will attack the player.
	 */
	private final int attackPercent = 20;
	
	/**
	 * Determine that this soul attacked the player in the last turn or not.
	 */
	private boolean isFriendly;

	/**
	 * The constructor of this class.
	 * 
	 * @param posY Position of this monster in the Y-axis
	 * @param posX Position of this monster in the X-axis
	 */
	public Soul(int posY, int posX) {
		super("Soul", 1, 0, 0, posY, posX, Direction.DOWN, 0, 0);
		setPositionOnMap(posY, posX);
	}

	@Override
	public int getSymbol() {
		if (isFriendly)
			return Sprites.FRIENDLY_SOUL;
		return Sprites.SOUL;
	}

	/**
	 * Updates this soul. If this soul already died, remove it from the game. If
	 * this soul can attack the player, it will randomly attack the player by
	 * reducing the player's health point to 1. 
	 */
	@Override
	public void update() {
		if (RandomUtil.random(1, 100) <= attackPercent) {
			setFriendly(false);
		} else
			setFriendly(true);
		if (getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (isAttackable(gamePlayer)) {
			attack(gamePlayer);
		}
	}

	@Override
	public boolean attack(Entity target) {
		if (isFriendly()) {
			return true;
		}
		if (target.getHealth() > 0) {
			MessageTextUtil.textWhenAttack(this, target, target.getHealth() - 1);
			target.setHealth(1);
		} else {
			MessageTextUtil.textWhenAttack(this, target, 0);
		}
		target.setAttacked(true);
		return true;
	}

	/**
	 * Checks if this soul attacked the player in the last turn or not.
	 * 
	 * @return False if this soul attacked the player in the last turn, true
	 *         otherwise.
	 */
	public boolean isFriendly() {
		return isFriendly;
	}

	/**
	 * Setter for isFriendly.
	 * 
	 * @param isFriendly friendly status to be set.
	 */
	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}

}
