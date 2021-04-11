package utils;

import java.nio.IntBuffer;

import controller.InterruptController;
import entity.base.DispatchAction;
import entity.base.Entity;
import entity.base.Monster;
import items.base.Armor;
import items.base.Item;
import items.base.Potion;
import items.weapon.Knife;
import items.weapon.Spear;
import items.weapon.Sword;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import logic.Cell;
import logic.Direction;
import logic.GameLogic;
import logic.Sprites;
import scene.GameScene;

public class DrawUtil {
	private static PixelReader wallSprites;
	private static PixelReader backpackSprites;
	private static PixelReader pauseSprites;
	private static PixelReader itemSprites;
	private static PixelReader ladderSprites;
	private static PixelReader entitySprites;
	private static Image attackMouseIcon;

	static {
		wallSprites = getImagePixelReader("sprites/wall.png");
		entitySprites = getImagePixelReader("sprites/entity.png");
		backpackSprites = getImagePixelReader("sprites/backpack.png");
		pauseSprites = getImagePixelReader("sprites/pause.png");
		itemSprites = getImagePixelReader("sprites/item.png");
		ladderSprites = getImagePixelReader("sprites/ladder.png");
		attackMouseIcon = getAttackMouseIcon();
	}

	private static WritableImage changeColor(WritableImage img) {

		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		WritableImage newImg = new WritableImage(width, height);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = img.getPixelReader().getColor(x, y);
				newImg.getPixelWriter().setColor(x, y, color);

				double hue = 0;
				double saturation = 0.7;
				double brightness = color.getBrightness();
				double opacity = color.getOpacity();

				Color newColor = Color.hsb(hue, saturation, brightness, opacity);
				newImg.getPixelWriter().setColor(x, y, newColor);

			}
		}
		return newImg;
	}

	public static PixelReader getImagePixelReader(String filePath) {
		return getImage(filePath).getPixelReader();
	}

	public static void drawBackpack(GraphicsContext gc) {
		WritableImage img = new WritableImage(backpackSprites, 0, 0, 32, 32);
		gc.drawImage(scaleUp(img, GameConfig.getScale()), 0, 0);
	}

	public static void drawPause(GraphicsContext gc) {
		WritableImage img = new WritableImage(pauseSprites, 0, 0, 16, 16);
		gc.drawImage(scaleUp(img, GameConfig.getScale()), 0, 0);
	}

	public static void drawItem(GraphicsContext gc, int y, int x, Item item) {
		if (item == null) {
			return;
		}
		int index = getIndexItemSymbol(item);
		WritableImage img = new WritableImage(itemSprites, item.getSymbol() * 32, index * 32, 32, 32);
		gc.drawImage(scaleUp(img, GameConfig.getScale()), y, x);
	}

	public static void drawCell(int y, int x, Cell cell) {
		GraphicsContext gc = GameScene.getGraphicsContext();
		if (cell.getType() != Cell.VOID) {
			WritableImage img = new WritableImage(wallSprites, cell.getSymbol() * 32, 0, 32, 40);
			gc.drawImage(scaleUp(img, GameConfig.getScale()), x, y - 8 * GameConfig.getScale());
		}
	}

	public static void drawLadder(int y, int x, Cell cell) {
		GraphicsContext gc = GameScene.getGraphicsContext();
		int idx = -1;
		if (cell.getType() == Cell.LADDER_DOWN) {
			idx = 0;
		} else if (cell.getType() == Cell.LADDER_UP) {
			idx = 1;
		} else {
			return;
		}
		WritableImage img = new WritableImage(ladderSprites, idx * 32, 0, 32, 32);
		gc.drawImage(scaleUp(img, GameConfig.getScale()), x, y - 8 * GameConfig.getScale());

	}

	public static void drawItemOnCell(int y, int x, Item item) {
		if (item == null) {
			return;
		}

		int index = getIndexItemSymbol(item);
		GraphicsContext gc = GameScene.getGraphicsContext();
		if (item instanceof Potion) {
			WritableImage img = new WritableImage(itemSprites, item.getSymbol() * 32, Sprites.SMALL_POTION * 32, 32, 32);
			gc.drawImage(scaleUp(img, GameConfig.getScale()), x - 1, y);
		} else {
			WritableImage img = new WritableImage(itemSprites, item.getSymbol() * 32, index * 32, 32, 32);
			gc.drawImage(scaleUp(img, GameConfig.getScale()), x, y - 4 * GameConfig.getScale());
		}
	}

	public static void drawEntity(int y, int x, Entity entity,int cnt) {
		
		if (entity == null)
			return;
		GraphicsContext gc = GameScene.getGraphicsContext();
		int direction = 0;
		if (entity.getDirection() == Direction.LEFT)
			direction = 1;
		if (entity.getDirection() == Direction.RIGHT)
			direction = 2;
		if (entity.getDirection() == Direction.UP)
			direction = 3;
		int walkIndex=(cnt/8+1)%4;
		if(walkIndex==3)walkIndex = 1;
		if(!entity.isMoving()){
			walkIndex = 1;
		}
		WritableImage img = new WritableImage(entitySprites, (96 * entity.getSymbol()) + 32 * walkIndex, direction * 32, 32,
				32);
		// Fix later?
		img = scaleUp(img, GameConfig.getScale());
		if (entity.isAttacked())
			img = changeColor(img);
		gc.drawImage(img, x, y /*- 4 * GameConfig.getScale()*/);

		if (entity instanceof Monster)
			drawHPBar(y, x, (Monster) entity);
	}

	public static void drawHPBar(int y, int x, Entity entity) {
		if (!(entity instanceof Monster)) {
			return;
		}
		GraphicsContext gc = GameScene.getGraphicsContext();
		gc.setFill(Color.BLACK);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(), 25 * GameConfig.getScale(),
				2 * GameConfig.getScale());
		gc.setFill(Color.RED);
		gc.fillRect(x + 4 * GameConfig.getScale(), y - 4 * GameConfig.getScale(),
				Math.ceil((double) entity.getHealth() / (double) entity.getMaxHealth() * 25.0 * GameConfig.getScale()),
				2 * GameConfig.getScale());
	}

	public static WritableImage scaleUp(WritableImage image, int z) {
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();

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

	public static void addEntityButton(int y, int x, Entity entity) {
		if (entity == null || !(entity instanceof Monster)) {
			return;
		}

		Canvas canvas = new Canvas(GameConfig.SPRITE_SIZE * GameConfig.getScale(),
				GameConfig.SPRITE_SIZE * GameConfig.getScale());
		canvas.setOnMouseClicked((event) -> {
			if(!InterruptController.isInterruptPlayerInput()) {
				GameLogic.gameUpdate(DispatchAction.ATTACK, (Monster) entity);
			}
		});
		addCursorHover(canvas, true);
		AnchorPane.setTopAnchor(canvas, (double) (y - 8));
		AnchorPane.setLeftAnchor(canvas, (double) x);
		GameScene.getButtonPane().getChildren().add(canvas);
	}

	public static void addCursorHover(Node node, boolean isEntity) {
		node.setOnMouseEntered((event) -> {
			if (isEntity) {
				GameScene.getScene().setCursor(new ImageCursor(attackMouseIcon));
			} else {
				GameScene.getScene().setCursor(Cursor.HAND);
			}
		});

		node.setOnMouseExited((event) -> {
			GameScene.getScene().setCursor(null);
		});
	}

	private static Image getAttackMouseIcon() {
		return new WritableImage(itemSprites, 0, 0, 32, 32);
	}

	private static int getIndexItemSymbol(Item item) {
		int index = 0;
		if (item instanceof Sword)
			index = Sprites.SWORD;
		if (item instanceof Spear)
			index = Sprites.SPEAR;
		if (item instanceof Knife)
			index = Sprites.KNIFE;
		if (item instanceof Armor)
			index = Sprites.ARMOR;
		if (item instanceof Potion)
			index = Sprites.POTION;
		return index;
	}

	private static Image getImage(String filePath) {
		return new Image(ClassLoader.getSystemResource(filePath).toString());
	}
}
