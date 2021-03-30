package components;

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
import utils.GameConfig;
import utils.Util;

public class StatusPane extends StackPane{
	private Text hp,attack,defense; 
	public StatusPane() {
		super();

		Canvas background = new Canvas(75 * GameConfig.getScale(), 48 * GameConfig.getScale());
		drawTexture(background.getGraphicsContext2D());
		getChildren().add(background);

		AnchorPane.setTopAnchor(this, 0.0);
		AnchorPane.setLeftAnchor(this, 0.0);

		VBox statusBox = new VBox();
		getChildren().add(statusBox);
		statusBox.setPadding(new Insets(10*GameConfig.getScale(),10*GameConfig.getScale(),10*GameConfig.getScale(),15*GameConfig.getScale()));


		hp = new Text("HP: 0 / 0");
		hp.setFont(Util.getFont());
		statusBox.getChildren().add(hp);

		attack = new Text("Attack: 0");
		attack.setFont(Util.getFont());
		statusBox.getChildren().add(attack);

		defense = new Text("Defense: 0");
		defense.setFont(Util.getFont());
		statusBox.getChildren().add(defense);
	}
	
	private void drawTexture(GraphicsContext gc) {
		PixelReader texture = DrawUtil.getImagePixelReader("sprites/statusPane.png");
		WritableImage img = new WritableImage(texture, 0, 0, 75, 48);
		gc.drawImage(DrawUtil.scaleUp(img), 0, 0);
	}
	
	public void setHP(Integer hp,Integer maxHP){
		this.hp.setText("HP: "+hp.toString()+" / "+maxHP.toString());
	}
	public void setAttack(Integer attack){
		this.attack.setText("Attack: "+ attack.toString());
	}
	public void setDefense(Integer defense){
		this.defense.setText("Defense: "+ defense.toString());
	}
}
