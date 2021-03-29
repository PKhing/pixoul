package components;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class GameAudio {
	public static MediaPlayer getGameSceneAudioLoop() {
		URL resource = ClassLoader.getSystemResource("bgm/BGMDungeon.mp3");
		MediaPlayer player = new MediaPlayer(new Media(resource.toString()));
		
		player.setOnEndOfMedia(new Runnable() {
			
			@Override
			public void run() {
				player.seek(Duration.ZERO);
			}
		});

		player.setVolume(0.1);
		return player;
	}
}
