package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class DrawUtil {

	private static String image_path = ClassLoader.getSystemResource("sprites.png").toString();
	private static Image mainSprites = new Image(image_path);

	public static void drawSprite(GraphicsContext gc, int x, int y, int index) {
		WritableImage img = new WritableImage(mainSprites.getPixelReader(), index * 45, 0, 45, 60);
		gc.drawImage(img, y, x);
	}

}
