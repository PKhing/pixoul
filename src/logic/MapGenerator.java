package logic;

import java.util.LinkedList;
import java.util.Queue;
import javafx.util.Pair;
import utils.GameConfig;
import utils.RandomUtil;

public class MapGenerator {

	private static final int MAX_ROOM = 10;
	private static final int MAX_PATH = 25;
	private static final int MAX_LENGTH = 15;
	private static final int MIN_LENGTH = 4;
	private static final int PATH = -1;
	private static final int ROOM = -2;
	private static final int PROCESSING = -3;
	private static final int STRAIGHT = 1;
	private static final int TURN_LEFT = 2;
	private static final int TURN_RIGHT = 3;

	private static int map[][];

	static class State {
		private int y, x, direction;

		public State(int y, int x, int direction) {
			this.y = y;
			this.x = x;
			this.direction = direction;
		}

		public State newState() {
			return new State(y, x, direction);
		}

		public boolean isValid() {
			if (y <= 0 || x <= 0 || y >= GameConfig.MAP_SIZE || x >= GameConfig.MAP_SIZE)
				return true;
			if (isConnectTo(PROCESSING))
				return false;
			return true;
		}

		public boolean isConnectTo(int txpe) {
			State f = this.newState();
			State l = this.newState();
			State r = this.newState();
			f.move();
			l.turnLeft();
			l.move();
			r.turnRight();
			r.move();
			if (f.getType() == txpe || l.getType() == txpe || r.getType() == txpe)
				return true;
			return false;
		}

		public void move() {
			if (direction == 0)
				y--;
			else if (direction == 1)
				x++;
			else if (direction == 2)
				y++;
			else
				x--;
		}

		public void turnLeft() {
			direction--;
			if (direction == -1)
				direction = 3;
		}

		public void turnRight() {
			direction++;
			if (direction == 4)
				direction = 0;
		}

		public void doAction(int action) {
			if (action == TURN_LEFT) {
				this.turnLeft();
			}
			if (action == TURN_RIGHT) {
				this.turnRight();
			}
			this.move();
		}

		public int getType() {
			if (y <= 0 || x <= 0 || y >= GameConfig.MAP_SIZE || x >= GameConfig.MAP_SIZE)
				return -1000;
			return map[y][x];
		}

		public void setType(int type) {
			map[y][x] = type;
		}
	}

	private static boolean makeRoom(int y, int x, int no) {

		// check if room is valid or not
		if (y - GameConfig.ROOM_SIZE <= 0 || y + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE
				|| x - GameConfig.ROOM_SIZE <= 0 || x + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE)
			return false;
		for (int i = y - GameConfig.ROOM_SIZE; i <= y + GameConfig.ROOM_SIZE; i++) {
			for (int j = x - GameConfig.ROOM_SIZE; j <= x + GameConfig.ROOM_SIZE; j++) {
				if (map[i][j] != 0)
					return false;
			}
		}

		// make room
		for (int i = y - GameConfig.ROOM_SIZE; i <= y + GameConfig.ROOM_SIZE; i++) {
			for (int j = x - GameConfig.ROOM_SIZE; j <= x + GameConfig.ROOM_SIZE; j++) {
				if (j != x - GameConfig.ROOM_SIZE && j != x + GameConfig.ROOM_SIZE && i != y - GameConfig.ROOM_SIZE
						&& i != y + GameConfig.ROOM_SIZE)
					map[i][j] = ROOM;
				if (j != x - GameConfig.ROOM_SIZE && j != x + GameConfig.ROOM_SIZE) {
					map[y - GameConfig.ROOM_SIZE][j] = no;
					map[y + GameConfig.ROOM_SIZE][j] = no;
				}
			}
			if (i != y - GameConfig.ROOM_SIZE && i != y + GameConfig.ROOM_SIZE) {
				map[i][x - GameConfig.ROOM_SIZE] = no;
				map[i][x + GameConfig.ROOM_SIZE] = no;
			}
		}
		return true;
	}

	private static boolean makePath(State state, int startRoom, int length) {
		if (length >= MAX_LENGTH)
			return false;
		if (!state.isValid() || state.getType() == startRoom || state.getType() <= ROOM)
			return false;
		if (state.getType() > 0 || state.isConnectTo(PATH) || state.getType() == PATH) {
			if (length < MIN_LENGTH)
				return false;
			state.setType(PATH);
			return true;
		}

		state.setType(PROCESSING);
		Integer actionType[] = { STRAIGHT, TURN_LEFT, TURN_RIGHT };
		RandomUtil.shuffle(actionType);

		for (int i = 0; i < 3; i++) {
			State newState = state.newState();
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
				if (map[i][j] == ROOM || map[i][j] == PATH) {
					gameMap[i][j].setType(Cell.PATH);
				}
			}
		}

		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				int pathCount = 0;
				for (int k = i - 1; k <= i + 1; k++) {
					for (int l = j - 1; l <= j + 1; l++) {
						if (k < 0 || l < 0 || k > GameConfig.MAP_SIZE || l > GameConfig.MAP_SIZE)
							continue;
						if (gameMap[k][l].getType() == Cell.PATH)
							pathCount += 1;
					}
				}
				if (pathCount > 0 && gameMap[i][j].getType() != Cell.PATH)
					gameMap[i][j].setType(Cell.WALL);
			}
		}
	}

	private static boolean validate(GameMap gameMap) {
		boolean[][] visit = new boolean[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];

		int move[][] = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
		queue.add(gameMap.getRoomList().get(0));

		while (!queue.isEmpty()) {

			int y = queue.peek().getKey();
			int x = queue.peek().getValue();

			queue.remove();

			if (gameMap.get(y, x).getType() != Cell.PATH || visit[y][x]) {
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

	public static GameMap generateMap() {
		GameMap gameMap;
		do {
			gameMap = new GameMap();
			map = new int[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];
			int roomCnt = 1;
			while (roomCnt <= MAX_ROOM) {
				int y = RandomUtil.random(0, GameConfig.MAP_SIZE);
				int x = RandomUtil.random(0, GameConfig.MAP_SIZE);
				if (makeRoom(y, x, roomCnt)) {
					gameMap.getRoomList().add(new Pair<>(y, x));
					roomCnt++;
				}
			}
			int pathCnt = 1;
			while (pathCnt <= MAX_PATH) {
				int y = RandomUtil.random(0, GameConfig.MAP_SIZE);
				int x = RandomUtil.random(0, GameConfig.MAP_SIZE);
				while (map[y][x] < 1) {
					y = RandomUtil.random(0, GameConfig.MAP_SIZE);
					x = RandomUtil.random(0, GameConfig.MAP_SIZE);
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
		} while (!validate(gameMap));
		
		Pair<Integer, Integer> posLadderUp = gameMap.getRoomList().get(0);
		Pair<Integer, Integer> posLadderDown = gameMap.getRoomList().get(gameMap.getRoomList().size() - 1);
		
		gameMap.getGameMap()[posLadderUp.getKey()][posLadderUp.getValue()].setType(Cell.LADDER_UP);
		gameMap.getGameMap()[posLadderDown.getKey()][posLadderDown.getValue()].setType(Cell.LADDER_DOWN);
		
		return gameMap;
	}

}
