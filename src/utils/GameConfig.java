package utils;

public class GameConfig {
	private static int screenWidth = 854;
	private static int screenHeight = 400;
	private static int scale = 2;
	private static double volume = 0.1;
	private static boolean skipMoveAnimation = false;

	public static final int SCREEN_RESPONSIVE = 864;
	public static final boolean SCREEN_SCALABLE = false;
	public static final String GAME_TITLE = "Pixoul";
	public static final String ICON_NAME = "icon.png";
	public static final double SCREEN_SCALING = 0.8;

	public static final int MAP_SIZE = 50;
	public static final int ROOM_SIZE = 4;
	public static final int SPRITE_SIZE = 32;

	public static final int MAX_ITEM = 16;
	public static final int LINE_OF_SIGHT = 3;
	public static final int MONSTER_LINE_OF_SIGHT = 3;

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

	public static int getScale() {
		return scale;
	}

	public static void setScale(int scale) {
		GameConfig.scale = scale;
	}

	public static double getVolume() {
		return volume;
	}

	public static void setVolume(double volume) {
		GameConfig.volume = volume;
	}

	public static boolean isSkipMoveAnimation() {
		return skipMoveAnimation;
	}

	public static void setSkipMoveAnimation(boolean skipMoveAnimation) {
		GameConfig.skipMoveAnimation = skipMoveAnimation;
	}
}
