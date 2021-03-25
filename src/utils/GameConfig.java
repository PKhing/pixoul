package utils;

public class GameConfig {
	private static int screenWidth = 854;
	private static int screenHeight = 400;

	public static final double SCREEN_SCALING = 0.7;
	public static final int MAP_SIZE = 50;
	public static final int ROOM_SIZE = 4;
	public static final int SPRITE_SIZE = 45;

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int screenWidth) {
		GameConfig.screenWidth = screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		GameConfig.screenHeight = screenHeight;
	}
}
