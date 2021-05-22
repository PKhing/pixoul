package utils;

import items.base.Item;
import logic.GameMap;
import scene.GameScene;

/**
 * The GameConfig class is the class that provide the variable which involved
 * with game directly such as
 * 
 * <ul>
 * <li>Music volume</li>
 * <li>Screen Width and Height</li>
 * <li>GameMap size</li>
 * </ul>
 *
 */
public class GameConfig {

	/**
	 * Represent the default width of scene
	 */
	private static int screenWidth = 854;

	/**
	 * Represent the default height of scene
	 */
	private static int screenHeight = 400;

	/**
	 * Represent the scale factor of sprite, components and font which it depends on
	 * window width and height
	 */
	private static int scale = 2;

	/**
	 * Represent the volume of music audio
	 */
	private static double bgmVolume = 0.1;

	/**
	 * Represent the volume of effect
	 */
	private static double effectVolume = 0.4;

	/**
	 * Represent that the game system will ignore moving animation or not
	 */
	private static boolean isSkipMoveAnimation = false;
	
	/**
	 * Represent the lower bound of window width that it need to scale scene up to
	 * higher width and height
	 */
	public static final int SCREEN_RESPONSIVE = 864;

	/**
	 * Represent that the stage is scalable or not
	 */
	public static final boolean STAGE_SCALABLE = false;

	/**
	 * Represent the name of game which used for show in program
	 */
	public static final String GAME_TITLE = "Pixoul";

	/**
	 * Represent the name of game icon file
	 */
	public static final String ICON_NAME = "icon.png";

	/**
	 * Represent the scale factor of scene so that it do not equal to window height
	 * or width
	 */
	public static final double SCREEN_SCALING = 0.8;

	/**
	 * Represent the size of {@link GameMap}
	 */
	public static final int MAP_SIZE = 50;

	/**
	 * Represent the size of each room in {@link GameMap}
	 */
	public static final int ROOM_SIZE = 4;

	/**
	 * Represent the default size of sprite
	 */
	public static final int SPRITE_SIZE = 32;

	/**
	 * Represent the maximum amount of {@link Item} that generated in each level
	 */
	public static final int MAX_ITEM = 16;

	/**
	 * Represent the render distance of {@link GameScene}
	 */
	public static final int LINE_OF_SIGHT = 3;

	/**
	 * Represent the monster field of view
	 */
	public static final int MONSTER_LINE_OF_SIGHT = 3;
	
	/**
	 * Represent the maximum level that player can reach when disable endless mode
	 */
	public static final int LEVEL_BOUND = 11;


	/**
	 * Getter for {@link #screenWidth}
	 * 
	 * @return {@link #screenWidth}
	 */
	public static int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Setter for {@link #screenWidth}
	 * 
	 * @param screenWidth the new {@link #screenWidth}
	 */
	public static void setScreenWidth(int screenWidth) {
		GameConfig.screenWidth = screenWidth;
	}

	/**
	 * Getter for {@link #screenHeight}
	 * 
	 * @return {@link #screenHeight}
	 */
	public static int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Setter for {@link #screenHeight}
	 * 
	 * @param screenHeight the new {@link #screenHeight}
	 */
	public static void setScreenHeight(int screenHeight) {
		GameConfig.screenHeight = screenHeight;
	}

	/**
	 * Getter for {@link #scale}
	 * 
	 * @return {@link #scale}
	 */
	public static int getScale() {
		return scale;
	}

	/**
	 * Setter for {@link #scale}
	 * 
	 * @param scale the new {@link #scale}
	 */
	public static void setScale(int scale) {
		GameConfig.scale = scale;
	}

	/**
	 * Getter for {@link #bgmVolume}
	 * 
	 * @return {@link #bgmVolume}
	 */
	public static double getBgmVolume() {
		return bgmVolume;
	}

	/**
	 * Setter for {@link #bgmVolume}
	 * 
	 * @param bgmVolume the new {@link #bgmVolume}
	 */
	public static void setBgmVolume(double bgmVolume) {
		GameConfig.bgmVolume = bgmVolume;
	}

	/**
	 * Getter for {@link #isSkipMoveAnimation}
	 * 
	 * @return {@link #isSkipMoveAnimation}
	 */
	public static boolean isSkipMoveAnimation() {
		return isSkipMoveAnimation;
	}

	/**
	 * Setter for {@link #isSkipMoveAnimation}
	 * 
	 * @param isSkipMoveAnimation the new {@link #isSkipMoveAnimation}
	 */
	public static void setSkipMoveAnimation(boolean isSkipMoveAnimation) {
		GameConfig.isSkipMoveAnimation = isSkipMoveAnimation;
	}

	/**
	 * Getter for {@link #effectVolume}
	 * 
	 * @return {@link #effectVolume}
	 */
	public static double getEffectVolume() {
		return effectVolume;
	}

	/**
	 * Setter for {@link #effectVolume}
	 * 
	 * @param effectVolume the new {@link #effectVolume}
	 */
	public static void setEffectVolume(double effectVolume) {
		GameConfig.effectVolume = effectVolume;
	}
}
