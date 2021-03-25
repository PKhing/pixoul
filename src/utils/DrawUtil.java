package utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class DrawUtil {

	private static PixelReader mainSpritesPixel = new Image(ClassLoader.getSystemResource("sprites/wall.png").toString()).getPixelReader();
	private static PixelReader characterSpritesPixel = new Image(ClassLoader.getSystemResource("sprites/character.png").toString()).getPixelReader();

	public static void drawSprite(GraphicsContext gc, int x, int y, int index) {
		WritableImage img = new WritableImage(mainSpritesPixel, index * 45, 0, 45, 60);
		gc.drawImage(img, y, x);
	}
	public static void drawCharacter(GraphicsContext gc, int x, int y, int index) {
		WritableImage img = new WritableImage(characterSpritesPixel, index * 45, 0, 45, 60);
		gc.drawImage(img, y, x);
	}

}
