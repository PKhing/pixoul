package entity;

import controller.GameController;
import entity.base.Attackable;
import entity.base.Entity;
import entity.base.Monster;
import logic.Direction;
import logic.Sprites;
import utils.MessageTextUtil;
import utils.RandomUtil;

public class Soul extends Monster implements Attackable {
	private final int attackPercent = 20;
	private boolean isFriendly;

	public Soul(int posY, int posX) {
		super("Soul", 0, 1, 0, posY, posX, Direction.DOWN, 0, 0, 0);
		GameController.getGameMap().get(posY, posX).setEntity(this);
	}

	@Override
	public int getSymbol() {
		if (isFriendly)
			return Sprites.FRIENDLY_SOUL;
		return Sprites.SOUL;
	}

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
		if (isFriendly())
			return true;
		if (target.getHealth() > 0) {
			MessageTextUtil.textWhenAttack(this, target, target.getHealth() - 1);
			target.setHealth(1);
		} else {
			MessageTextUtil.textWhenAttack(this, target, 0);
		}
		target.setAttacked(true);
		return true;
	}

	public boolean isFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}

}
