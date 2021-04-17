package entity;

import controller.GameController;
import controller.InterruptController;
import effects.Poison;
import entity.base.Entity;
import entity.base.Monster;
import javafx.animation.FadeTransition;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;
import logic.GameMap;
import logic.MapRenderer;
import logic.Sprites;
import scene.GameScene;
import utils.GameConfig;
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
			int action = RandomUtil.random(1, 100);
			if (action <= 20) {
				poison(gamePlayer);
			} else if (action <= 50) {
				warp(gamePlayer);
			}
		}
	}

	private void warp(Entity target) {
		Pair<Integer, Integer> newPos = GameController.getRoomList()
				.get(RandomUtil.random(1, GameController.getRoomList().size() - 2));
		// TODO add fade animation
		FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

		InterruptController.setTransition(true);

		int randSummon = RandomUtil.random(1, 100);

		fadeOut.setOnFinished((event) -> {
			target.setPositionOnMap(newPos.getKey(), newPos.getValue());
			if (randSummon <= 50) {
				summonMonster(target);
			}
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

	private void summonMonster(Entity target) {
		int level = GameController.getLevel();
		int range = GameConfig.LINE_OF_SIGHT;
		GameMap gameMap = GameController.getGameMap();

		int posY = target.getPosY();
		int posX = target.getPosX();

		int diff[][] = { { 0, range }, { range, 0 }, { -range, 0 }, { 0, -range } };

		for (int i = 0; i < 4; i++) {
			int newY = posY + diff[i][0];
			int newX = posX + diff[i][1];

			if ((gameMap.get(newY, newX).getEntity() == null) && (gameMap.get(newY, newX).getType() == Cell.PATH)) {
				Monster summonedMonster = new Skeleton(level * 5, level * 3, level, newY, newX, Direction.DOWN, 1.5,
						0.5, 1);
				gameMap.getMonsterList().add(summonedMonster);
			}
		}
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
