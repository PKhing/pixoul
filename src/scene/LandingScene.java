package scene;

import controller.SceneController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.GameConfig;

public class LandingScene {
	private static Scene cachedScene = null;
	
	public static Scene getScene() {
		if(cachedScene != null) {
			return cachedScene;
		}
		
		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);

		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setVgap(10);
		grid.setHgap(10);

		Text titleText = new Text("Pixoul");
		titleText.setFont(new Font("Consolas", 40));

		grid.add(titleText, 0, 0, 3, 2);
		
		Button startBtn = new Button("Start");
		
		FadeTransition fading = new FadeTransition(Duration.seconds(1.0), grid);
		fading.setFromValue(1.0);
		fading.setToValue(0.0);

		fading.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SceneController.setSceneToStage(new GameScene().getScene());
				grid.setOpacity(1.0);
			}
		});
		
		startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				fading.play();
			}

		});

		Button exitBtn = new Button("Exit");
		exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.exit(0);
			};

		});
		
		grid.add(startBtn, 0, 3);
		grid.add(exitBtn, 1, 3);
		
		Scene scene = new Scene(grid, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
		cachedScene = scene;
		return scene;
	}

}
