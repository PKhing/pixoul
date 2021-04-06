package logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.GameController;
import entity.base.Monster;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import scene.GameScene;
import utils.DrawUtil;
import utils.GameConfig;

public class GameMap {
	private Cell gameMap[][];
	private List<Pair<Integer, Integer>> roomList;
	private List<Monster> monsterList;

	public GameMap() {
		gameMap = new Cell[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];
		monsterList = new CopyOnWriteArrayList<Monster>();
		roomList = new CopyOnWriteArrayList<Pair<Integer, Integer>>();
	}

	public void printMap() {
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				if (gameMap[i][j].getType() == Cell.PATH)
					System.out.print(" ");
				else if (gameMap[i][j].getType() == Cell.WALL)
					System.out.print("#");
				else {
					System.out.print(".");
				}
			}
			System.out.print("\n");
		}
	}

	public void drawMap() {
		GraphicsContext gc = GameScene.getGraphicsContext();
		AnchorPane buttonPane = GameScene.getButtonPane();
		buttonPane.getChildren().clear();
		gc.setFill(Color.rgb(0, 0, 0));
		gc.fillRect(0, 0, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();

		int centerY = GameController.getPlayer().getPosY() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = GameController.getPlayer().getPosX() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;

		int startY = centerY - GameConfig.getScreenHeight() / 2;
		int startX = centerX - GameConfig.getScreenWidth() / 2;

		int maxCellY = GameConfig.getScreenHeight() / (newSpriteSize);
		int maxCellX = GameConfig.getScreenWidth() / (newSpriteSize);

		int startIdxY = Math.max(0, GameController.getPlayer().getPosY() - maxCellY / 2 - 1);
		int endIdxY = Math.min(GameConfig.MAP_SIZE, GameController.getPlayer().getPosY() + maxCellY / 2 + 1);

		int startIdxX = Math.max(0, GameController.getPlayer().getPosX() - maxCellX / 2 - 1);
		int endIdxX = Math.min(GameConfig.MAP_SIZE, GameController.getPlayer().getPosX() + maxCellX / 2 + 1);

		GameMap gameMap = GameController.getGameMap();

//		ArrayList<Pair<Integer, Integer>> allVisibleField = GameController.getPlayer().getAllVisibleField(startIdxY,
//				endIdxY, startIdxX, endIdxX);
//
//		allVisibleField.sort(Comparator.comparing(Pair<Integer, Integer>::getKey).thenComparingInt(Pair::getValue));
//
//		for (Pair<Integer, Integer> pos : allVisibleField) {
//			int posX = pos.getValue();
//			int posY = pos.getKey();
//
//			int writeX = newSpriteSize * posX - startX;
//			int writeY = newSpriteSize * posY - startY;
//
//			Cell nowCell = gameMap.get(posY, posX);
//			
//			DrawUtil.drawCell(writeY, writeX, nowCell);
//			DrawUtil.drawLadder(writeY, writeX, nowCell);
//			if (nowCell.getItem() != null) {
//				DrawUtil.drawItemOnCell(writeY, writeX, nowCell.getItem());
//			}
//			
//			if (nowCell.getEntity() != null)
//				DrawUtil.drawEntity(writeY, writeX, nowCell.getEntity());
//			if (nowCell.getEntity() instanceof Monster)
//				DrawUtil.addEntityButton(writeY, writeX, nowCell.getEntity());
//		}

		for (int i = startIdxY; i <= endIdxY; i++) {
			for (int j = startIdxX; j <= endIdxX; j++) {
				DrawUtil.drawCell(newSpriteSize * i - startY, newSpriteSize * j - startX,
						gameMap.get(i, j));
				DrawUtil.drawLadder(newSpriteSize * i - startY, newSpriteSize * j - startX,
						gameMap.get(i, j));
				if (gameMap.get(i, j).getEntity() != null)
					DrawUtil.drawEntity(newSpriteSize * i - startY, newSpriteSize * j - startX,
							gameMap.get(i, j).getEntity());
				if (gameMap.get(i, j).getEntity() instanceof Monster)
					DrawUtil.addEntityButton(newSpriteSize * i - startY, newSpriteSize * j - startX,
							gameMap.get(i, j).getEntity());
			}
		}

	}

	public Cell[][] getGameMap() {
		return gameMap;
	}

	public Cell get(int i, int j) {
		if (i < 0 || i > GameConfig.MAP_SIZE || j < 0 || j > GameConfig.MAP_SIZE)
			return new Cell();
		return gameMap[i][j];
	}

	public List<Pair<Integer, Integer>> getRoomList() {
		return roomList;
	}

	public List<Monster> getMonsterList() {
		return monsterList;
	}
}
