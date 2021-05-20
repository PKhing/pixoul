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

/**
 * The DarkMage class represents dark mage monsters. They can not attack or move
 * but they can warp the player to a random room on the current map, inflict
 * {@link Poison poison effect} on the player, or summon {@link Skeleton
 * skeleton}.
 */
public class DarkMage extends Monster {

	/**
	 * The damage of the poison effect that the dark mage will inflict on the
	 * player.
	 */
	private int poisonDamage;
	
	/**
	 * The duration of the poison effect that the dark mage will inflict on the
	 * player.
	 */
	private int poisonDuration;
	
	/**
	 * The poison effect that the dark mage inflict on the player.
	 */
	private Poison poison;

	/**
	 * The constructor for DarkMage class.
	 * 
	 * @param maxHealth      Maximum amount of health point of this monster.
	 * @param defense        The defense value of this monster
	 * @param posY           Position of this monster in the Y-axis
	 * @param posX           Position of this monster in the X-axis
	 * @param direction      {@link Direction} of this monster
	 * @param poisonDamage   The damage of the poison effect that the dark mage will
	 *                       Inflict on the player.
	 * @param poisonDuration The duration of the poison effect that the dark mage
	 *                       will inflict on the player.
	 */
	public DarkMage(int maxHealth, int defense, int posY, int posX, int direction, int poisonDamage,
			int poisonDuration) {
		super("Dark Mage", maxHealth, 0, defense, posY, posX, direction, 0, 0);
		setPositionOnMap(posY, posX);
		setPoisonDamage(poisonDamage);
		setPoisonDuration(poisonDuration);
	}

	@Override
	public int getSymbol() {
		return Sprites.DARK_MAGE;
	}

	/**
	 * Updates this dark mage. If this dark mage already dies, remove it from the
	 * game. If this dark mage is near the player, inflict {@link Poison poison
	 * effect} to the player, warp the player to a random room, or summon
	 * {@link Skeleton skeleton}.
	 */
	@Override
	public void update() {
		if (getHealth() <= 0) {
			remove();
			return;
		}
		Player gamePlayer = GameController.getPlayer();
		if (isAttackable(gamePlayer)) {
			int action = RandomUtil.random(1, 100);
			if (action <= 20) {
				poison(gamePlayer);
			} else if (action <= 50) {
				warp(gamePlayer);
			}
		}
	}

	/**
	 * Warp target entity to the random room on the current map.
	 * 
	 * @param target The target that will be warped
	 */
	private void warp(Entity target) {
		Pair<Integer, Integer> newPos = GameController.getRoomList()
				.get(RandomUtil.random(1, GameController.getRoomList().size() - 2));

		// Plays fade animation
		FadeTransition fadeOut = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 1.0, 0.0);
		FadeTransition fadeIn = TransitionUtil.makeFadingNode(GameScene.getGamePane(), 0.0, 1.0);

		InterruptController.setTransition(true);

		Entity currentEntity = GameController.getGameMap().get(newPos.getKey(), newPos.getValue()).getEntity();

		if (currentEntity != null) {
			GameController.getGameMap().getMonsterList().remove(currentEntity);
			GameController.getGameMap().get(newPos.getKey(), newPos.getValue()).setEntity(null);
		}

		int randSummon = RandomUtil.random(1, 100);

		fadeOut.setOnFinished((event) -> {
			target.setPositionOnMap(newPos.getKey(), newPos.getValue());
			if (randSummon <= 50) {
				summonMonster(target);
			}
			MapRenderer.render();
			MessageTextUtil.textWhenDarkMageWarp(target);
			fadeIn.play();
		});
		fadeIn.setOnFinished((event) -> InterruptController.setTransition(false));

		fadeOut.play();
	}

	/**
	 * Inflict {@link Poison poison effect} to the target entity.
	 * 
	 * @param target The target to inflict poison on
	 */
	private void poison(Entity target) {
		if ((poison == null) || (poison.getDuration() == 0)) {
			poison = new Poison(getName(), poisonDamage, poisonDuration, false);
			poison.onAdd(target);
		} else {
			poison.setDuration(poisonDuration);
		}
		MessageTextUtil.textWhenDarkMageUsePoison(target);
	}

	/**
	 * Summon skeleton around the target entity.
	 * 
	 * @param target The target entity
	 */
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
						0.5);
				gameMap.getMonsterList().add(summonedMonster);
			}
		}
	}

	/**
	 * Getter for poison damage.
	 * 
	 * @return This monster's poison damage
	 */
	public int getPoisonDamage() {
		return poisonDamage;
	}

	/**
	 * Setter for poison damage.
	 * 
	 * @param poisonDamage The poison damage to be set
	 */
	public void setPoisonDamage(int poisonDamage) {
		this.poisonDamage = poisonDamage;
	}

	/**
	 * Getter for poison duration.
	 * 
	 * @return This monster's poison duration
	 */
	public int getPoisonDuration() {
		return poisonDuration;
	}

	/**
	 * Setter for poison duration.
	 * 
	 * @param poisonDuration The poison duration to be set
	 */
	public void setPoisonDuration(int poisonDuration) {
		this.poisonDuration = poisonDuration;
	}

}
