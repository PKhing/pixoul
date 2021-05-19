package scene;

import components.GameOverPane;
import controller.SceneController;
import javafx.scene.Scene;

public class GameOverScene {
	
	/**
	 * Represent the cached scene that it does not need to have a multiple initialize
	 */
	private static Scene cachedScene = null;

	/**
	 * Get the {@link #cachedScene gameOverScene}
	 * 
	 * @return the {@link Scene} which used for display when game over
	 */
	public static Scene getScene() {
		// If it have already cached then return the cache
		if (cachedScene != null) {
			return cachedScene;
		}
		cachedScene = SceneController.makeNewScene(new GameOverPane());
		return cachedScene;
	}
}
