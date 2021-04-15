package components;

import controller.GameController;
import entity.Player;
import javafx.geometry.Insets;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameConfig;

public class StatusPane extends VBox {
	private Text hp, attack, defense;

	private static WritableImage background = DrawUtil.getWritableImage("sprites/statusPane.png");

	public StatusPane() {

		AnchorPane.setTopAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);

		this.setBackground(new Background(
				new BackgroundImage(DrawUtil.scaleUp(background, GameConfig.getScale()), null, null, null, null)));
		this.setPrefSize(75 * GameConfig.getScale(), 48 * GameConfig.getScale());
		this.setPadding(new Insets(9 * GameConfig.getScale(), 0, 0, 14 * GameConfig.getScale()));

		// Add text
		Player nowPlayer = GameController.getPlayer();

		hp = new Text("HP: " + nowPlayer.getHealth() + " / " + nowPlayer.getMaxHealth());
		hp.setFont(FontUtil.getFont(12));

		attack = new Text("Attack: " + nowPlayer.getAttack());
		attack.setFont(FontUtil.getFont(12));

		defense = new Text("Defense: " + nowPlayer.getDefense());
		defense.setFont(FontUtil.getFont(12));

		this.getChildren().addAll(hp, attack, defense);
	}

	public void setAllValue(Player player) {
		this.setHP(player.getHealth(), player.getMaxHealth());
		this.setAttack(player.getAttack());
		this.setDefense(player.getDefense());
	}

	public void setHP(Integer hp, Integer maxHP) {
		this.hp.setText("HP: " + hp.toString() + " / " + maxHP.toString());
	}

	public void setAttack(Integer attack) {
		this.attack.setText("Attack: " + attack.toString());
	}

	public void setDefense(Integer defense) {
		this.defense.setText("Defense: " + defense.toString());
	}
}
