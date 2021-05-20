package utils;

import java.net.URL;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import scene.GameScene;

/**
 * The GameAudioUtils class is the class that provide the {@link MediaPlayer}
 * for audio and update volume method
 */
public class GameAudioUtils {

	/**
	 * Represent {@link GameScene} background music
	 */
	private static MediaPlayer GameSceneBGM;

	/**
	 * Represent {@link LandingScene} background music
	 */
	private static MediaPlayer LandingSceneBGM;

	private static AudioClip openInventorySFX;

	private static AudioClip closeInventorySFX;
	
	/**
	 * Load music audio
	 */
	static {
		GameSceneBGM = getAudioLoop("bgm/BGMDungeon.mp3");
		LandingSceneBGM = getAudioLoop("bgm/BGMMainScene.mp3");
		openInventorySFX = getNewSFX("sfx/open_inventory.mp3");
		closeInventorySFX = getNewSFX("sfx/close_inventory.mp3");
		
		updateBGMVolume();
	}

	/**
	 * Load audio by file path and make it loop when play
	 * 
	 * @param filePath the path of audio file
	 * @param volumes  initial volume of audio
	 * @return {@link MediaPlayer} instance
	 */
	private static MediaPlayer getAudioLoop(String filePath) {
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
	private static AudioClip getNewSFX(String filePath) {
		URL resource = ClassLoader.getSystemResource(filePath);
		AudioClip player = new AudioClip(resource.toExternalForm());
		
		return player;
	}


	/**
	 * Update all background volume to current setting
	 */
	public static void updateBGMVolume() {
		GameSceneBGM.setVolume(GameConfig.getVolume());
		LandingSceneBGM.setVolume(GameConfig.getVolume());
		openInventorySFX.setVolume(GameConfig.getVolume());
		closeInventorySFX.setVolume(GameConfig.getVolume());
	}

	/**
	 * Getter for {@link #GameSceneBGM}
	 * 
	 * @return {@link #GameSceneBGM}
	 */
	public static MediaPlayer getGameSceneBGM() {
		return GameSceneBGM;
	}

	/**
	 * Setter for {@link #GameSceneBGM}
	 * 
	 * @param gameSceneBGM new instance of {@link #GameSceneBGM}
	 */
	public static void setGameSceneBGM(MediaPlayer gameSceneBGM) {
		GameSceneBGM = gameSceneBGM;
	}

	/**
	 * Getter for {@link #LandingSceneBGM}
	 * 
	 * @return {@link #LandingSceneBGM}
	 */
	public static MediaPlayer getLandingSceneBGM() {
		return LandingSceneBGM;
	}

	/**
	 * Setter for {@link #LandingSceneBGM}
	 * 
	 * @param landingSceneBGM new instance of {@link #LandingSceneBGM}
	 */
	public static void setLandingSceneBGM(MediaPlayer landingSceneBGM) {
		LandingSceneBGM = landingSceneBGM;
	}
	
	public static AudioClip getOpenInventorySFX() {
		return openInventorySFX;
	}

	public static void setOpenInventorySFX(AudioClip openInventorySFX) {
		GameAudioUtils.openInventorySFX = openInventorySFX;
	}

	public static AudioClip getCloseInventorySFX() {
		return closeInventorySFX;
	}

	public static void setCloseInventorySFX(AudioClip closeInventorySFX) {
		GameAudioUtils.closeInventorySFX = closeInventorySFX;
	}
	
	
}
