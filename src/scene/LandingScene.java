package scene;

import components.LandingPane;
import controller.SceneController;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;

public class LandingScene {
	private static Scene cachedScene = null;
	private static MediaPlayer bgm = LandingPane.getBgm();
	private static StackPane root;
	
	public static Scene getScene() {
		bgm.play();

		if (cachedScene != null) {
			return cachedScene;
		}

		root = new StackPane();
		
		LandingPane newLandingPane = new LandingPane();
		root.getChildren().add(newLandingPane);
		
		Scene scene = SceneController.makeNewScene(root);
		cachedScene = scene;

		return scene;
	}

}
