package in.fssa.vanha.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionUtil {
	public static Connection getConnection() {

		String url;
		String userName;
		String passWord;

		if (System.getenv("CI") != null) {
			url = System.getenv("DATABASE_HOST");
			userName = System.getenv("DATABASE_USERNAME");
			passWord = System.getenv("DATABASE_PASSWORD");
		} else {
			Dotenv env = Dotenv.load();
			url = env.get("DATABASE_HOST");
			userName = env.get("DATABASE_USERNAME");
			passWord = env.get("DATABASE_PASSWORD");
		}

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, userName, passWord);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("e");
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

	public static void close(Connection conn, PreparedStatement pre1, PreparedStatement pre2, PreparedStatement pre3,
			PreparedStatement pre4, PreparedStatement pre5) {

		try {
			if (pre1 != null) {
				pre1.close();
			}
			if (pre2 != null) {
				pre2.close();
			}
			if (pre3 != null) {
				pre3.close();
			}
			if (pre4 != null) {
				pre4.close();
			}
			if (pre5 != null) {
				pre5.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
