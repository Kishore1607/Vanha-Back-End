package in.fssa.vanha.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {

	/**
	 * Establishes a database connection to a MySQL database.
	 *
	 * @return A {@link Connection} object representing the database connection.
	 * @throws RuntimeException If any error occurs during the connection process.
	 */
	public static Connection getConnection() {

		String url;
		String userName;
		String passWord;

//		url = System.getenv("DATABASE_HOST");
//		userName = System.getenv("DATABASE_USERNAME");
//		passWord = System.getenv("DATABASE_PASSWORD");

		url = "jdbc:mysql://127.0.0.1:3306/kishore";
		userName = "root";
		passWord = "123456";

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, passWord);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}

	/**
	 * Closes the provided database connection and prepared statement.
	 *
	 * This method is used to safely close a database connection and a prepared
	 * statement. It ensures that both the connection and prepared statement are
	 * properly closed and any potential SQLExceptions are caught and printed to the
	 * standard error stream.
	 *
	 * @param connection The database connection to be closed. It can be null.
	 * @param presta     The prepared statement to be closed. It can be null.
	 */
	public static void close(Connection connection, PreparedStatement presta) {

		try {
			if (presta != null) {
				presta.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the provided database connection and prepared statements.
	 * 
	 * This method safely closes the provided resources (Connection,
	 * PreparedStatement) to release any associated database connections and free up
	 * resources. It ensures that each resource is closed if it is not null and
	 * handles any SQLExceptions that may occur during the closing process.
	 *
	 * @param conn The database Connection to be closed.
	 * @param pre1 The first PreparedStatement to be closed.
	 * @param pre2 The second PreparedStatement to be closed.
	 */
	public static void close(Connection conn, PreparedStatement pre1, PreparedStatement pre2) {

		try {
			if (pre2 != null) {
				pre2.close();
			}
			if (pre1 != null) {
				pre1.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the database resources, including the database connection, prepared
	 * statement, and result set.
	 *
	 * @param connection The database connection to be closed. If null, no action is
	 *                   taken.
	 * @param presta     The prepared statement to be closed. If null, no action is
	 *                   taken.
	 * @param reset      The result set to be closed. If null, no action is taken.
	 */
	public static void close(Connection connection, PreparedStatement presta, ResultSet reset) {

		try {
			if (presta != null) {
				presta.close();
			}
			if (connection != null) {
				connection.close();
			}
			if (reset != null) {
				reset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
