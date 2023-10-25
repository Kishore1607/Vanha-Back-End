package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.model.User;
import in.fssa.vanha.util.ConnectionUtil;
import in.fssa.vanha.util.PasswordUtil;

public class UserDAO {

	String newDateFormat = "yyyy-MM-dd HH:mm:ss"; // DATETIME format
	SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);

	/**
	 * Creates a new user by inserting their information into the database.
	 *
	 * @param newClient The User object containing the new user's information.
	 * @return The User object with the newly generated user ID.
	 * @throws PersistenceException if there is an issue with the database
	 *                              connection or SQL execution.
	 */
	public User create(User newClient) throws PersistenceException {

		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "Insert Into users (username, email, password, number, location, image, status, created_at, modified_at) Values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, newClient.getName());
			pre.setString(2, newClient.getEmail());

			String password = PasswordUtil.hashPassword(newClient.getPassword());

			pre.setString(3, password);
			pre.setLong(4, newClient.getNumber());
			pre.setString(5, newClient.getLocation());
			pre.setString(6, newClient.getImage());
			pre.setBoolean(7, true);

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(8, formattedDateTime);
			pre.setString(9, formattedDateTime);

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
		return newClient;
	}

	/**
	 * Updates user information in the database, including their username, phone
	 * number, and location.
	 *
	 * @param updateUser The User object containing the updated user information.
	 * @throws PersistenceException if there is an issue with the database
	 *                              connection or SQL execution.
	 */
	public void update(User updateUser) throws PersistenceException {

		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "UPDATE users SET username=?, number=? , location=?, modified_at=? WHERE email=?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, updateUser.getName());
			pre.setLong(2, updateUser.getNumber());
			pre.setString(3, updateUser.getLocation());

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(4, formattedDateTime);
			pre.setString(5, updateUser.getEmail());
			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * Updates a user's profile image in the database.
	 *
	 * @param updateImage The User object containing the updated user image and
	 *                    email.
	 * @throws PersistenceException if there is an issue with the database
	 *                              connection or SQL execution.
	 */
	public void updateImage(User updateImage) throws PersistenceException {
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "UPDATE users SET image=?, modified_at=? WHERE email=?";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, updateImage.getImage());

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(2, formattedDateTime);
			pre.setString(3, updateImage.getEmail());
			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * Retrieves user information from the database based on their email address.
	 *
	 * @param email The email address of the user to retrieve.
	 * @return The User object containing the user's information, or null if the
	 *         user is not found.
	 * @throws PersistenceException if there is an issue with the database
	 *                              connection or SQL execution.
	 */
	public User findUser(String email) throws PersistenceException {

		User value = null;
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {

			String query = "SELECT id, username, email, location, number, image FROM users WHERE status = 1 And email = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, email);
			rs = pre.executeQuery();
			if (rs.next()) {
				value = new User();
				value.setId(rs.getInt("id"));
				value.setName(rs.getString("username"));
				value.setEmail(rs.getString("email"));
				value.setNumber(rs.getLong("number"));
				value.setLocation(rs.getString("location"));
				value.setImage(rs.getString("image"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return value;
	}

	/**
	 * Authenticates a user by checking their email and password against the
	 * database records.
	 *
	 * @param user The User object containing the user's email and password for
	 *             authentication.
	 * @return The User object with authenticated user information, or null if
	 *         authentication fails.
	 * @throws PersistenceException if there is an issue with the database
	 *                              connection or SQL execution.
	 */
	public User loginUser(User user) throws PersistenceException {
		User value = null;
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {

			String query = "SELECT username, email, location, number, image, password FROM users WHERE status = 1 AND email = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, user.getEmail());
			rs = pre.executeQuery();

			if (rs.next()) {
				String hashedPasswordFromDB = rs.getString("password");
				boolean passwordMatches = PasswordUtil.checkPassword(user.getPassword(), hashedPasswordFromDB);

				if (passwordMatches) {
					value = new User();
					value.setName(rs.getString("username"));
					value.setEmail(rs.getString("email"));
					value.setNumber(rs.getLong("number"));
					value.setLocation(rs.getString("location"));
					if (rs.getString("image") == null) {
						value.setImage("null");
					} else {
						value.setImage(rs.getString("image"));
					}
				}
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return value;
	}

	/**
	 * Retrieves detailed user information from the database based on their unique
	 * user ID.
	 *
	 * @param id The unique identifier of the user to retrieve.
	 * @return The User object containing the user's detailed information, or null
	 *         if the user is not found.
	 * @throws PersistenceException if there is an issue with the database
	 *                              connection or SQL execution.
	 */
	public User userDetail(int id) throws PersistenceException {
		User value = null;
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {
			conn = ConnectionUtil.getConnection();
			String query = "SELECT username, email, location, number, image FROM users WHERE id = ?";
			pre = conn.prepareStatement(query);
			pre.setInt(1, id);
			rs = pre.executeQuery();

			if (rs.next()) {
				value = new User();
				value.setName(rs.getString("username"));
				value.setEmail(rs.getString("email"));
				value.setNumber(rs.getLong("number"));
				value.setLocation(rs.getString("location"));
				value.setImage(rs.getString("image"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return value;
	}

}
