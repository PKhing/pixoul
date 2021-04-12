package components;

import controller.GameController;
import entity.Player;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.DrawUtil;
import utils.FontUtil;
import utils.GameConfig;

public class StatusPane extends StackPane {
	private Text hp, attack, defense;

	public StatusPane() {
		super();

		Canvas background = new Canvas(75 * GameConfig.getScale(), 48 * GameConfig.getScale());
		drawTexture(background.getGraphicsContext2D());
		getChildren().add(background);

		AnchorPane.setTopAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);

		VBox statusBox = new VBox();
		getChildren().add(statusBox);
		statusBox.setPadding(new Insets(10 * GameConfig.getScale(), 10 * GameConfig.getScale(),
				10 * GameConfig.getScale(), 15 * GameConfig.getScale()));

		Player nowPlayer = GameController.getPlayer();

		hp = new Text("HP: " + nowPlayer.getHealth() + " / " + nowPlayer.getMaxHealth());
		hp.setFont(FontUtil.getFont(12));
		statusBox.getChildren().add(hp);

		attack = new Text("Attack: " + nowPlayer.getAttack());
		attack.setFont(FontUtil.getFont(12));
		statusBox.getChildren().add(attack);

		defense = new Text("Defense: " + nowPlayer.getDefense());
		defense.setFont(FontUtil.getFont(12));
		statusBox.getChildren().add(defense);
	}

	private void drawTexture(GraphicsContext gc) {
		PixelReader texture = DrawUtil.getImagePixelReader("sprites/statusPane.png");
		WritableImage img = new WritableImage(texture, 0, 0, 75, 48);
		gc.drawImage(DrawUtil.scaleUp(img, GameConfig.getScale()), 0, 0);
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
