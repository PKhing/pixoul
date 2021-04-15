package logic;

public class Direction {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public static int getMoveX(int direction, int step) {
		if (direction == Direction.LEFT) {
			return -step;
		}
		if (direction == Direction.RIGHT) {
			return step;
		}
		return 0;
	}

	public static int getMoveY(int direction, int step) {
		if (direction == Direction.UP) {
			return -step;
		}
		if (direction == Direction.DOWN) {
			return step;
		}
		return 0;
	}

	public static int getSpriteIndex(int direction) {
		if (direction == Direction.LEFT) {
			return 1;
		}
		if (direction == Direction.RIGHT) {
			return 2;
		}
		if (direction == Direction.UP) {
			return 3;
		}
		return 0;
	}
}
