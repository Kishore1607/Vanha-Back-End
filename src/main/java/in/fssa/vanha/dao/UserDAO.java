package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import in.fssa.vanha.model.User;
import in.fssa.vanha.util.ConnectionUtil;

public class UserDAO {

	 String newDateFormat = "yyyy-MM-dd HH:mm:ss"; // DATETIME format
     SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
     DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);

	public void create(User newClient) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "Insert Into users (username, email, password, number, location, status, created_at, modified_at) Values(?, ?, ?, ?, ?, ?, ?, ?)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, newClient.getName());
			pre.setString(2, newClient.getEmail());
			pre.setString(3, newClient.getPassword());
			pre.setLong(4, newClient.getNumber());
			pre.setString(5, newClient.getLocation());
			pre.setBoolean(6, true);

			String nowDate = "" + LocalDateTime.now();
	        LocalDateTime dateTime = LocalDateTime.parse(nowDate);
	        String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(7, formattedDateTime);
			pre.setString(8, formattedDateTime);

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	public void update(User updateUser) {
		// TODO Auto-generated method stub
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
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		User value = null;
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {

			String query = "Select * From users Where status = 1 And email = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, email);
			rs = pre.executeQuery();
			if (rs.next()) {
				value = new User();
				value.setId(rs.getInt("user_id"));
	            value.setName(rs.getString("username"));
	            value.setEmail(rs.getString("email"));
	            value.setPassword(rs.getString("password"));
	            value.setNumber(rs.getLong("number"));
	            value.setLocation(rs.getString("location"));
	            value.setStatus(rs.getBoolean("status"));
	            value.setCreatedAt(rs.getString("created_at"));
	            value.setModifiedAt(rs.getString("modified_at"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return value;
	}
//	
//	public static boolean findUserById(int id) {
//		
//		Connection conn = null;
//		PreparedStatement pre = null;
//		ResultSet rs = null;
//
//		try {
//
//			String query = "Select * From users Where user_id = ?";
//			conn = ConnectionUtil.getConnection();
//			pre = conn.prepareStatement(query);
//			pre.setInt(1, id);
//			rs = pre.executeQuery();
//			if (rs.next()) {
//				return true;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		} finally {
//			ConnectionUtil.close(conn, pre, rs);
//		}
//		return false;
//	}
}
