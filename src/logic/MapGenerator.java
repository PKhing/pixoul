package logic;

import java.util.ArrayList;
import java.util.Random;

import javafx.util.Pair;
import utils.GameConfig;
import utils.Util;

public class MapGenerator {
	private static final int MAX_ROOM = 10;
	private static final int MAX_PATH = 20;
	private static final int MAX_LENGTH = 15;
	private static final int MIN_LENGTH = 4;
	private static final int PATH = -1;
	private static final int ROOM = -2;
	private static final int PROCESSING = -3;
	private static final int STRAIGHT = 1;
	private static final int TURN_LEFT = 2;
	private static final int TURN_RIGHT = 3;
	private int gameMap[][];
	private ArrayList<Pair<Integer, Integer>> roomList;
	
	public MapGenerator() {
		gameMap = new int[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];
		roomList = new ArrayList<Pair<Integer, Integer>>();
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
			return gameMap[x][y];
		}

		public void setType(int type) {
			gameMap[x][y] = type;
		}
	}

	public void printMap() {
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				if (gameMap[i][j] == Cell.PATH)
					System.out.print(" ");
				else if (gameMap[i][j] == Cell.WALL)
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
		if (x - GameConfig.ROOM_SIZE <= 0 || x + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE || y - GameConfig.ROOM_SIZE <= 0 || y + GameConfig.ROOM_SIZE >= GameConfig.MAP_SIZE)
			return false;
		for (int i = x - GameConfig.ROOM_SIZE; i <= x + GameConfig.ROOM_SIZE; i++) {
			for (int j = y - GameConfig.ROOM_SIZE; j <= y + GameConfig.ROOM_SIZE; j++) {
				if (gameMap[i][j] != 0)
					return false;
			}
		}

		// make room
		for (int i = x - GameConfig.ROOM_SIZE; i <= x + GameConfig.ROOM_SIZE; i++) {
			for (int j = y - GameConfig.ROOM_SIZE; j <= y + GameConfig.ROOM_SIZE; j++) {
				if (j != y - GameConfig.ROOM_SIZE && j != y + GameConfig.ROOM_SIZE && i != x - GameConfig.ROOM_SIZE && i != x + GameConfig.ROOM_SIZE)
					gameMap[i][j] = ROOM;
				if (j != y - GameConfig.ROOM_SIZE && j != y + GameConfig.ROOM_SIZE) {
					gameMap[x - GameConfig.ROOM_SIZE][j] = no;
					gameMap[x + GameConfig.ROOM_SIZE][j] = no;
				}
			}
			if (i != x - GameConfig.ROOM_SIZE && i != x + GameConfig.ROOM_SIZE) {
				gameMap[i][y - GameConfig.ROOM_SIZE] = no;
				gameMap[i][y + GameConfig.ROOM_SIZE] = no;
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

	private void format() {

		int newMap[][] = new int[GameConfig.MAP_SIZE + 10][GameConfig.MAP_SIZE + 10];
		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				newMap[i][j] = Cell.VOID;
				if (gameMap[i][j] == ROOM)
					newMap[i][j] = Cell.PATH;
				if (gameMap[i][j] == PATH)
					newMap[i][j] = Cell.PATH;
			}
		}

		for (int i = 0; i <= GameConfig.MAP_SIZE; i++) {
			for (int j = 0; j <= GameConfig.MAP_SIZE; j++) {
				int pathCount = 0;
				for (int k = i - 1; k <= i + 1; k++) {
					for (int l = j - 1; l <= j + 1; l++) {
						if (k < 0 || l < 0 || k > GameConfig.MAP_SIZE || l > GameConfig.MAP_SIZE)
							continue;
						if (newMap[k][l] == Cell.PATH)
							pathCount += 1;
					}
				}
				if (pathCount > 0 && newMap[i][j] != Cell.PATH)
					newMap[i][j] = Cell.WALL;
			}
		}
		this.gameMap = newMap;
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
			while (gameMap[x][y] < 1) {
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
		format();
	}

	public int[][] getMap() {
		return gameMap;
	}

	public ArrayList<Pair<Integer, Integer>> getRoomList() {
		return roomList;
	}

}
