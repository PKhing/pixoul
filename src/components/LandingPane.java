package components;

import controller.GameController;
import controller.SceneController;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
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

public class LandingPane extends StackPane {
	private static int widthBox = 100;

	private static int heightBox = 60;

	private VBox container;

	private Text titleText;

	private VBox buttonBox;

	private FadeTransition fading;

	private Button startBtn;

	private Button optionBtn;

	private Button exitBtn;

	private SettingPane settingPane = new SettingPane();

	private static MediaPlayer bgm = GameAudioUtils.LandingSceneBGM;

	public LandingPane() {
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		setupContainer();

		setupButtonBox();

		setupTitleText();

		setupFadingAnimation();

		setupStartButton();

		setupSettingButton();

		setupExitButton();

		getChildren().add(container);

		container.getChildren().addAll(titleText, buttonBox);

		buttonBox.getChildren().addAll(startBtn, optionBtn, exitBtn);
	}

	private void setupContainer() {
		container = new VBox();

		BackgroundSize bgSize = new BackgroundSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight(), false,
				false, false, false);

		WritableImage bgImg = DrawUtil.getWritableImage("landingBG.jpg");

		container.setAlignment(Pos.CENTER);
		container.setBackground(new Background(new BackgroundImage(bgImg, null, null, null, bgSize)));
	}

	private void setupButtonBox() {
		buttonBox = new VBox();

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
	}

	private void setupTitleText() {
		titleText = new Text("Pixoul");
		titleText.setFont(FontUtil.getFont(30));
		titleText.setFill(Color.CRIMSON);
		VBox.setMargin(titleText, new Insets(0, 0, 15, 0));
		titleText.setTextAlignment(TextAlignment.CENTER);
	}

	private void setupFadingAnimation() {
		fading = TransitionUtil.makeFadingNode(container, 1.0, 0.0);

		fading.setOnFinished((event) -> {
			bgm.stop();
			bgm.seek(Duration.ZERO);
			GameController.start();
			container.setOpacity(1.0);
		});
	}

	private void setupStartButton() {
		startBtn = new StyledButton(widthBox, "Start", Color.WHITE, Color.BLACK);
		startBtn.setTextFill(Color.WHITE);

		startBtn.setOnMouseClicked((event) -> fading.play());
	}

	private void setupSettingButton() {
		optionBtn = new StyledButton(widthBox, "Option", Color.WHITE, Color.BLACK);
		optionBtn.setTextFill(Color.WHITE);
		optionBtn.setOnMouseClicked((event) -> {
			settingPane.updateSetting();
			getChildren().add(settingPane);
			settingPane.requestFocus();
		});
	}

	private void setupExitButton() {
		exitBtn = new StyledButton(widthBox, "Exit", Color.WHITE, Color.BLACK);
		exitBtn.setTextFill(Color.WHITE);
		exitBtn.setOnMouseClicked((event) -> SceneController.exitGame());
	}

	public static MediaPlayer getBgm() {
		return bgm;
	}

}
