package in.fssa.vanha.exception;

import java.sql.SQLException;

public class PersistenceException extends Exception {

	public PersistenceException(SQLException e) {
		super(e);
	}
}
