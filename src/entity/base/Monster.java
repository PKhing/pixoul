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

/**
 * Base class to represent all monsters.
 *
 */
public abstract class Monster extends Entity {
	/**
	 * Parent cell position of the specified cell. This variable only uses for the
	 * breadth-first search method.
	 */
	@SuppressWarnings("unchecked")
	private Pair<Integer, Integer>[][] parent = new Pair[GameConfig.MAP_SIZE + 1][GameConfig.MAP_SIZE + 1];
	/**
	 * Visit status of the specified cell. This variable only uses for the
	 * breadth-first search method.
	 */
	private boolean visit[][] = new boolean[GameConfig.MAP_SIZE + 1][GameConfig.MAP_SIZE + 1];

	/**
	 * The constructor for this class.
	 * 
	 * @param name              Name of this entity
	 * @param maxHealth         Max amount of health point of this entity
	 * @param attack            Attack value of this entity
	 * @param defense           Defense value of this entity
	 * @param posY              Position of this entity in the Y-axis
	 * @param posX              Position of this entity in the X-axis
	 * @param direction         {@link Direction} of this monster
	 * @param critRate          Critical rate of this entity
	 * @param critDamagePercent Critical damage percent of this entity
	 */
	public Monster(String name, int maxHealth, int attack, int defense, int posY, int posX, int direction,
			double critRate, double critDamagePercent) {
		super(name, maxHealth, attack, defense, posY, posX, direction, critRate, critDamagePercent);
	}

	/**
	 * Update this monster. This method is called after the player does action to
	 * make the monster does an action.
	 */
	public abstract void update();

	/**
	 * Finds the next direction that the monster should walk to get closer to the
	 * player.
	 * 
	 * @return The next direction that the monster should walk
	 */
	public int getNextDirection() {

		// Finds the player position
		Pair<Integer, Integer> playerPos = bfs(1);
		if (playerPos == null) {
			playerPos = bfs(2);

			if (playerPos == null) {
				return -1;
			}
		}

		// Finds the next position of this monster
		Pair<Integer, Integer> newPos = playerPos;
		while (!(new Pair<>(getPosY(), getPosX()).equals(parent[newPos.getKey()][newPos.getValue()]))) {
			newPos = parent[newPos.getKey()][newPos.getValue()];
		}
		int newY = newPos.getKey();
		int newX = newPos.getValue();

		// Finds the next direction
		if (GameController.getGameMap().get(newY, newX).getEntity() != null) {
			return -1;
		}
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

	/**
	 * Do the breadth-first search to find the shortest path to the player.
	 * 
	 * @param type Type of the search
	 *             <ul>
	 *             <li>{@code '1'} don't walk through other monsters</li>
	 *             <li>{@code '2'} walk through other monsters</li>
	 *             </ul>
	 * @return The player position
	 */
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
				if ((GameController.getGameMap().get(y, x).getEntity() != null)
						&& (GameController.getGameMap().get(y, x).getEntity() != this)
						&& !(GameController.getGameMap().get(y, x).getEntity() instanceof Player)) {
					continue;
				}
			}
			visit[y][x] = true;

			if (GameController.getGameMap().get(y, x).getEntity() instanceof Player) {
				return new Pair<>(y, x);
			}

			for (int i = 0; i < 4; i++) {
				if ((y + move[i][0] >= GameConfig.MAP_SIZE) || (x + move[i][1] >= GameConfig.MAP_SIZE)) {
					continue;
				}
				if (!visit[y + move[i][0]][x + move[i][1]]) {
					parent[y + move[i][0]][x + move[i][1]] = new Pair<>(y, x);
					queue.add(new Pair<>(length + 1, new Pair<>(y + move[i][0], x + move[i][1])));
				}
			}
		}
		return null;
	}

}
