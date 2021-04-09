package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.GameController;
import entity.base.Entity;
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

	class Node implements Comparable<Node> {
		public int x;
		public int y;
		public int priority;
		public Renderable obj;

		public Node(int y, int x, int priority, Renderable obj) {
			this.obj = obj;
			this.y = y;
			this.x = x;
			this.priority = priority;
		}

		public int compareTo(Node node) {
			if (this.priority == node.priority) {
				if (this.y < node.y)
					return -1;
				return 1;
			}
			if (this.priority < node.priority)
				return -1;
			return 1;
		}
	}

	public void drawMap() {
		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();
		int centerY = GameController.getPlayer().getPosY() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = GameController.getPlayer().getPosX() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		drawMap(centerY, centerX, 0);
	}

	public void drawMap(int centerY, int centerX, int cnt) {
		GraphicsContext gc = GameScene.getGraphicsContext();
		AnchorPane buttonPane = GameScene.getButtonPane();
		buttonPane.getChildren().clear();
		gc.setFill(Color.rgb(0, 0, 0));
		gc.fillRect(0, 0, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());

		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();

		int startY = centerY - GameConfig.getScreenHeight() / 2;
		int startX = centerX - GameConfig.getScreenWidth() / 2;

		int maxCellY = GameConfig.getScreenHeight() / (newSpriteSize);
		int maxCellX = GameConfig.getScreenWidth() / (newSpriteSize);

		int startIdxY = Math.max(0, GameController.getPlayer().getPosY() - maxCellY / 2 - 1);
		int endIdxY = Math.min(GameConfig.MAP_SIZE, GameController.getPlayer().getPosY() + maxCellY / 2 + 1);

		int startIdxX = Math.max(0, GameController.getPlayer().getPosX() - maxCellX / 2 - 1);
		int endIdxX = Math.min(GameConfig.MAP_SIZE, GameController.getPlayer().getPosX() + maxCellX / 2 + 1);

		ArrayList<Pair<Integer, Integer>> allVisibleField = GameController.getPlayer().getAllVisibleField(startIdxY,
				endIdxY, startIdxX, endIdxX);
		//
		// allVisibleField.sort(Comparator.comparing(Pair<Integer,
		// Integer>::getKey).thenComparingInt(Pair::getValue));
		//
		// for (Pair<Integer, Integer> pos : allVisibleField) {
		// int posX = pos.getValue();
		// int posY = pos.getKey();
		//
		// int writeX = newSpriteSize * posX - startX;
		// int writeY = newSpriteSize * posY - startY;
		//
		// Cell nowCell = gameMap.get(posY, posX);
		//
		// DrawUtil.drawCell(writeY, writeX, nowCell);
		// DrawUtil.drawLadder(writeY, writeX, nowCell);
		// if (nowCell.getItem() != null) {
		// DrawUtil.drawItemOnCell(writeY, writeX, nowCell.getItem());
		// }
		//
		// if (nowCell.getEntity() != null)
		// DrawUtil.drawEntity(writeY, writeX, nowCell.getEntity());
		// if (nowCell.getEntity() instanceof Monster)
		// DrawUtil.addEntityButton(writeY, writeX, nowCell.getEntity());
		// }

		ArrayList<Pair<Integer, Integer>> posList = new ArrayList<>();

		for (int i = startIdxY; i <= endIdxY; i++) {
			for (int j = startIdxX; j <= endIdxX; j++) {
				posList.add(new Pair<Integer, Integer>(i, j));
			}
		}

		PriorityQueue<Node> pq = buildPrioritizeNode(allVisibleField, newSpriteSize, startY, startX, cnt);

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			node.obj.render();
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

	private PriorityQueue<Node> buildPrioritizeNode(ArrayList<Pair<Integer, Integer>> arr, int newSpriteSize,
			int startY, int startX, int cnt) {
		GameMap gameMap = GameController.getGameMap();
		PriorityQueue<Node> pq = new PriorityQueue<Node>();

		for (Pair<Integer, Integer> pos : arr) {
			int i = pos.getKey();
			int j = pos.getValue();

			int posY = newSpriteSize * i - startY;
			int posX = newSpriteSize * j - startX;
			Cell thisCell = gameMap.get(i, j);
			Entity entity = thisCell.getEntity();
			int shiftX = 0;
			int shiftY = 0;

			if (entity != null && entity.isMoving()) {
				if (entity.getDirection() == Direction.UP)
					shiftY = cnt * GameConfig.getScale();
				if (entity.getDirection() == Direction.DOWN)
					shiftY = -cnt * GameConfig.getScale();
				if (entity.getDirection() == Direction.LEFT)
					shiftX = cnt * GameConfig.getScale();
				if (entity.getDirection() == Direction.RIGHT)
					shiftX = -cnt * GameConfig.getScale();
			}
			int finalShiftY = shiftY;
			int finalShiftX = shiftX;

			// Draw Wall and Path
			if (thisCell.getType() == Cell.WALL) {
				pq.add(new Node(posY, posX, 50, () -> {
					DrawUtil.drawCell(posY, posX, thisCell);
				}));
			} else {
				pq.add(new Node(posY, posX, 0, () -> {
					DrawUtil.drawCell(posY, posX, thisCell);
				}));
			}

			// Draw Ladder
			int ladderPriority = 3;

			if (thisCell.getEntity() != null) {
				ladderPriority = 1;
			}

			if (thisCell.getType() == Cell.LADDER_UP) {
				pq.add(new Node(posY, posX, ladderPriority, () -> {
					DrawUtil.drawLadder(posY, posX, thisCell);
				}));
			} else if (thisCell.getType() == Cell.LADDER_DOWN) {
				pq.add(new Node(posY, posX, ladderPriority, () -> {
					DrawUtil.drawLadder(posY, posX, thisCell);
				}));
			}

			// Draw item which on cell
			if (thisCell.getItem() != null)
				pq.add(new Node(posY, posX, 1, () -> {
					DrawUtil.drawItemOnCell(posY, posX, thisCell.getItem());
				}));

			// Draw entity
			if (thisCell.getEntity() != null)
				pq.add(new Node(posY, posX, 2, () -> {
					DrawUtil.drawEntity(posY + finalShiftY, posX + finalShiftX, thisCell.getEntity());
				}));

			// Draw Monster HP Bar
			if (thisCell.getEntity() instanceof Monster)
				pq.add(new Node(posY, posX, 100, () -> {
					DrawUtil.drawHPBar(posY + finalShiftY, posX + finalShiftX, thisCell.getEntity());
				}));
			if (thisCell.getEntity() instanceof Monster)
				pq.add(new Node(posY, posX, 2, () -> {
					DrawUtil.addEntityButton(posY + finalShiftY, posX + finalShiftX, thisCell.getEntity());
				}));
		}

		return pq;
	}
}
