package exception;

import controller.GameController;

/**
 * The NullMapException is {@link Exception} that will be thrown when
 * {@link GameMap} is not initiated yet
 * 
 * @see GameController#getGameMap()
 */
public class NullMapException extends Exception {

	private static final long serialVersionUID = 1341001087143272696L;

	/**
	 * The constructor of class. Initialize superclass with message
	 * 
	 * @param message The message of exception
	 */
	public NullMapException(String message) {
		super(message);
	}
}
