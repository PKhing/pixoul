package entity.base;

import java.util.LinkedList;
import java.util.Queue;

import controller.GameController;
import entity.Player;
import javafx.util.Pair;
import logic.Cell;
import logic.Direction;
import utils.GameConfig;
import utils.RandomUtil;

public abstract class Monster extends Entity {
	@SuppressWarnings("unchecked")
	private Pair<Integer, Integer>[][] parent = new Pair[GameConfig.MAP_SIZE + 1][GameConfig.MAP_SIZE + 1];
	private boolean visit[][] = new boolean[GameConfig.MAP_SIZE + 1][GameConfig.MAP_SIZE + 1];

	public Monster(String name, int attack, int maxHealth, int defense, int posY, int posX, int direction,
			double critRate, double critPercent, int moveSpeed) {
		super(name, attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
	}

	public abstract void update();

	public int getNextDirection() {
		
		Pair<Integer, Integer> playerPos = bfs(1);
		if (playerPos == null) {
			playerPos = bfs(2);
		}
		if (playerPos == null)
			return -1;
		Pair<Integer, Integer> newPos = playerPos;
		while (!(new Pair<>(getPosY(), getPosX()).equals(parent[newPos.getKey()][newPos.getValue()]))) {
			newPos = parent[newPos.getKey()][newPos.getValue()];
		}
		int newY = newPos.getKey();
		int newX = newPos.getValue();
	
		if (GameController.getGameMap().get(newY, newX).getEntity() != null)
			return -1;
		if (newY - this.getPosY() == 1) {
			return Direction.DOWN;
		}
		if (newY - this.getPosY() == -1) {
			return Direction.UP;
		}
		if (newX - this.getPosX() == -1) {
			return Direction.LEFT;
		}
		if (newX - this.getPosX() == 1) {
			return Direction.RIGHT;
		}
		return -1;
	}

	private Pair<Integer, Integer> bfs(int type) {
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				parent[i][j] = null;
				visit[i][j] = false;
			}
		}
		int move[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		RandomUtil.shuffle(move);

		Queue<Pair<Integer, Pair<Integer, Integer>>> queue = new LinkedList<>();
		queue.add(new Pair<>(0, new Pair<>(this.getPosY(), this.getPosX())));

		while (!queue.isEmpty()) {

			int length = queue.peek().getKey();
			int y = queue.peek().getValue().getKey();
			int x = queue.peek().getValue().getValue();

			queue.remove();

			if (GameController.getGameMap().get(y, x).getType() == Cell.WALL
					|| length > GameConfig.MONSTER_LINE_OF_SIGHT || visit[y][x]) {
				continue;
			}

			if (type == 1) {
				if (GameController.getGameMap().get(y, x).getEntity() != null
						&& GameController.getGameMap().get(y, x).getEntity() != this
						&& !(GameController.getGameMap().get(y, x).getEntity() instanceof Player)) {
					continue;
				}
			}
			visit[y][x] = true;

			if (GameController.getGameMap().get(y, x).getEntity() instanceof Player) {
				return new Pair<>(y, x);
			}

			for (int i = 0; i < 4; i++) {
				if (!visit[y + move[i][0]][x + move[i][1]]) {
					parent[y + move[i][0]][x + move[i][1]] = new Pair<>(y, x);
					queue.add(new Pair<>(length + 1, new Pair<>(y + move[i][0], x + move[i][1])));
				}
			}
		}
		return null;
	}

}
