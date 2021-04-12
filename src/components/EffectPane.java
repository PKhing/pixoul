package components;

import java.util.List;

import controller.GameController;
import effects.EntityEffect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class EffectPane extends VBox {
	public EffectPane() {

		AnchorPane.setTopAnchor(this, 25.0 * GameConfig.getScale());
		AnchorPane.setRightAnchor(this, 0.0);

		this.setPadding(new Insets(7));
		this.setStyle("-fx-background-color: rgba(0,0,0, 0.5)");
		this.setAlignment(Pos.CENTER_RIGHT);

		update();
	}

	public void update() {
		this.getChildren().clear();
		List<EntityEffect> playerEffect = GameController.getPlayer().getEffectList();
		
		if (playerEffect.size() == 0) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
		for (EntityEffect effect : playerEffect) {
			Text effectText = new Text(effect.toString());
			effectText.setFont(FontUtil.getFont(12));
			effectText.setFill(Color.WHITE);
			this.getChildren().add(effectText);
		}
	}
}
