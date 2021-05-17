package components;

import controller.InterruptController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameAudioUtils;
import utils.GameConfig;

/**
 * The SettingPane is the pane that contains all the setting which available to
 * adjust in this game
 */

public class SettingPane extends VBox {

	/**
	 * Represent the height of the pane
	 */
	private final int heightBox = 140;

	/**
	 * Represent the width of the pane
	 */
	private final int widthBox = 180;

	/**
	 * Represent the {@link Slider} that control sound volume
	 */
	private Slider volumeSlider;

	/**
	 * Represent the {@link CheckBox} that disable the animation when entity is
	 * moving
	 */
	private CheckBox animationCheckBox;

	/**
	 * The constructor of the class. Initialize the inside component, event handler
	 * and style
	 */
	public SettingPane() {
		styleSetup();
		addTitle();
		addVolumeSlider();
		addDisableAnimation();
		addCloseText();

		setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				((Pane) this.getParent()).getChildren().remove(this);
			}
		});

		InterruptController.setSettingOpen(true);
	}

	/**
	 * Update value inside setting to current value
	 */
	public void updateSetting() {
		volumeSlider.setValue((int) (GameConfig.getVolume() * 100));
		animationCheckBox.setSelected(GameConfig.isSkipMoveAnimation());
	}

	/**
	 * Initialize style for pane
	 */
	private void styleSetup() {
		setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		setPadding(new Insets(20));
		setSpacing(10);

		setAlignment(Pos.CENTER);
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());

	}

	/**
	 * Initialize new close text which can be clicked to close pane
	 */
	private void addCloseText() {
		HBox closeBox = new HBox();
		closeBox.setPadding(new Insets(20, 0, 0, 0));
		closeBox.setAlignment(Pos.CENTER);

		Text closeText = new Text("OK");
		closeText.setFont(FontUtil.getFont("medium"));
		closeText.setFill(Color.BLACK);
		closeText.setStroke(null);

		closeText.setOnMouseClicked((event) -> {
			try {
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});

		closeBox.getChildren().addAll(closeText);

		this.getChildren().add(closeBox);
	}

	/**
	 * Initialize new title text
	 */
	private void addTitle() {
		Text optionTitle = new Text("Option");

		optionTitle.setFont(FontUtil.getFont("large"));
		optionTitle.setFill(Color.BLACK);

		this.getChildren().add(optionTitle);
	}

	/**
	 * Initialize new {@link #volumeSlider} and container with current value
	 */
	private void addVolumeSlider() {
		HBox bgmVolumeBox = new HBox();
		bgmVolumeBox.setPadding(new Insets(10));
		bgmVolumeBox.setSpacing(10);
		bgmVolumeBox.setAlignment(Pos.CENTER_LEFT);

		Label volumeLabel = new Label("BGM Volume");
		volumeLabel.setFont(FontUtil.getFont("small"));
		volumeLabel.setTextFill(Color.BLACK);

		volumeSlider = new Slider(0, 100, (int) (GameConfig.getVolume() * 100));
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				GameConfig.setVolume((double) newValue / 100);
				GameAudioUtils.updateBGMVolume();
			}

		});

		bgmVolumeBox.getChildren().addAll(volumeLabel, volumeSlider);
		this.getChildren().add(bgmVolumeBox);
	}

	/**
	 * Initialize new {@link #animationCheckBox} and container
	 */
	private void addDisableAnimation() {
		HBox disableAnimationBox = new HBox();
		disableAnimationBox.setPadding(new Insets(10));
		disableAnimationBox.setSpacing(7);
		disableAnimationBox.setAlignment(Pos.CENTER_LEFT);

		Label disableLabel = new Label("Disable Animation ");
		disableLabel.setFont(FontUtil.getFont("small"));
		disableLabel.setTextFill(Color.BLACK);

		animationCheckBox = new CheckBox();
		animationCheckBox.setSelected(GameConfig.isSkipMoveAnimation());

		animationCheckBox.setOnMouseClicked((event) -> {
			boolean newSkipMove = !GameConfig.isSkipMoveAnimation();

			GameConfig.setSkipMoveAnimation(newSkipMove);
			animationCheckBox.setSelected(newSkipMove);
		});

		disableAnimationBox.getChildren().addAll(disableLabel, animationCheckBox);
		this.getChildren().add(disableAnimationBox);
	}
}
