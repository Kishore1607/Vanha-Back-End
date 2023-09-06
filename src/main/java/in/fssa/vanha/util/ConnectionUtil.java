package in.fssa.vanha.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {

	/**
	 * 
	 * @return
	 */
	public static Connection getConnection() {

		String url;
		String userName;
		String passWord;

		url = System.getenv("DATABASE_HOST");
		userName = System.getenv("DATABASE_USERNAME");
		passWord = System.getenv("DATABASE_PASSWORD");

//		url = "jdbc:mysql://164.52.216.41:3306/kishore_sugumar_corejava_project";
//		userName = "UCqdEGfN4Qv8";
//		passWord = "11167b76-bd97-4ba1-8a6d-68129cfbf600";

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
	 * 
	 * @param conn
	 * @param pre1
	 * @param pre2
	 * @param pre3
	 * @param pre4
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
	 * 
	 * @param connection
	 * @param presta
	 * @param reset
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
