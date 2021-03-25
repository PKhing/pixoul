package application;

import controller.SceneController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scene.LandingScene;
import utils.GameConfig;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		String iconPath = ClassLoader.getSystemResource("icon.png").toString();

		Rectangle2D screenBound = Screen.getPrimary().getBounds();
		
		double rectangleSize = Math.min(screenBound.getMaxX() * GameConfig.SCREEN_SCALING, screenBound.getMaxY() * GameConfig.SCREEN_SCALING);
		
		GameConfig.setScreenWidth((int) rectangleSize);
		GameConfig.setScreenHeight((int) rectangleSize);
		
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
