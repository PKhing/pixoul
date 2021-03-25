package scene;

import controller.SceneController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.GlobalConstant;

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
		startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SceneController.setSceneToStage(new GameScene().getScene());
			}

		});

		Button exitBtn = new Button("Exit");
		exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.exit(0);
			};

		});
		
		grid.add(startBtn, 0,3);
		grid.add(exitBtn, 1, 3);
		
		Scene scene = new Scene(grid, GlobalConstant.SCREEN_WIDTH, GlobalConstant.SCREEN_HEIGHT);
		cachedScene = scene;
		return scene;
	}

}
