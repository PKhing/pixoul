package exception;

import controller.GameController;

/**
 * The InvalidFloorException is {@link Exception} that will be used to throw when
 * input floor number is invalid such as decimal or negative value
 * 
 * @see GameController#getFloor(int floor)
 */
public class InvalidFloorException extends Exception {

	private static final long serialVersionUID = 6209367624571488316L;

	/**
	 * The constructor of class. Initialize superclass with message
	 * 
	 * @param message The message of exception
	 */
	public InvalidFloorException(String message) {
		super(message);
	}
}
