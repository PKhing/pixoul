package entity.base;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import controller.GameController;
import entity.Player;
import javafx.util.Pair;
import logic.Cell;
import utils.GameConfig;
import utils.Util;

public abstract class Monster extends Entity {
	@SuppressWarnings("unchecked")
	private Pair<Integer, Integer>[][] parent = new Pair[GameConfig.MAP_SIZE + 1][GameConfig.MAP_SIZE + 1];
	private boolean visit[][] = new boolean[GameConfig.MAP_SIZE + 1][GameConfig.MAP_SIZE + 1];

	public Monster(int health, int attack, int maxHealth, int defense, int posY, int posX, int direction,
			double critRate, double critPercent, int moveSpeed) {
		super(health, attack, maxHealth, defense, posY, posX, direction, critRate, critPercent, moveSpeed);
	}

	public abstract void update();
	
	public Pair<Integer, Integer> getNextPos() {
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				parent[i][j] = null;
				visit[i][j] = false;
			}
		}
		Pair<Integer, Integer> playerPos = bfs();
		if (playerPos == null)
			return null;
		Pair<Integer, Integer> newPos = playerPos;
		while (!(new Pair<>(getPosY(), getPosX()).equals(parent[newPos.getKey()][newPos.getValue()]))) {
			newPos = parent[newPos.getKey()][newPos.getValue()];
		}
		return newPos;
	}

	private Pair<Integer, Integer> bfs() {

		int move[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		Util.shuffle(move);
		
		Queue<Pair<Integer, Pair<Integer, Integer>>> queue = new LinkedList<>();
		queue.add(new Pair<>(0, new Pair<>(this.getPosY(), this.getPosX())));

		while (!queue.isEmpty()) {

			int length = queue.peek().getKey();
			int y = queue.peek().getValue().getKey();
			int x = queue.peek().getValue().getValue();

			queue.remove();

			if (GameController.getGameMap().get(y, x).getType() != Cell.PATH
					|| length > GameConfig.MONSTER_LINE_OF_SIGHT || visit[y][x]) {
				continue;
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