package utils;

import java.util.List;

import controller.GameController;
import entity.Player;
import entity.base.Monster;
import javafx.application.Platform;
import logic.Direction;

public class AnimationUtil {

	private static final int ATTACK_ANIMATION_DURATION_MS = 300;
	private static final int FRAME_DURATION_MS = 20;

	public static Thread playAnimation(int step) {
		Player player = GameController.getPlayer();
		List<Monster> monsterList = GameController.getGameMap().getMonsterList();
		Thread animation = new Thread() {
			@Override
			public void run() {
				// Checks if any entity move or attacked
				boolean isAttacked = player.isAttacked();
				boolean isMove = player.isMoving();
				for (Monster monster : monsterList) {
					isMove |= monster.isMoving();
					isAttacked |= monster.isAttacked();
				}
				isMove &= !GameConfig.isSkipMoveAnimation();
				if (!isMove) {
					Platform.runLater(() -> {
						GameController.getGameMap().drawMap();
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

				Platform.runLater(() -> {
					GameController.getGameMap().drawMap();

					// Reset isMove and isAttacked of each entity
					for (Monster monster : monsterList) {
						monster.setAttacked(false);
						monster.setMoving(false);
					}
					player.setAttacked(false);
					player.setMoving(false);
				});
			}
		};
		animation.start();
		return animation;
	}

	public static Thread playAttackAnimation() {
		Thread attackAnimation = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(ATTACK_ANIMATION_DURATION_MS);
				} catch (InterruptedException e) {
					System.out.println("attack animation interrupted");
				}
			}
		};
		attackAnimation.start();
		return attackAnimation;
	}

	public static Thread playMoveAnimation(int stepY, int stepX) {
		Thread moveAnimation = new Thread() {
			@Override
			public void run() {
				Player player = GameController.getPlayer();
				int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();
				int centerY = player.getPosY() * newSpriteSize + newSpriteSize / 2;
				int centerX = player.getPosX() * newSpriteSize + newSpriteSize / 2;

				for (int frame = 31; frame >= 0; frame -= 2) {
					try {
						final int nowI = centerY + frame * stepY;
						final int nowJ = centerX + frame * stepX;
						final int nowCnt = frame;
						Platform.runLater(() -> {
							GameController.getGameMap().drawMap(nowI, nowJ, nowCnt);
						});
						Thread.sleep(FRAME_DURATION_MS);
					} catch (InterruptedException e) {
						System.out.println("Move animation interrupted");
					}
				}

			}
		};
		moveAnimation.start();
		return moveAnimation;
	}

}
