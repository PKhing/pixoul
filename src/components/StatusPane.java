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

/**
 * The StatusPane class is the pane that shows the health point, attack stat,
 * and defense stat of the {@link Player}.
 *
 */
public class StatusPane extends VBox {
	/**
	 * The text that shows the current health point of the player.
	 */
	private Text hp;
	/**
	 * The text that shows the current attack stat of the player.
	 */
	private Text attack;
	/**
	 * The text that shows the current defense stat of the player.
	 */
	private Text defense;

	/**
	 * The background image of this pane.
	 */
	private static WritableImage background = DrawUtil.getWritableImage("sprites/statusPane.png");

	/**
	 * Creates new StatusPane.
	 */
	public StatusPane() {

		AnchorPane.setTopAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);

		this.setBackground(new Background(
				new BackgroundImage(DrawUtil.scaleUp(background, GameConfig.getScale()), null, null, null, null)));
		this.setPrefSize(75 * GameConfig.getScale(), 48 * GameConfig.getScale());
		this.setPadding(new Insets(9 * GameConfig.getScale(), 0, 0, 14 * GameConfig.getScale()));

		// Adds text
		Player nowPlayer = GameController.getPlayer();

		hp = new Text("HP: " + nowPlayer.getHealth() + " / " + nowPlayer.getMaxHealth());
		hp.setFont(FontUtil.getFont(12));

		attack = new Text("Attack: " + nowPlayer.getAttack());
		attack.setFont(FontUtil.getFont(12));

		defense = new Text("Defense: " + nowPlayer.getDefense());
		defense.setFont(FontUtil.getFont(12));

		this.getChildren().addAll(hp, attack, defense);
	}

	/**
	 * Updates health point, attack stat, and defense stat text of this pane.
	 */
	public void update() {
		Player player = GameController.getPlayer();
		this.setHP(player.getHealth(), player.getMaxHealth());
		this.setAttack(player.getAttack());
		this.setDefense(player.getDefense());
	}

	/**
	 * Sets the health point text.
	 * 
	 * @param hp    The player's current health point
	 * @param maxHP The player's max health point
	 */
	public void setHP(Integer hp, Integer maxHP) {
		this.hp.setText("HP: " + hp.toString() + " / " + maxHP.toString());
	}

	/**
	 * Sets the attack stat text.
	 * 
	 * @param attack The player's attack stat
	 */
	public void setAttack(Integer attack) {
		this.attack.setText("Attack: " + attack.toString());
	}

	/**
	 * Sets the defense stat text.
	 * 
	 * @param defense The player's defense stat
	 */
	public void setDefense(Integer defense) {
		this.defense.setText("Defense: " + defense.toString());
	}
}
