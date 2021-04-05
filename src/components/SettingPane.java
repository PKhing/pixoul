package components;

import controller.InterruptController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameAudioUtils;
import utils.GameConfig;

public class SettingPane extends VBox {
	private final int heightBox = 200;
	private final int widthBox = 200;

	public SettingPane() {
		super();
		
		this.setStyle("-fx-background-color: white");
		this.setPadding(new Insets(20));

		this.setAlignment(Pos.CENTER);
		setPrefHeight(heightBox * GameConfig.getScale());
		setPrefWidth(widthBox * GameConfig.getScale());
		setMaxHeight(heightBox * GameConfig.getScale());
		setMaxWidth(widthBox * GameConfig.getScale());
		
		addTitle();
		this.addVolumeSlider();
		this.addCloseText();
		
		this.setOnKeyPressed((event) -> {
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
		closeText.setFont(FontUtil.getFont(12));
		
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
		this.getChildren().add(optionTitle);
	}
	
	private void addVolumeSlider() {
		HBox bgmVolumeBox = new HBox();
		bgmVolumeBox.setPadding(new Insets(10));
		bgmVolumeBox.setSpacing(10);
		bgmVolumeBox.setAlignment(Pos.CENTER);

		Label volumeLabel = new Label("BGM Volume");
		volumeLabel.setFont(FontUtil.getFont(12));
		
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
}
