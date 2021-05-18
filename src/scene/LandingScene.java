package scene;

import components.LandingPane;
import controller.SceneController;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;

public class LandingScene {

	/**
	 * Represent the cached scene so that it does not need to have a multiple
	 * initialize
	 */
	private static Scene cachedScene = null;

	/**
	 * Represent the background music of main page / landing page
	 */
	private static MediaPlayer bgm = LandingPane.getBgm();

	/**
	 * Get the {@link #cachedScene landingScene}
	 * 
	 * @return the {@link Scene} which used for display in first page
	 */
	public static Scene getScene() {
		bgm.play();
		
		// If it have already cached then return the cache
		if (cachedScene != null) {
			return cachedScene;
		}

		LandingPane newLandingPane = new LandingPane();

		cachedScene = SceneController.makeNewScene(newLandingPane);
		return cachedScene;
	}

}
