package utils;

import java.net.URL;

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

	/**
	 * Load music audio
	 */
	static {
		GameSceneBGM = getAudioLoop("bgm/BGMDungeon.mp3", GameConfig.getVolume());
		LandingSceneBGM = getAudioLoop("bgm/BGMMainScene.mp3", GameConfig.getVolume());
	}

	/**
	 * Load audio by file path and make it loop when play
	 * 
	 * @param filePath the path of audio file
	 * @param volumes  initial volume of audio
	 * @return {@link MediaPlayer} instance
	 */
	private static MediaPlayer getAudioLoop(String filePath, double volumes) {
		URL resource = ClassLoader.getSystemResource(filePath);
		MediaPlayer player = new MediaPlayer(new Media(resource.toString()));

		player.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				player.seek(Duration.ZERO);
			}
		});

		player.setVolume(volumes);
		return player;
	}

	/**
	 * Update all background volume to current setting
	 */
	public static void updateBGMVolume() {
		GameSceneBGM.setVolume(GameConfig.getVolume());
		LandingSceneBGM.setVolume(GameConfig.getVolume());
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
	
	
}
