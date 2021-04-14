package scene;

import components.SettingPane;
import components.StyledButton;
import controller.GameController;
import controller.SceneController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameAudioUtils;
import utils.GameConfig;
import utils.TransitionUtil;

public class LandingScene {
	private static Scene cachedScene = null;
	private static MediaPlayer bgm = GameAudioUtils.LandingSceneBGM;
	private static int widthBox = 100;
	private static int heightBox = 60;
	
	public static Scene getScene() {
		bgm.play();

		if (cachedScene != null) {
			return cachedScene;
		}

		StackPane root = new StackPane();
		
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		
		VBox container = new VBox();

		container.setAlignment(Pos.CENTER);
		container.setBackground(new Background(new BackgroundImage(DrawUtil.getWritableImage("landingBG.jpg"), null, null, null, new BackgroundSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight(), false, false, false, false))));
		VBox buttonBox = new VBox();

		buttonBox.setBorder(new Border(
				new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		buttonBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		buttonBox.setSpacing(10.0);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(15));

		buttonBox.setMaxHeight(heightBox * GameConfig.getScale());
		buttonBox.setPrefHeight(heightBox * GameConfig.getScale());

		buttonBox.setMaxWidth(widthBox * GameConfig.getScale());
		buttonBox.setPrefWidth(widthBox * GameConfig.getScale());

		// Title Text

		Text titleText = new Text("Pixoul");
		titleText.setFont(FontUtil.getFont(30));
		titleText.setFill(Color.LIGHTGRAY);
		VBox.setMargin(titleText, new Insets(0, 0, 15, 0));
		titleText.setTextAlignment(TextAlignment.CENTER);

		// Fading animation setup

		FadeTransition fading = TransitionUtil.makeFadingNode(container, 1.0, 0.0);

		buttonBox.setCache(true);
		buttonBox.setCacheShape(true);
		buttonBox.setCacheHint(CacheHint.DEFAULT);

		// Start Button
		Button startBtn = new StyledButton(widthBox, "Start", Color.WHITE, Color.BLACK);
		startBtn.setTextFill(Color.WHITE);
		fading.setOnFinished((event) -> {
			bgm.stop();
			bgm.seek(Duration.ZERO);
			GameController.start();
			container.setOpacity(1.0);
		});

		startBtn.setOnMouseClicked((event) -> fading.play());

		// Exit button
		Button optionBtn = new StyledButton(widthBox, "Option", Color.WHITE, Color.BLACK);
		optionBtn.setTextFill(Color.WHITE);
		optionBtn.setOnMouseClicked((event) -> {
			SettingPane settingPane = new SettingPane();
			root.getChildren().add(settingPane);
			settingPane.requestFocus();
		});

		Button exitBtn = new StyledButton(widthBox, "Exit", Color.WHITE, Color.BLACK);
		exitBtn.setTextFill(Color.WHITE);
		exitBtn.setOnMouseClicked((event) -> SceneController.exitGame());

		root.getChildren().add(container);

		container.getChildren().addAll(titleText, buttonBox);

		buttonBox.getChildren().addAll(startBtn, optionBtn, exitBtn);

		Scene scene = SceneController.makeNewScene(root);
		cachedScene = scene;

		return scene;
	}

}
