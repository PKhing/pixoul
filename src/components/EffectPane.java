package components;

import java.util.ArrayList;

import items.base.Potion;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.GameConfig;
import utils.Util;

public class EffectPane extends VBox{
	public EffectPane(ArrayList<Potion> potionList) {
		super();

		AnchorPane.setTopAnchor(this, 25.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(this, 0.0);
		this.setStyle("-fx-background-color: rgba(0,0,0, 0.5);-fx-padding:7");


		update(potionList);
	}
	
	public void update(ArrayList<Potion> potionList) {
		this.getChildren().clear();
		for(Potion potion: potionList) {
			Text effect = new Text(potion.getName()+": "+String.valueOf(potion.getDuration()));
			effect.setFont(Util.getFont());
			effect.setFill(Color.WHITE);
			this.getChildren().add(effect);
		}
		
	}
}
