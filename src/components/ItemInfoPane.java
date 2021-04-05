package components;

import items.base.Item;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.FontUtil;
import utils.GameConfig;

public class ItemInfoPane extends StackPane {
	public ItemInfoPane(Item item,int y, int x) {
		super();
		if (x < GameConfig.getScreenWidth() / 2) {
			AnchorPane.setLeftAnchor(this, (double) (x + 45 * GameConfig.getScale()));
		} else {

			AnchorPane.setLeftAnchor(this, (double) (x - 105 * GameConfig.getScale()));
		}

		AnchorPane.setTopAnchor(this, (double) y);
		addTexture();
		VBox infoPane = new VBox();
		this.getChildren().add(infoPane);
		
		Text name = new Text(item.getName());
		name.setFont(FontUtil.getFont());
		infoPane.getChildren().add(name);
		
		Text description = new Text(item.getDescription());
		description.setWrappingWidth(200.0);
		description.setFont(FontUtil.getFont());
		infoPane.getChildren().add(description);
		
	}

	public void addTexture() {
		Canvas canvas = new Canvas(200,100);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 200, 100);
		this.getChildren().add(canvas);
	}
}
