package exception;

public class NullMapException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1341001087143272696L;

	@Override
	public String getMessage() {
		return "GameMap is not instantiated yet";
	}
}
