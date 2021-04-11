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
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameAudioUtils;
import utils.GameConfig;

public class SettingPane extends VBox {
	private final int heightBox = 200;
	private final int widthBox = 200;

	public SettingPane() {
		super();
		
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		setPadding(new Insets(30));
		setSpacing(10);

		setAlignment(Pos.CENTER);
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());
		
		addTitle();
		addVolumeSlider();
		addDisableAnimation();
		addCloseText();
		
		setOnKeyPressed((event) -> {
			if(event.getCode() == KeyCode.ESCAPE) {
				((Pane) this.getParent()).getChildren().remove(this);
			}
		});
		
		InterruptController.setSettingOpen(true);
	}

	private void addCloseText() {
		HBox closeBox = new HBox();
		closeBox.setPadding(new Insets(20));
		closeBox.setAlignment(Pos.CENTER);
		
		Text closeText = new Text("OK");
		closeText.setFont(FontUtil.getFont(18));
		closeText.setFill(Color.WHITE);
		
		closeText.setOnMouseClicked((event) -> {
			try {
				((Pane) this.getParent()).getChildren().remove(this);
				InterruptController.setSettingOpen(false);
			} catch(UnsupportedOperationException e) {
				e.printStackTrace();
			}
		});
		
		closeBox.getChildren().add(closeText);
		
		this.getChildren().add(closeBox);
	}
	
	private void addTitle() {
		Text optionTitle = new Text("Option");
		
		optionTitle.setFont(FontUtil.getFont(30));
		optionTitle.setFill(Color.WHITE);
		
		this.getChildren().add(optionTitle);
	}
	
	private void addVolumeSlider() {
		HBox bgmVolumeBox = new HBox();
		bgmVolumeBox.setPadding(new Insets(10));
		bgmVolumeBox.setSpacing(10);
		bgmVolumeBox.setAlignment(Pos.CENTER_LEFT);
		
		Label volumeLabel = new Label("BGM Volume");
		volumeLabel.setFont(FontUtil.getFont(12));
		volumeLabel.setTextFill(Color.WHITE);
		
		Slider volumeSlider = new Slider(0, 100, (int) (GameConfig.getVolume() * 100));
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
	
	private void addDisableAnimation() {
		HBox disableAnimationBox = new HBox();
		disableAnimationBox.setPadding(new Insets(10));
		disableAnimationBox.setSpacing(7);
		disableAnimationBox.setAlignment(Pos.CENTER_LEFT);
		
		Label disableLabel = new Label("Disable Animation ");
		disableLabel.setFont(FontUtil.getFont(12));
		disableLabel.setTextFill(Color.WHITE);
		
		CheckBox checkBox = new CheckBox();
		checkBox.setSelected(GameConfig.isSkipMoveAnimation()); 
		
		checkBox.setOnMouseClicked((event) -> {
			boolean newSkipMove = !GameConfig.isSkipMoveAnimation();
			
			GameConfig.setSkipMoveAnimation(newSkipMove);
			checkBox.setSelected(newSkipMove);
		});
		
		disableAnimationBox.getChildren().addAll(disableLabel, checkBox);
		this.getChildren().add(disableAnimationBox);
	}
}
