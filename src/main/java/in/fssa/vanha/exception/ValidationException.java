package in.fssa.vanha.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ValidationException with the specified detail message.
	 *
	 * @param message The detail message for this exception.
	 */
	public ValidationException(String message) {
		super(message);
	}
}
