package in.fssa.vanha.exception;

import java.sql.SQLException;

public class PersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersistenceException(SQLException e) {
		super(e);
	}
}
