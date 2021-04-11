package scene;

import components.SettingPane;
import controller.GameController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.FontUtil;
import utils.GameAudioUtils;
import utils.GameConfig;

public class LandingScene {
	private static Scene cachedScene = null;
	private static MediaPlayer bgm = GameAudioUtils.LandingSceneBGM;

	public static Scene getScene() {
		bgm.play();

		if (cachedScene != null) {
			return cachedScene;
		}
		
		StackPane root = new StackPane();
		
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		VBox box = new VBox();

		box.setAlignment(Pos.CENTER);

		box.setSpacing(10.0);
		
		box.setPadding(new Insets(25, 25, 25, 25));

		// Title Text

		Text titleText = new Text("Pixoul");
		titleText.setFont(FontUtil.getFont(30));
		titleText.setFill(Color.WHITE);

		box.getChildren().add(titleText);

		// Fading animation setup

		FadeTransition fading = new FadeTransition(Duration.seconds(1.0), box);
		fading.setFromValue(1.0);
		fading.setToValue(0.0);

		box.setCache(true);
		box.setCacheShape(true);
		box.setCacheHint(CacheHint.DEFAULT);

		// Start Button
		Button startBtn = new Button("Start");
		fading.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bgm.stop();
				bgm.seek(Duration.ZERO);
				GameController.start();
				box.setOpacity(1.0);
			}
		});

		startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				fading.play();
			}

		});

		// Exit button
		Button optionBtn = new Button("Option");
		optionBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				SettingPane settingPane = new SettingPane();
				root.getChildren().add(settingPane);
				settingPane.requestFocus();
			};

		});

		Button exitBtn = new Button("Exit");
		exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.exit(0);
			};

		});

		box.getChildren().addAll(startBtn, optionBtn, exitBtn);

		root.getChildren().add(box);

		Scene scene = new Scene(root, GameConfig.getScreenWidth(), GameConfig.getScreenHeight());
		cachedScene = scene;

		return scene;
	}

}
