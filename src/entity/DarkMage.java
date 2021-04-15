package entity;

import controller.GameController;
import effects.Poison;
import entity.base.Entity;
import entity.base.Monster;
import javafx.util.Pair;
import logic.Sprites;
import utils.RandomUtil;

public class DarkMage extends Monster {

	private int poisonDamage;
	private int poisonDuration;
	private Poison poison;

	public DarkMage(int maxHealth, int defense, int posY, int posX, int direction, int poisonDamage,
			int poisonDuration) {
		super("Dark Mage", 0, maxHealth, defense, posY, posX, direction, 0, 0, 0);
		GameController.getGameMap().get(posY, posX).setEntity(this);
		setPoisonDamage(poisonDamage);
		setPoisonDuration(poisonDuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getSymbol() {
		return Sprites.DARK_MAGE;
	}

	@Override
	public void update() {
		if (getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (isAttackable(gamePlayer)) {
			//TODO change action ratio?
			int action = RandomUtil.random(0, 2);
			if (action == 0)
				poison(gamePlayer);
			else if (action== 1)
				warp(gamePlayer);
			else
				summonMonster();
		}
	}

	private void warp(Entity target) {
		Pair<Integer, Integer> newPos = GameController.getRoomList()
				.get(RandomUtil.random(0, GameController.getRoomList().size() - 2));
		target.setPos(newPos.getKey(), newPos.getValue());
		// TODO add fade animation
		// TODO add message
	}

	private void poison(Entity target) {
		if (poison == null || poison.getDuration() == 0) {
			poison = new Poison(getName(), poisonDamage, poisonDuration, false);
			poison.onAdd(target);
		} else {
			poison.setDuration(poisonDuration);
		}
		// TODO add message
	}

	private void summonMonster() {
		//TODO
	}
	public int getPoisonDamage() {
		return poisonDamage;
	}

	public void setPoisonDamage(int poisonDamage) {
		this.poisonDamage = poisonDamage;
	}

	public int getPoisonDuration() {
		return poisonDuration;
	}

	public void setPoisonDuration(int poisonDuration) {
		this.poisonDuration = poisonDuration;
	}

}
