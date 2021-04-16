package utils;

import controller.GameController;
import controller.InterruptController;
import entity.Player;
import entity.base.Monster;
import javafx.application.Platform;
import logic.Direction;
import logic.GameLogic;

public class AnimationUtil {

	public static Thread playAnimation(int step) {
		Player player = GameController.getPlayer();
		Thread animation = new Thread() {
			public void run() {

				boolean isAttacked = player.isAttacked();
				boolean isMove = player.isMoving();
				for (Monster monster : GameController.getGameMap().getMonsterList()) {
					isMove |= monster.isMoving();
					isAttacked |= monster.isAttacked();
				}
				isMove &= !GameConfig.isSkipMoveAnimation();
				if (!isMove) {
					Platform.runLater(() -> {
						GameController.getGameMap().drawMap();
					});
				}
				Thread attackAnimation = null;
				Thread moveAnimation = null;
				try {
					if (isAttacked) {
						attackAnimation = AnimationUtil.playAttackAnimation();
					}
					if (isMove) {
						moveAnimation = AnimationUtil.playMoveAnimation(
								-Direction.getMoveY(player.getDirection(), step),
								-Direction.getMoveX(player.getDirection(), step));
					}
					if (moveAnimation != null) {
						moveAnimation.join();
					}
					if (attackAnimation != null) {
						attackAnimation.join();
					}

				} catch (InterruptedException e) {
					System.out.println("animation interrupted");
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					GameController.getGameMap().drawMap();
					for (Monster monster : GameController.getGameMap().getMonsterList()) {
						monster.setAttacked(false);
					}
					GameController.getPlayer().setAttacked(false);
					for (Monster monster : GameController.getGameMap().getMonsterList()) {
						monster.setMoving(false);
					}
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
					Thread.sleep(300);
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
				int centerY = player.getPosY() * newSpriteSize + GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
				int centerX = player.getPosX() * newSpriteSize + GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;

				for (int cnt = 31; cnt >= 0; cnt -= 2) {
					try {
						final int nowI = centerY + cnt * stepY;
						final int nowJ = centerX + cnt * stepX;
						final int nowCnt = cnt;
						Platform.runLater(() -> {
							GameController.getGameMap().drawMap(nowI, nowJ, nowCnt);
						});
						Thread.sleep(20);
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
