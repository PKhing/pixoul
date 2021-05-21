package utils;

import java.net.URL;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import scene.GameScene;
import scene.LandingScene;

/**
 * The GameAudioUtils class is the class that provide the {@link MediaPlayer}
 * for audio and update volume method
 */
public class GameAudioUtils {

	/**
	 * The {@link GameScene} background music
	 */
	private static MediaPlayer gameSceneBGM;

	/**
	 * The {@link LandingScene} background music
	 */
	private static MediaPlayer landingSceneBGM;

	/**
	 * The open inventory sound effect
	 */
	private static AudioClip openInventorySFX;

	/**
	 * The close inventory sound effect
	 */
	private static AudioClip closeInventorySFX;

	/**
	 * The attack sound effect
	 */
	private static AudioClip attackSFX;

	/**
	 * Load music audio
	 */
	static {
		gameSceneBGM = loadAudioLoop("bgm/BGMDungeon.mp3");
		landingSceneBGM = loadAudioLoop("bgm/BGMMainScene.mp3");
		openInventorySFX = loadSFX("sfx/open_inventory.mp3");
		closeInventorySFX = loadSFX("sfx/close_inventory.mp3");
		attackSFX = loadSFX("sfx/attack.mp3");

		updateBGMVolume();
		updateEffectVolume();
	}

	/**
	 * Load audio by file path and make it loop when play
	 * 
	 * @param filePath the path of audio file
	 * @return {@link MediaPlayer} instance
	 */
	private static MediaPlayer loadAudioLoop(String filePath) {
		URL resource = ClassLoader.getSystemResource(filePath);
		MediaPlayer player = new MediaPlayer(new Media(resource.toString()));

		player.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				player.seek(Duration.ZERO);
			}
		});

		return player;
	}

	/**
	 * Load sound effect by file path
	 * 
	 * @param filePath the path of audio file
	 * @return {@link AudioClip} instance
	 */
	private static AudioClip loadSFX(String filePath) {
		URL resource = ClassLoader.getSystemResource(filePath);
		AudioClip player = new AudioClip(resource.toExternalForm());

		return player;
	}

	/**
	 * Update all background music volume to current setting
	 */
	public static void updateBGMVolume() {
		gameSceneBGM.setVolume(GameConfig.getBgmVolume());
		landingSceneBGM.setVolume(GameConfig.getBgmVolume());
	}

	/**
	 * Update all effect volume to current setting
	 */
	public static void updateEffectVolume() {
		openInventorySFX.setVolume(GameConfig.getEffectVolume());
		closeInventorySFX.setVolume(GameConfig.getEffectVolume());
		attackSFX.setVolume(GameConfig.getEffectVolume());
	}

	/**
	 * Getter for {@link #gameSceneBGM}
	 * 
	 * @return {@link #gameSceneBGM}
	 */
	public static MediaPlayer getGameSceneBGM() {
		return gameSceneBGM;
	}

	/**
	 * Setter for {@link #gameSceneBGM}
	 * 
	 * @param gameSceneBGM new instance of {@link #gameSceneBGM}
	 */
	public static void setGameSceneBGM(MediaPlayer gameSceneBGM) {
		GameAudioUtils.gameSceneBGM = gameSceneBGM;
	}

	/**
	 * Getter for {@link #landingSceneBGM}
	 * 
	 * @return {@link #landingSceneBGM}
	 */
	public static MediaPlayer getLandingSceneBGM() {
		return landingSceneBGM;
	}

	/**
	 * Setter for {@link #landingSceneBGM}
	 * 
	 * @param landingSceneBGM new instance of {@link #landingSceneBGM}
	 */
	public static void setLandingSceneBGM(MediaPlayer landingSceneBGM) {
		GameAudioUtils.landingSceneBGM = landingSceneBGM;
	}

	/**
	 * Getter for {@link #openInventorySFX}
	 * 
	 * @return {@link #openInventorySFX}
	 */
	public static AudioClip getOpenInventorySFX() {
		return openInventorySFX;
	}

	/**
	 * Setter for {@link #openInventorySFX}
	 * 
	 * @param openInventorySFX the new {@link #openInventorySFX}
	 */
	public static void setOpenInventorySFX(AudioClip openInventorySFX) {
		GameAudioUtils.openInventorySFX = openInventorySFX;
	}

	/**
	 * Getter for {@link #closeInventorySFX}
	 * 
	 * @return {@link #closeInventorySFX}
	 */
	public static AudioClip getCloseInventorySFX() {
		return closeInventorySFX;
	}

	/**
	 * Setter for {@link #closeInventorySFX}
	 * 
	 * @param closeInventorySFX the new {@link #closeInventorySFX}
	 */
	public static void setCloseInventorySFX(AudioClip closeInventorySFX) {
		GameAudioUtils.closeInventorySFX = closeInventorySFX;
	}

	/**
	 * Getter for {@link #attackSFX}
	 * 
	 * @return {@link #attackSFX}
	 */
	public static AudioClip getAttackSFX() {
		return attackSFX;
	}

	/**
	 * Setter for {@link #attackSFX}
	 * 
	 * @param attackSFX the new {@link #attackSFX}
	 */
	public static void setAttackSFX(AudioClip attackSFX) {
		GameAudioUtils.attackSFX = attackSFX;
	}
}
