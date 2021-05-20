package utils;

import java.util.List;

import controller.GameController;
import entity.Player;
import entity.base.Monster;
import javafx.application.Platform;
import logic.Direction;
import logic.MapRenderer;

/**
 * The AnimationUtil class provides methods about animation.
 *
 */
public class AnimationUtil {

	/**
	 * A constant holding duration of attack animation in millisecond.
	 */
	private static final int ATTACK_ANIMATION_DURATION_MS = 300;
	
	/**
	 * A constant holding duration of move animation frame in millisecond.
	 */
	private static final int FRAME_DURATION_MS = 20;
	
	/**
	 * A constant holding max frame number.
	 */
	private static final int MAX_FRAME_NUMBER = 31;
	
	/**
	 * A constant holding amount of frame change per loop.
	 */
	private static final int FRAME_CHANGE_PER_LOOP = 2;

	/**
	 * Plays attack and move animation and return animation thread.
	 * 
	 * @param step The amount of center position change per frame (in pixel)
	 * @return Animation thread
	 */
	public static Thread playAnimation(int step) {
		Player player = GameController.getPlayer();
		List<Monster> monsterList = GameController.getGameMap().getMonsterList();
		Thread animation = new Thread(() -> {
			// Checks if any entity move or attacked
			boolean isAttacked = player.isAttacked();
			boolean isMove = player.isMoving();
			for (Monster monster : monsterList) {
				isMove |= monster.isMoving();
				isAttacked |= monster.isAttacked();
			}
			isMove &= !GameConfig.isSkipMoveAnimation();
			if (!isMove && isAttacked) {
				Platform.runLater(() -> {
					MapRenderer.render();
				});
			}

			// Plays move and attack animation
			Thread attackAnimation = null;
			Thread moveAnimation = null;
			try {
				if (isAttacked) {
					attackAnimation = playAttackAnimation();
				}
				if (isMove) {
					int stepX = -Direction.getMoveX(player.getDirection(), step);
					int stepY = -Direction.getMoveY(player.getDirection(), step);
					moveAnimation = playMoveAnimation(stepY, stepX);
				}
				if (moveAnimation != null) {
					moveAnimation.join();
				}
				if (attackAnimation != null) {
					attackAnimation.join();
				}

			} catch (InterruptedException e) {
				System.out.println("animation interrupted");
			}

			boolean finalIsAttacked = isAttacked;
			Platform.runLater(() -> {
				for (Monster monster : monsterList) {
					monster.setAttacked(false);
					monster.setMoving(false);
				}
				player.setAttacked(false);
				player.setMoving(false);

				if (step == 0 || !GameConfig.isSkipMoveAnimation() || finalIsAttacked) {
					MapRenderer.render();
				}
			});
		});
		animation.start();
		return animation;
	}

	/**
	 * Plays attack animation and return animation thread.
	 * 
	 * @return Attack animation thread
	 */
	public static Thread playAttackAnimation() {
		Thread attackAnimation = new Thread(() -> {
			try {
				Thread.sleep(ATTACK_ANIMATION_DURATION_MS);
			} catch (InterruptedException e) {
				System.out.println("Attack animation interrupted");
			}
		});
		attackAnimation.start();
		return attackAnimation;
	}

	/**
	 * Plays move animation and return animation thread.
	 * 
	 * @param stepY The amount of center position change per frame in Y-axis (in
	 *              pixel)
	 * @param stepX The amount of center position change per frame in X-axis (in
	 *              pixel)
	 * @return Move animation thread
	 */
	public static Thread playMoveAnimation(int stepY, int stepX) {
		Thread moveAnimation = new Thread(() -> {
			Player player = GameController.getPlayer();
			int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();
			int centerY = player.getPosY() * newSpriteSize + newSpriteSize / 2;
			int centerX = player.getPosX() * newSpriteSize + newSpriteSize / 2;

			for (int frame = MAX_FRAME_NUMBER; frame >= 0; frame -= FRAME_CHANGE_PER_LOOP) {
				try {
					final int nowI = centerY + frame * stepY;
					final int nowJ = centerX + frame * stepX;
					final int nowCnt = frame;
					Platform.runLater(() -> {
						MapRenderer.render(nowI, nowJ, nowCnt);
					});
					Thread.sleep(FRAME_DURATION_MS);
				} catch (InterruptedException e) {
					System.out.println("Move animation interrupted");
				}
			}

		});
		moveAnimation.start();
		return moveAnimation;
	}

}
