package utils;

import controller.GameController;
import entity.Player;
import javafx.application.Platform;
import logic.Direction;

public class AnimationUtil {
	public static void playerMove(int direction) {
		GameController.getPlayer().setMoving(true);
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
		move(stepY,stepX);
	}
	
	public static void monsterMove() {
		move(0,0);
	}
	
	
	public static void move(int stepY,int stepX) {
		Player player = GameController.getPlayer();
		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();
		int centerY = player.getPosY()* newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = player.getPosX()* newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		new Thread() {
			public void run() {
//				InterruptController.setAnimate(true);
				
				for(int cnt = 31;cnt >=0;cnt-=2){
					try {
						final int nowI = centerY+cnt*stepY;
						final int nowJ = centerX+cnt*stepX;
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

				GameController.getPlayer().setMoving(false);
			}
		}.start();
	}
}
