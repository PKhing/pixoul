package utils;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class GameAudioUtils {
	public static MediaPlayer GameSceneBGM;
	public static MediaPlayer LandingSceneBGM;

	static {
		GameSceneBGM = getAudioLoop("bgm/BGMDungeon.mp3", GameConfig.getVolume());
		LandingSceneBGM = getAudioLoop("bgm/BGMMainScene.mp3", GameConfig.getVolume());
	}
	
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

	public static void updateBGMVolume() {
		GameSceneBGM.setVolume(GameConfig.getVolume());
		LandingSceneBGM.setVolume(GameConfig.getVolume());
	}
}
