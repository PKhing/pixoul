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
import scene.GameScene;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameAudioUtils;
import utils.GameConfig;
import utils.TransitionUtil;

/**
 * The LandingPane class represent the {@link StackPane} which display in the
 * first scene
 */
public class LandingPane extends StackPane {

	/**
	 * Represent the width of the pane
	 */
	private static int widthBox = 100;

	/**
	 * Represent the height of the pane
	 */
	private static int heightBox = 60;

	/**
	 * The {@link VBox} that contain titleText and buttonBox
	 */
	private VBox container;

	/**
	 * The {@link Text} that display as a title
	 */
	private Text titleText;

	/**
	 * The {@link VBox} that contain the start, option and exit button
	 */
	private VBox buttonBox;

	/**
	 * The {@link FadeTransition} which is using for making the fade transition
	 * while switch to {@link GameScene}
	 */
	private FadeTransition fading;

	/**
	 * The {@link Button} that used to start the game
	 */
	private Button startBtn;

	/**
	 * The {@link Button} that used to open {@link SettingPane}
	 */
	private Button optionBtn;

	/**
	 * The {@link Button} that used for exit the game
	 */
	private Button exitBtn;

	/**
	 * The {@link SettingPane} that used when click on {@value }
	 */
	private SettingPane settingPane = new SettingPane();

	/**
	 * The {@link MediaPlayer} that play the background music while in LandingPane
	 */
	private static MediaPlayer bgm = GameAudioUtils.LandingSceneBGM;

	/**
	 * The constructor of LandingPane. Initialize all field of LandingPane
	 */
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

	/**
	 * Initialize the {@link VBox} that contain buttonBox and titleText
	 */
	private void setupContainer() {
		container = new VBox();

		BackgroundSize bgSize = new BackgroundSize(GameConfig.getScreenWidth(), GameConfig.getScreenHeight(), false,
				false, false, false);

		WritableImage bgImg = DrawUtil.getWritableImage("landingBG.jpg");

		container.setAlignment(Pos.CENTER);
		container.setBackground(new Background(new BackgroundImage(bgImg, null, null, null, bgSize)));
	}

	/**
	 * Initialize the {@link VBox} that contain only button
	 */
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

	/**
	 * Initialize the title text
	 */
	private void setupTitleText() {
		titleText = new Text("Pixoul");
		titleText.setFont(FontUtil.getFont("large"));
		titleText.setFill(Color.CRIMSON);
		VBox.setMargin(titleText, new Insets(0, 0, 15, 0));
		titleText.setTextAlignment(TextAlignment.CENTER);
	}

	/**
	 * Initialize Fade Animation by using makeFadingNode and setup event when fading
	 * finish
	 * 
	 * @see utils.TransitionUtil#makeFadingNode(Node, double, double) makeFadingNode
	 */
	private void setupFadingAnimation() {
		fading = TransitionUtil.makeFadingNode(container, 1.0, 0.0);

		fading.setOnFinished((event) -> {
			bgm.stop();
			bgm.seek(Duration.ZERO);
			GameController.start();
			container.setOpacity(1.0);
		});
	}

	/**
	 * Initialize {@link StyledButton} which used for start button
	 */
	private void setupStartButton() {
		startBtn = new StyledButton(widthBox, "Start", Color.WHITE, Color.BLACK);
		startBtn.setTextFill(Color.WHITE);

		startBtn.setOnMouseClicked((event) -> fading.play());
	}

	/**
	 * Initialize {@link StyledButton} which used for setting button
	 */
	private void setupSettingButton() {
		optionBtn = new StyledButton(widthBox, "Option", Color.WHITE, Color.BLACK);
		optionBtn.setTextFill(Color.WHITE);
		optionBtn.setOnMouseClicked((event) -> {
			settingPane.updateSetting();
			getChildren().add(settingPane);
			settingPane.requestFocus();
		});
	}

	/**
	 * Initialize {@link StyledButton} which used for exit button
	 */
	private void setupExitButton() {
		exitBtn = new StyledButton(widthBox, "Exit", Color.WHITE, Color.BLACK);
		exitBtn.setTextFill(Color.WHITE);
		exitBtn.setOnMouseClicked((event) -> SceneController.exitGame());
	}

	/**
	 * The static getter of background music
	 * 
	 * @return background music of landingPane
	 */
	public static MediaPlayer getBgm() {
		return bgm;
	}

}
