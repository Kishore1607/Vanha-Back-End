package in.fssa.vanha.exception;

import java.sql.SQLException;

public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new PersistenceException with the provided SQLException as its
	 * cause.
	 *
	 * @param e The SQLException that caused this PersistenceException.
	 */
	public PersistenceException(SQLException e) {
		super(e);
	}
}
