package utils;

import controller.GameController;
import entity.Player;
import entity.base.Monster;
import javafx.application.Platform;
import logic.Direction;
import logic.GameLogic;

public class AnimationUtil {
	public static void playerMove(int direction) {
		final int step = GameConfig.getScale();
		int stepX = 0;
		int stepY = 0;
		if (direction == Direction.UP)
			stepY = step;
		if (direction == Direction.DOWN)
			stepY = -step;
		if (direction == Direction.LEFT)
			stepX = step;
		if (direction == Direction.RIGHT)
			stepX = -step;
		int finalStepY = stepY;
		int finalStepX = stepX;
		new Thread() {
			public void run() {
				GameController.getPlayer().setMoving(true);
				move(finalStepY, finalStepX);
				GameController.getPlayer().setMoving(false);
				Platform.runLater(() -> {
					GameLogic.postMoveUpdate();
					GameLogic.postGameUpdate();
				});
			}
		}.start();
	}

	public static void monsterMove() {
		new Thread() {
			public void run() {
				boolean isMove = false;
				for (Monster monster : GameController.getGameMap().getMonsterList())
					if (monster.isMoving())
						isMove = true;
				if (isMove) {
					move(0, 0);
					for (Monster monster : GameController.getGameMap().getMonsterList())
						monster.setMoving(false);
				}
			}
		}.start();
	}

	public static void move(int stepY, int stepX) {
		Player player = GameController.getPlayer();
		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();
		int centerY = player.getPosY() * newSpriteSize + GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = player.getPosX() * newSpriteSize + GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
//				InterruptController.setAnimate(true);

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//				InterruptController.setAnimate(false);

	}
}
