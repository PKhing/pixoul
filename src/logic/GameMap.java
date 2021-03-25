package logic;

import java.util.ArrayList;
import java.util.Random;

import javafx.util.Pair;
import utils.GameConfig;
import utils.Util;

public class GameMap {
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
	private int map[][];
	private Cell gameMap[][];
	private ArrayList<Pair<Integer, Integer>> roomList;

	public GameMap() {
		map = new int[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];
		gameMap = new Cell[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];
		roomList = new ArrayList<Pair<Integer, Integer>>();
		generateMap();
	}

	class State {
		private int x, y, direction;

		public State(int x, int y, int direction) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}

		public State newState() {
			return new State(x, y, direction);
		}

		public boolean isValid() {
			if (x <= 0 || y <= 0 || x >= GameConfig.MAP_SIZE || y >= GameConfig.MAP_SIZE)
				return true;
			if (isConnectTo(PROCESSING))
				return false;
			return true;
		}

		public boolean isConnectTo(int type) {
			State f = this.newState();
			State l = this.newState();
			State r = this.newState();
			f.move();
			l.turnLeft();
			l.move();
			r.turnRight();
			r.move();
			if (f.getType() == type || l.getType() == type || r.getType() == type)
				return true;
			return false;
		}

		public void move() {
			if (direction == 0)
				x--;
			else if (direction == 1)
				y++;
			else if (direction == 2)
				x++;
			else
				y--;
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
			if (x <= 0 || y <= 0 || x >= GameConfig.MAP_SIZE || y >= GameConfig.MAP_SIZE)
				return -1000;
			return map[x][y];
		}

		public void setType(int type) {
			map[x][y] = type;
		}
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

	private boolean makeRoom(int x, int y, int no) {

		// check if room is valid or not
		if (x - GameConfig.ROOM_SIZE <= 0 || x + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE
				|| y - GameConfig.ROOM_SIZE <= 0 || y + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE)
			return false;
		for (int i = x - GameConfig.ROOM_SIZE; i <= x + GameConfig.ROOM_SIZE; i++) {
			for (int j = y - GameConfig.ROOM_SIZE; j <= y + GameConfig.ROOM_SIZE; j++) {
				if (map[i][j] != 0)
					return false;
			}
		}

		// make room
		for (int i = x - GameConfig.ROOM_SIZE; i <= x + GameConfig.ROOM_SIZE; i++) {
			for (int j = y - GameConfig.ROOM_SIZE; j <= y + GameConfig.ROOM_SIZE; j++) {
				if (j != y - GameConfig.ROOM_SIZE && j != y + GameConfig.ROOM_SIZE
						&& i != x - GameConfig.ROOM_SIZE && i != x + GameConfig.ROOM_SIZE)
					map[i][j] = ROOM;
				if (j != y - GameConfig.ROOM_SIZE && j != y + GameConfig.ROOM_SIZE) {
					map[x - GameConfig.ROOM_SIZE][j] = no;
					map[x + GameConfig.ROOM_SIZE][j] = no;
				}
			}
			if (i != x - GameConfig.ROOM_SIZE && i != x + GameConfig.ROOM_SIZE) {
				map[i][y - GameConfig.ROOM_SIZE] = no;
				map[i][y + GameConfig.ROOM_SIZE] = no;
			}
		}
		return true;
	}

	private boolean makePath(State state, int startRoom, int length) {
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
		Util.shuffle(actionType);

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

	private void makeMap() {

		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				gameMap[i][j] = new Cell(Cell.VOID);
				if (map[i][j] == ROOM)
					gameMap[i][j].setType(Cell.PATH);
				if (map[i][j] == PATH)
					gameMap[i][j].setType(Cell.PATH);
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

	public void generateMap() {
		int roomCnt = 1;
		while (roomCnt <= MAX_ROOM) {
			int x = Util.random(0, GameConfig.MAP_SIZE);
			int y = Util.random(0, GameConfig.MAP_SIZE);
			if (makeRoom(x, y, roomCnt)) {
				roomList.add(new Pair<>(x, y));
				roomCnt++;
			}
		}
		int pathCnt = 1;
		while (pathCnt <= MAX_PATH) {
			int x = Util.random(0, GameConfig.MAP_SIZE);
			int y = Util.random(0, GameConfig.MAP_SIZE);
			while (map[x][y] < 1) {
				x = Util.random(0, GameConfig.MAP_SIZE);
				y = Util.random(0, GameConfig.MAP_SIZE);
			}
			State state = new State(x, y, Util.random(0, 3));
			int tmp = state.getType();
			state.setType(0);
			if (makePath(state, tmp, 0)) {
				pathCnt++;
				state.setType(PATH);
			} else {
				state.setType(tmp);
			}
		}
		makeMap();
	}

	public Cell get(int i, int j) {
		if (i < 0 || i > GameConfig.MAP_SIZE || j < 0 || j > GameConfig.MAP_SIZE)
			return new Cell();
		return gameMap[i][j];
	}

	public ArrayList<Pair<Integer, Integer>> getRoomList() {
		return roomList;
	}

}
