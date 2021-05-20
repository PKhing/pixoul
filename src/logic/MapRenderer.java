package logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

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

/**
 * The MapRenderer class is used to render {@link GameMap}.
 *
 */
public class MapRenderer {
	
	/**
	 * The Renderable interface allows an object to be rendered. This interface
	 * should be implemented to pass render function as a parameter.
	 * 
	 */
	private static interface Renderable {

		/**
		 * Render an object by calling {@link DrawUtil}.
		 */
		public void render();
	}

	/**
	 * A class to represents position, priority, and method for rendering. This
	 * class is used for map rendering only.
	 *
	 */
	private static class Node implements Comparable<Node> {
		
		/**
		 * Cell index of this Node in the X-axis.
		 */
		public int x;
		
		/**
		 * Cell index of this Node in the Y-axis.
		 */
		public int y;
		
		/**
		 * Priority of this Node.
		 */
		public int priority;
		
		/**
		 * Object that contains render method.
		 */
		public Renderable obj;

		/**
		 * Creates new node.
		 * 
		 * @param y        Cell index of this Node in the Y-axis.
		 * @param x        Cell index of this Node in the X-axis.
		 * @param priority Priority of this Node.
		 * @param obj      Object that contains render method.
		 */
		public Node(int y, int x, int priority, Renderable obj) {
			this.obj = obj;
			this.y = y;
			this.x = x;
			this.priority = priority;
		}

		@Override
		public int compareTo(Node node) {
			if (this.priority == node.priority) {
				if (this.y == node.y) {
					if (this.x < node.x) {
						return -1;
					}
					return 1;
				}
				if (this.y < node.y) {
					return -1;
				}
				return 1;
			}
			if (this.priority < node.priority) {
				return -1;
			}
			return 1;
		}
	}

	/**
	 * Renders this level by having the player at the center of the screen.
	 */
	public static void render() {
		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();
		int centerY = GameController.getPlayer().getPosY() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		int centerX = GameController.getPlayer().getPosX() * newSpriteSize
				+ GameConfig.SPRITE_SIZE * GameConfig.getScale() / 2;
		render(centerY, centerX, 0);
	}

	/**
	 * Renders this level by having the specified position at the center of the
	 * screen.
	 * 
	 * @param centerY Center position in Y-axis
	 * @param centerX Center position in X-axis
	 * @param frame   Current animation frame
	 */
	public static void render(int centerY, int centerX, int frame) {
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

		ArrayList<Pair<Integer, Integer>> posList = new ArrayList<>();

		for (int i = startIdxY; i <= endIdxY; i++) {
			for (int j = startIdxX; j <= endIdxX; j++) {
				posList.add(new Pair<Integer, Integer>(i, j));
			}
		}

		PriorityQueue<Node> pq = buildPrioritizedNode(allVisibleField, startY, startX, frame);

		while (!pq.isEmpty()) {
			Node node = pq.poll();
			node.obj.render();
		}
	}

	/**
	 * Builds priority queue of nodes to be rendered. Sorted by priority of each
	 * rendering method.
	 * 
	 * @param posList List of cell's position to be rendered
	 * @param startY  Start rendering position in Y-axis
	 * @param startX  Start rendering position in X-axis
	 * @param frame   Current animation frame
	 * @return Priority queue of nodes to be rendered
	 */
	private static PriorityQueue<Node> buildPrioritizedNode(ArrayList<Pair<Integer, Integer>> posList, int startY,
			int startX, int frame) {
		GameMap gameMap = GameController.getGameMap();
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		int newSpriteSize = GameConfig.SPRITE_SIZE * GameConfig.getScale();

		for (Pair<Integer, Integer> pos : posList) {
			int i = pos.getKey();
			int j = pos.getValue();

			int posY = newSpriteSize * i - startY;
			int posX = newSpriteSize * j - startX;
			Cell thisCell = gameMap.get(i, j);
			Entity entity = thisCell.getEntity();
			int shiftX = 0;
			int shiftY = 0;

			if ((entity != null) && entity.isMoving()) {
				shiftX = -Direction.getMoveX(entity.getDirection(), frame * GameConfig.getScale());
				shiftY = -Direction.getMoveY(entity.getDirection(), frame * GameConfig.getScale());
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
			if (thisCell.getItem() != null) {
				pq.add(new Node(posY, posX, 1, () -> {
					DrawUtil.drawItemOnCell(posY, posX, thisCell.getItem());
				}));
			}

			// Draw entity
			if (thisCell.getEntity() != null) {
				pq.add(new Node(posY, posX, 2, () -> {
					DrawUtil.drawEntity(posY + finalShiftY, posX + finalShiftX, thisCell.getEntity(), frame);
				}));
			}
			// Draw Monster HP Bar
			if (thisCell.getEntity() instanceof Monster) {
				pq.add(new Node(posY, posX, 100, () -> {
					DrawUtil.drawHPBar(posY + finalShiftY, posX + finalShiftX, thisCell.getEntity());
				}));
			}
			if ((thisCell.getEntity() instanceof Monster) && (frame == 0)) {
				pq.add(new Node(posY, posX, 2, () -> {
					DrawUtil.addEntityButton(posY + finalShiftY, posX + finalShiftX, thisCell.getEntity());
				}));
			}
		}

		return pq;
	}

}
