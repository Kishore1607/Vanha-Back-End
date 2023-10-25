package in.fssa.vanha.exception;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	/**
	 * Constructs a new ServiceException with the specified exception as its cause.
	 *
	 * @param e The exception that caused this ServiceException.
	 */
	public ServiceException(Exception e) {
		super(e);
	}

	/**
	 * Constructs a new ServiceException with the specified detail message.
	 *
	 * @param string The detail message for this ServiceException.
	 */
	public ServiceException(String string) {
		super(string);
	}
}
