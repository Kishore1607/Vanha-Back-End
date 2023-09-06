package in.fssa.vanha.exception;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String string) {
		super(string);
	}
}
