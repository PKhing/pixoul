package utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class DrawUtil {

	private static Image mainSprites = new Image(ClassLoader.getSystemResource("sprites/wall.png").toString());
	private static Image characterSprites = new Image(ClassLoader.getSystemResource("sprites/character.png").toString());

	public static void drawSprite(GraphicsContext gc, int x, int y, int index) {
		WritableImage img = new WritableImage(mainSprites.getPixelReader(), index * 45, 0, 45, 60);
		gc.drawImage(img, y, x);
	}
	public static void drawCharacter(GraphicsContext gc, int x, int y, int index) {
		WritableImage img = new WritableImage(characterSprites.getPixelReader(), index * 45, 0, 45, 60);
		gc.drawImage(img, y, x);
	}

}
