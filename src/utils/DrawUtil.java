package utils;

import java.nio.IntBuffer;

import entity.Player;
import entity.Skeleton;
import entity.base.DispatchAction;
import entity.base.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.AnchorPane;
import logic.Cell;
import logic.Direction;
import logic.GameLogic;

public class DrawUtil {
	private static PixelReader wallSprites = getImagePixelReader("sprites/wall.png");
	private static PixelReader playerSprites = getImagePixelReader("sprites/player.png");
	private static PixelReader skeletonSprites = getImagePixelReader("sprites/skeleton.png");
	private static PixelReader backpackSprites = getImagePixelReader("sprites/backpack.png");
	private static PixelReader pauseSprites = getImagePixelReader("sprites/pause.png");

	public static PixelReader getImagePixelReader(String filePath) {
		return new Image(ClassLoader.getSystemResource(filePath).toString()).getPixelReader();
	}

	public static void drawBackpack(GraphicsContext gc) {
		WritableImage img = new WritableImage(backpackSprites, 0, 0, 32, 32);
		gc.drawImage(scaleUp(img), 0, 0);
	}

	public static void drawPause(GraphicsContext gc) {
		WritableImage img = new WritableImage(pauseSprites, 0, 0, 16, 16);
		gc.drawImage(scaleUp(img), 0, 0);
	}

	public static void drawCell(GraphicsContext gc, int y, int x, Cell cell) {
		if (cell.getType() != Cell.VOID) {
			WritableImage img = new WritableImage(wallSprites, cell.getSymbol() * 32, 0, 32, 40);
			gc.drawImage(scaleUp(img), x, y - 16);
		}
	}

	public static void drawEntity(GraphicsContext gc, int y, int x, Entity entity) {
		int direction = 0;
		if (entity.getDirection() == Direction.UP)
			direction = 3;
		if (entity.getDirection() == Direction.LEFT)
			direction = 1;
		if (entity.getDirection() == Direction.RIGHT)
			direction = 2;
		WritableImage img = null;
		if(entity instanceof Player)img = new WritableImage(playerSprites, 1 * 32, direction * 32, 32, 32);
		if(entity instanceof Skeleton)img = new WritableImage(skeletonSprites, 1 * 32, direction * 32, 32, 32);
		gc.drawImage(scaleUp(img), x, y - 8);
	}

	public static WritableImage scaleUp(WritableImage image) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

		int z = GameConfig.getScale();
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
	public static void addEntityButton(AnchorPane buttonPane, int y, int x, Entity entity) {
		Canvas canvas = new Canvas(GameConfig.SPRITE_SIZE*GameConfig.getScale(),GameConfig.SPRITE_SIZE*GameConfig.getScale());
		canvas.setOnMouseClicked((event) -> {
			GameLogic.gameUpdate(DispatchAction.ATTACK, entity);
		});
		AnchorPane.setTopAnchor(canvas, (double) (y-8));
		AnchorPane.setLeftAnchor(canvas, (double) x);
		buttonPane.getChildren().add(canvas);
	}

}
