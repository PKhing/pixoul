package entity;

import controller.GameController;
import controller.InterruptController;
import effects.Poison;
import entity.base.Entity;
import entity.base.Monster;
import javafx.animation.FadeTransition;
import javafx.util.Pair;
import logic.MapRenderer;
import logic.Sprites;
import scene.GameScene;
import utils.MessageTextUtil;
import utils.RandomUtil;
import utils.TransitionUtil;

public class DarkMage extends Monster {

	private int poisonDamage;
	private int poisonDuration;
	private Poison poison;

	public DarkMage(int maxHealth, int defense, int posY, int posX, int direction, int poisonDamage,
			int poisonDuration) {
		super("Dark Mage", maxHealth, 0, defense, posY, posX, direction, 0, 0, 0);
		setPositionOnMap(posY, posX);
		setPoisonDamage(poisonDamage);
		setPoisonDuration(poisonDuration);
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
			// TODO change action ratio?
			int action = RandomUtil.random(0, 2);
			if (action == 0)
				poison(gamePlayer);
			else if (action == 1)
				warp(gamePlayer);
			else
				summonMonster();
		}
	}

	private void warp(Entity target) {
		Pair<Integer, Integer> newPos = GameController.getRoomList()
				.get(RandomUtil.random(1, GameController.getRoomList().size() - 2));
		// TODO add fade animation
		FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

		InterruptController.setTransition(true);

		fadeOut.setOnFinished((event) -> {
			target.setPositionOnMap(newPos.getKey(), newPos.getValue());
			MapRenderer.render();
			MessageTextUtil
					.makeNewMessage("%s has been teleported to somewhere in this level".formatted(target.getName()));
			fadeIn.play();
		});
		fadeIn.setOnFinished((event) -> InterruptController.setTransition(false));

		fadeOut.play();
	}

	private void poison(Entity target) {
		if ((poison == null) || (poison.getDuration() == 0)) {
			poison = new Poison(getName(), poisonDamage, poisonDuration, false);
			poison.onAdd(target);
		} else {
			poison.setDuration(poisonDuration);
		}
		MessageTextUtil.makeNewMessage("%s has been poisoned by Dark mage".formatted(target.getName()));
	}

	private void summonMonster() {
		// TODO
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
