package logic;

import java.util.LinkedList;
import java.util.Queue;

import controller.GameController;
import entity.Player;
import javafx.util.Pair;
import utils.GameConfig;
import utils.RandomUtil;

/**
 * The MapGenerator class is used to generate {@link GameMap}.
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
	 * Represents void.
	 */
	private static final int VOID = 0;
	/**
	 * Represents path.
	 */
	private static final int PATH = -1;
	/**
	 * Represents room.
	 */
	private static final int ROOM = -2;
	/**
	 * Represents processing.
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
	 * The Array of integer that represents a type of each cell.
	 */
	private static int map[][];

	/**
	 * Generates new {@link GameMap}.
	 * 
	 * @return Randomly generated {@link GameMap}
	 */
	public static GameMap generateMap() {
		GameMap gameMap = buildNewEmptyMap();
		// Generates ladder
		Pair<Integer, Integer> posLadderUp = gameMap.getRoomList().get(0);
		Pair<Integer, Integer> posLadderDown = gameMap.getRoomList().get(gameMap.getRoomList().size() - 1);

		gameMap.getGameMap()[posLadderUp.getKey()][posLadderUp.getValue()].setType(Cell.LADDER_UP);
		gameMap.getGameMap()[posLadderDown.getKey()][posLadderDown.getValue()].setType(Cell.LADDER_DOWN);

		// Generates items and monsters
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
		 * The {@link Direction} of this state.
		 */
		private int direction;

		/**
		 * Creates a new state.
		 * 
		 * @param x         The position in the Y-axis of this state
		 * @param x         The position in the X-axis of this state
		 * @param direction The direction of this state
		 */
		public State(int y, int x, int direction) {
			this.y = y;
			this.x = x;
			this.direction = direction;
		}

		/**
		 * Creates a copy of this state.
		 * 
		 * @return A copy of this state
		 */
		public State Duplicate() {
			return new State(y, x, direction);
		}

		/**
		 * Checks if this state is valid to create a path or not.
		 * 
		 * @return True if this state is valid; false otherwise
		 */
		public boolean isValid() {
			if ((y <= 0) || (x <= 0) || (y >= GameConfig.MAP_SIZE) || (x >= GameConfig.MAP_SIZE)) {
				return false;
			}
			return true;
		}

		/**
		 * Checks if cells in the front left or right have a specified type or not.
		 * 
		 * @param type The type to check with
		 * @return True if this state connects to a cell with a specified type; false
		 *         otherwise
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
			if ((f.getCellType() == type) || (l.getCellType() == type) || (r.getCellType() == type)) {
				return true;
			}
			return false;
		}

		/**
		 * Moves this state forward one cell.
		 */
		public void move() {
			if (direction == Direction.UP) {
				y--;
			} else if (direction == Direction.RIGHT) {
				x++;
			} else if (direction == Direction.DOWN) {
				y++;
			} else if (direction == Direction.LEFT) {
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
		 * @param action The action to be done. It can be <code>STRAIGHT</code>,
		 *               <code>TURN_LEFT</code> or <code>TURN_RIGHT</code>
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
		 * @return Type of the cell
		 */
		public int getCellType() {
			if (!this.isValid()) {
				return -1000;
			}
			return map[y][x];
		}

		/**
		 * Sets type of cell that this state is on.
		 * 
		 * @param type The type to be set
		 */
		public void setCellType(int type) {
			map[y][x] = type;
		}
	}

	/**
	 * Generates new empty map.
	 * 
	 * @return Randomly generated empty map
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
				int x, y;
				do {
					y = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);
					x = RandomUtil.random(1, GameConfig.MAP_SIZE - 1);

					// Random until the cell type is room's wall
				} while (map[y][x] < 1);

				State state = new State(y, x, RandomUtil.random(0, 3));
				int tmp = state.getCellType();

				// Changes cell type from room's wall to void
				state.setCellType(VOID);
				if (makePath(state, tmp, 0)) {

					// If path generated successfully, changes cell type to PATH
					state.setCellType(PATH);
					pathCnt++;
				} else {
					// otherwise changes cell type back to room's wall
					state.setCellType(tmp);
				}
			}
			makeMap(gameMap.getGameMap());

			// If the map is not valid, generates again
		} while (!isValid(gameMap));

		return gameMap;
	}

	/**
	 * Generates room at the specified position.
	 * 
	 * @param y      The position of the room in the Y-axis
	 * @param x      The position of the room in the X-axis
	 * @param number The room number
	 * @return True if room is generated successfully; false otherwise
	 */
	private static boolean makeRoom(int y, int x, int number) {

		int startY = y - GameConfig.ROOM_SIZE;
		int endY = y + GameConfig.ROOM_SIZE;
		int startX = x - GameConfig.ROOM_SIZE;
		int endX = x + GameConfig.ROOM_SIZE;

		// Checks if the room is valid or not
		if ((startY <= 0) || (endY >= GameConfig.MAP_SIZE) || (startX <= 0) || (endX >= GameConfig.MAP_SIZE))
			return false;
		for (int i = startY; i <= endY; i++) {
			for (int j = startX; j <= endX; j++) {
				if (map[i][j] != VOID) {
					return false;
				}
			}
		}

		// Creates room
		for (int i = startY; i <= endY; i++) {
			for (int j = startX; j <= endX; j++) {
				if ((j != startX) && (j != endX) && (i != startY) && (i != endY)) {
					map[i][j] = ROOM;
				} else {
					map[i][j] = number;
				}
			}
		}

		// Changes corner cell type to void
		map[startY][startX] = VOID;
		map[startY][endX] = VOID;
		map[endY][startX] = VOID;
		map[endY][endX] = VOID;

		return true;
	}

	/**
	 * Generates path at the specified position.
	 * 
	 * @param state     The state of the path to be created
	 * @param startRoom The number of the room that this path starts
	 * @param length    Current path length
	 * @return True if path is generated successfully; false otherwise
	 */
	private static boolean makePath(State state, int startRoom, int length) {

		// Path is too long or the path have self loop
		if (length >= MAX_LENGTH || state.isConnectTo(PROCESSING) || (state.getCellType() == startRoom)) {
			return false;
		}

		// The state is not valid or cell type is PROCESSING or ROOM
		if (!state.isValid() || (state.getCellType() == PROCESSING) || (state.getCellType() == ROOM)) {
			return false;
		}

		// Cell type is room's wall or PATH or this path connect to other path
		if ((state.getCellType() > 0) || (state.getCellType() == PATH) || state.isConnectTo(PATH)) {

			// Path is too short
			if (length < MIN_LENGTH) {
				return false;
			}

			// Success!
			state.setCellType(PATH);
			return true;
		}

		state.setCellType(PROCESSING);

		// Does next action in random order

		Integer actionType[] = { STRAIGHT, TURN_LEFT, TURN_RIGHT };
		RandomUtil.shuffle(actionType);
		for (int i = 0; i < 3; i++) {
			State newState = state.Duplicate();
			newState.doAction(actionType[i]);
			if (makePath(newState, startRoom, length + 1)) {
				state.setCellType(PATH);
				return true;
			}
		}
		state.setCellType(VOID);
		return false;
	}

	/**
	 * Creates {@link Cell} array from array of cell type
	 * 
	 * @param gameMap The array of {@link Cell} to store the result in
	 */
	private static void makeMap(Cell[][] gameMap) {

		// Sets PATH
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				gameMap[i][j] = new Cell(Cell.VOID);
				if ((map[i][j] == ROOM) || (map[i][j] == PATH)) {
					gameMap[i][j].setType(Cell.PATH);
				}
			}
		}

		// Sets WALL
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

	/**
	 * Checks if the map is valid or not by checking whether the map is connected.
	 * 
	 * @param gameMap The map to be checked
	 * @return True if the map is valid; false otherwise
	 */
	private static boolean isValid(GameMap gameMap) {
		boolean[][] visit = new boolean[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];

		int move[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

		// Flood fill
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
