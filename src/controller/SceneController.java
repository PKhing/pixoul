package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	private static Stage mainStage;

	public static void setMainStage(Stage mainStage) {
		if (mainStage == null)
			return;
		SceneController.mainStage = mainStage;
	}

	public static void setSceneToStage(Scene newScene) {
		if (newScene == null)
			return;
		mainStage.setScene(newScene);
	}

	public static void showStage() {
		mainStage.show();
	}
}
