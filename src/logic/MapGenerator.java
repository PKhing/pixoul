package logic;

import java.util.LinkedList;
import java.util.Queue;

import controller.GameController;
import entity.Player;
import javafx.util.Pair;
import utils.GameConfig;
import utils.RandomUtil;

/**
 * An instance of this class is used to generate {@link GameMap}.
 * 
 */
public class MapGenerator {

	/**
	 * A constant holding the maximum amount of room.
	 */
	private static final int MAX_ROOM = 10;
	/**
	 * A constant holding the maximum amount of path.
	 */
	private static final int MAX_PATH = 25;
	/**
	 * A constant holding the maximum length of path.
	 */
	private static final int MAX_LENGTH = 15;
	/**
	 * A constant holding the minimum length of path.
	 */
	private static final int MIN_LENGTH = 4;
	/**
	 * Representing path type
	 */
	private static final int PATH = -1;
	/**
	 * Representing room type
	 */
	private static final int ROOM = -2;
	/**
	 * Represents processing type.
	 */
	private static final int PROCESSING = -3;
	/**
	 * Represents walking straight action.
	 */
	private static final int STRAIGHT = 1;
	/**
	 * Represents turning left action.
	 */
	private static final int TURN_LEFT = 2;
	/**
	 * Represents turning right action.
	 */
	private static final int TURN_RIGHT = 3;
	/**
	 * Array of integer that represents type of each cell.
	 */
	private static int map[][];

	/**
	 * Generates new {@link GameMap}.
	 * 
	 * @return Randomly generated {@link GameMap}.
	 */
	public static GameMap generateMap() {
		GameMap gameMap = buildNewEmptyMap();
		gameMap.printMap();
		Pair<Integer, Integer> posLadderUp = gameMap.getRoomList().get(0);
		Pair<Integer, Integer> posLadderDown = gameMap.getRoomList().get(gameMap.getRoomList().size() - 1);

		gameMap.getGameMap()[posLadderUp.getKey()][posLadderUp.getValue()].setType(Cell.LADDER_UP);
		gameMap.getGameMap()[posLadderDown.getKey()][posLadderDown.getValue()].setType(Cell.LADDER_DOWN);

		generateItemOnMap(gameMap);
		generateMonsterOnMap(gameMap);

		return gameMap;
	}

	/**
	 * A class to represent position and direction. This class is used for path
	 * creating only.
	 *
	 */
	static class State {
		/**
		 * Position in the Y-axis of this state.
		 */
		private int y;
		/**
		 * Position in the X-axis of this state.
		 */
		private int x;
		/**
		 * Direction of this state.
		 * 
		 * @see Direction
		 */
		private int direction;

		/**
		 * Creates a new state.
		 * 
		 * @param x         position in Y-axis of this state.
		 * @param x         position in X-axis of this state.
		 * @param direction direction of this state.
		 */
		public State(int y, int x, int direction) {
			this.y = y;
			this.x = x;
			this.direction = direction;
		}

		/**
		 * Creates a copy of this state.
		 * 
		 * @return a copy of this state.
		 */
		public State Duplicate() {
			return new State(y, x, direction);
		}

		/**
		 * Checks if this state is valid to create a path or not.
		 * 
		 * @return true if this state is valid; false otherwise.
		 */
		public boolean isValid() {
			if ((y <= 0) || (x <= 0) || (y >= GameConfig.MAP_SIZE) || (x >= GameConfig.MAP_SIZE)) {
				return false;
			}
			if (isConnectTo(PROCESSING)) {
				return false;
			}
			return true;
		}

		/**
		 * Checks if cells in the front left or right have a specified type or not.
		 * 
		 * @param type the type to check.
		 * @return true if this state connects to a cell with a specified type; false
		 *         otherwise.
		 */
		public boolean isConnectTo(int type) {
			State f = this.Duplicate();
			State l = this.Duplicate();
			State r = this.Duplicate();
			f.move();
			l.turnLeft();
			l.move();
			r.turnRight();
			r.move();
			if ((f.getType() == type) || (l.getType() == type) || (r.getType() == type)) {
				return true;
			}
			return false;
		}

		/**
		 * Moves this state forward one cell.
		 */
		public void move() {
			if (direction == 0) {
				y--;
			} else if (direction == 1) {
				x++;
			} else if (direction == 2) {
				y++;
			} else {
				x--;
			}
		}

		/**
		 * Changes the direction of this state by turning left.
		 */
		public void turnLeft() {
			direction--;
			if (direction == -1) {
				direction = 3;
			}
		}

		/**
		 * Changes the direction of this state by turning right.
		 */
		public void turnRight() {
			direction++;
			if (direction == 4) {
				direction = 0;
			}
		}

		/**
		 * Does the specified action.
		 * 
		 * @param action the action to be done. It can be <code>STRAIGHT</code>,
		 *               <code>TURN_LEFT</code> or <code>TURN_RIGHT</code>.
		 */
		public void doAction(int action) {
			if (action == TURN_LEFT) {
				this.turnLeft();
			}
			if (action == TURN_RIGHT) {
				this.turnRight();
			}
			this.move();
		}

		/**
		 * Gets the type of cell that this state is on.
		 * 
		 * @return type of the cell
		 */
		public int getType() {
			if ((y <= 0) || (x <= 0) || (y >= GameConfig.MAP_SIZE) || (x >= GameConfig.MAP_SIZE))
				return -1000;
			return map[y][x];
		}

		/**
		 * Sets type of cell that this state is on.
		 * 
		 * @param type type to be set.
		 */
		public void setType(int type) {
			map[y][x] = type;
		}
	}

	/**
	 * Generates new empty map.
	 * 
	 * @return randomly created empty map.
	 */
	private static GameMap buildNewEmptyMap() {
		GameMap gameMap;
		do {
			gameMap = new GameMap();
			map = new int[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];

			// Generates rooms
			int roomCnt = 1;
			while (roomCnt <= MAX_ROOM) {
				int y = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
				int x = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
				if (makeRoom(y, x, roomCnt)) {
					gameMap.getRoomList().add(new Pair<>(y, x));
					roomCnt++;
				}
			}

			// Generates paths
			int pathCnt = 1;
			while (pathCnt <= MAX_PATH) {
				int y = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
				int x = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
				while (map[y][x] < 1) {
					y = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
					x = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
				}
				State state = new State(y, x, RandomUtil.random(0, 3));
				int tmp = state.getType();
				state.setType(0);
				if (makePath(state, tmp, 0)) {
					pathCnt++;
					state.setType(PATH);
				} else {
					state.setType(tmp);
				}
			}
			makeMap(gameMap.getGameMap());

			// If the map is not valid, generates again
		} while (!isValid(gameMap));

		return gameMap;
	}

	private static boolean makeRoom(int y, int x, int no) {

		// check if room is valid or not
		if ((y - GameConfig.ROOM_SIZE <= 0) || (y + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE)
				|| (x - GameConfig.ROOM_SIZE <= 0) || (x + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE))
			return false;
		for (int i = y - GameConfig.ROOM_SIZE; i <= y + GameConfig.ROOM_SIZE; i++) {
			for (int j = x - GameConfig.ROOM_SIZE; j <= x + GameConfig.ROOM_SIZE; j++) {
				if (map[i][j] != 0) {
					return false;
				}
			}
		}

		// make room
		for (int i = y - GameConfig.ROOM_SIZE; i <= y + GameConfig.ROOM_SIZE; i++) {
			for (int j = x - GameConfig.ROOM_SIZE; j <= x + GameConfig.ROOM_SIZE; j++) {
				if ((j != x - GameConfig.ROOM_SIZE) && (j != x + GameConfig.ROOM_SIZE)
						&& (i != y - GameConfig.ROOM_SIZE) && (i != y + GameConfig.ROOM_SIZE)) {
					map[i][j] = ROOM;
				}
				if ((j != x - GameConfig.ROOM_SIZE) && (j != x + GameConfig.ROOM_SIZE)) {
					map[y - GameConfig.ROOM_SIZE][j] = no;
					map[y + GameConfig.ROOM_SIZE][j] = no;
				}
			}
			if ((i != y - GameConfig.ROOM_SIZE) && (i != y + GameConfig.ROOM_SIZE)) {
				map[i][x - GameConfig.ROOM_SIZE] = no;
				map[i][x + GameConfig.ROOM_SIZE] = no;
			}
		}
		return true;
	}

	private static boolean makePath(State state, int startRoom, int length) {
		if (length >= MAX_LENGTH) {
			return false;
		}
		if (!state.isValid() || (state.getType() == startRoom) || (state.getType() <= ROOM)) {
			return false;
		}
		if ((state.getType() > 0) || state.isConnectTo(PATH) || (state.getType() == PATH)) {
			if (length < MIN_LENGTH) {
				return false;
			}
			state.setType(PATH);
			return true;
		}

		state.setType(PROCESSING);
		Integer actionType[] = { STRAIGHT, TURN_LEFT, TURN_RIGHT };
		RandomUtil.shuffle(actionType);

		for (int i = 0; i < 3; i++) {
			State newState = state.Duplicate();
			newState.doAction(actionType[i]);
			if (makePath(newState, startRoom, length + 1)) {
				state.setType(PATH);
				return true;
			}
		}
		state.setType(0);
		return false;
	}

	private static void makeMap(Cell[][] gameMap) {
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				gameMap[i][j] = new Cell(Cell.VOID);
				if ((map[i][j] == ROOM) || (map[i][j] == PATH)) {
					gameMap[i][j].setType(Cell.PATH);
				}
			}
		}

		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				int pathCount = 0;
				for (int k = i - 1; k <= i + 1; k++) {
					for (int l = j - 1; l <= j + 1; l++) {
						if ((k < 0) || (l < 0) || (k > GameConfig.MAP_SIZE) || (l > GameConfig.MAP_SIZE)) {
							continue;
						}
						if (gameMap[k][l].getType() == Cell.PATH) {
							pathCount += 1;
						}
					}
				}
				if ((pathCount > 0) && (gameMap[i][j].getType() != Cell.PATH)) {
					gameMap[i][j].setType(Cell.WALL);
				}
			}
		}
	}

	private static boolean isValid(GameMap gameMap) {
		boolean[][] visit = new boolean[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];

		int move[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
		queue.add(gameMap.getRoomList().get(0));

		while (!queue.isEmpty()) {

			int y = queue.peek().getKey();
			int x = queue.peek().getValue();

			queue.remove();

			if ((gameMap.get(y, x).getType() != Cell.PATH) || visit[y][x]) {
				continue;
			}
			visit[y][x] = true;

			for (int i = 0; i < 4; i++) {
				if (!visit[y + move[i][0]][x + move[i][1]]) {
					queue.add(new Pair<>(y + move[i][0], x + move[i][1]));
				}
			}
		}

		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				if (gameMap.get(i, j).getType() == Cell.PATH && !visit[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	private static void generateItemOnMap(GameMap gameMap) {
		// TODO Generate item on map
		Cell[][] cellMap = gameMap.getGameMap();
		int level = GameController.getLevel();

	}

	private static void generateMonsterOnMap(GameMap gameMap) {
		// TODO Generate monster on map
		Cell[][] cellMap = gameMap.getGameMap();
		Player player = GameController.getPlayer();

		int playerAtk = player.getAttack();
	}

}
