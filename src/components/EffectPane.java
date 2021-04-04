package components;

import java.util.List;

import controller.GameController;
import items.base.Potion;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class EffectPane extends VBox {
	public EffectPane() {
		super();

		AnchorPane.setTopAnchor(this, 25.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(this, 0.0);
		
		this.setPadding(new Insets(7));
		this.setStyle("-fx-background-color: rgba(0,0,0, 0.5)");

		update();
	}

	public void update() {
		this.getChildren().clear();
		List<Potion> playerEffect = GameController.getPlayer().getPotionList();
		if(playerEffect.size() == 0) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
		for (Potion potion : GameController.getPlayer().getPotionList()) {
			Text effect = new Text(potion.getName() + ": " + String.valueOf(potion.getDuration()));
			effect.setFont(FontUtil.getFont());
			effect.setFill(Color.WHITE);
			this.getChildren().add(effect);
		}
	}
}
