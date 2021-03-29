package utils;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class GameAudioUtils {
	public static MediaPlayer getGameSceneAudioLoop() {
		return getAudioLoop("bgm/BGMDungeon.mp3", 0.1);
	}
	
	public static MediaPlayer getLandingSceneAudio() {
		return getAudioLoop("bgm/BGMMainScene.mp3", 0.35);
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
}
