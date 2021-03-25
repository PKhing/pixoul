package application;

import controller.SceneController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scene.LandingScene;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		String iconPath = ClassLoader.getSystemResource("icon.png").toString();

		primaryStage.setTitle("Pixoul");
		primaryStage.getIcons().add(new Image(iconPath));

		SceneController.setMainStage(primaryStage);
		SceneController.setSceneToStage(LandingScene.getScene());

		SceneController.showStage();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
