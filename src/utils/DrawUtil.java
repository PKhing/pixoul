package utils;

import java.nio.IntBuffer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import logic.Cell;
import logic.Direction;

public class DrawUtil {

	private static PixelReader wallSprites = new Image(ClassLoader.getSystemResource("sprites/wall.png").toString())
			.getPixelReader();
	private static PixelReader playerSprites = new Image(ClassLoader.getSystemResource("sprites/player.png").toString())
			.getPixelReader();

	public static void drawCell(GraphicsContext gc, int y, int x, Cell cell) {
		if (cell.getType() != Cell.VOID) {
			WritableImage img = new WritableImage(wallSprites, cell.getSymbol() * 32, 0, 32, 40);
			gc.drawImage(scaleUp(img), x, y - 16);
		}
	}

	public static void drawCharacter(GraphicsContext gc, int y, int x, int index) {
		int direction = 0;
		if (index == Direction.UP)
			direction = 3;
		if (index == Direction.LEFT)
			direction = 1;
		if (index == Direction.RIGHT)
			direction = 2;
		WritableImage img = new WritableImage(playerSprites, 1 * 32, direction * 32, 32, 32);
		gc.drawImage(scaleUp(img), x, y - 8);
	}

	public static WritableImage scaleUp(WritableImage image) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		int z = 2;
		IntBuffer src = IntBuffer.allocate(width * height);
		WritablePixelFormat<IntBuffer> pf = PixelFormat.getIntArgbInstance();
		image.getPixelReader().getPixels(0, 0, width, height, pf, src, width);
		int newWidth = width * z;
		int newHeight = height * z;
		int[] dst = new int[newWidth * newHeight];
		int index = 0;
		for (int y = 0; y < height; y++) {
			index = y * newWidth * z;
			for (int x = 0; x < width; x++) {
				int pixel = src.get();
				for (int i = 0; i < z; i++) {
					for (int j = 0; j < z; j++) {
						dst[index + i + (newWidth * j)] = pixel;
					}
				}
				index += z;
			}
		}
		WritableImage bigImage = new WritableImage(newWidth, newHeight);
		bigImage.getPixelWriter().setPixels(0, 0, newWidth, newHeight, pf, dst, 0, newWidth);
		return bigImage;

	}

}
