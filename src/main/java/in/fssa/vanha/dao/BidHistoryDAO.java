package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.BidDTO;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.model.YourListDTO;
import in.fssa.vanha.util.ConnectionUtil;

public class BidHistoryDAO {

	String newDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);

	/**
	 * 
	 * @param newBid
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public void create(int amount, String eamil, String productID)
			throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "Insert Into bid_history (bid_amount, bid_date, buyer_id, product_id, status) "
					+ "Values(?, ?, ?, ?, 1)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, amount);

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(2, formattedDateTime);

			int buyer = UserDAO.findUser(eamil).getId();
			pre.setInt(3, buyer);

			int product = ProductDAO.findId(productID);
			pre.setInt(4, product);

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * 
	 * @param productId
	 * @return Set<BidHistory>
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public static List<BidDTO> findAllBidsByProductId(int productId)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<BidDTO> bidHistoryArray = new ArrayList<>();

		try {
			String query = "SELECT b.id, b.bid_amount, u.username, u.image, u.number " + "FROM bid_history b "
					+ "INNER JOIN users u ON b.buyer_id = u.id " + "WHERE b.status = 1 AND b.product_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();
			while (rs.next()) {
				BidDTO bidHistory = new BidDTO();
				bidHistory.setId(rs.getInt("id"));
				bidHistory.setAmount(rs.getInt("bid_amount"));
				bidHistory.setBuyerName(rs.getString("username"));
				bidHistory.setBuyerImage(rs.getString("image"));
				bidHistory.setBuyerNumber(rs.getLong("number"));

				bidHistoryArray.add(bidHistory);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return bidHistoryArray;
	}

	public static List<BidDTO> findAllBids(int productId)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<BidDTO> bidHistoryArray = new ArrayList<>();

		try {
			String query = "SELECT b.id, b.bid_amount " + "u.username, u.image " + "FROM bid_history b "
					+ "INNER JOIN users u ON b.buyer_id = u.id " + "WHERE b.status = 1 AND b.product_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();

			while (rs.next()) {
				BidDTO bidHistory = new BidDTO();
				bidHistory.setId(rs.getInt("id"));
				bidHistory.setAmount(rs.getInt("bid_amount"));
				bidHistory.setBuyerName(rs.getString("username"));
				bidHistory.setBuyerImage(rs.getString("image"));

				bidHistoryArray.add(bidHistory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return bidHistoryArray;
	}

	public static List<YourListDTO> myProductList(String userEmail)
			throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<YourListDTO> productArray = new ArrayList<>();

		try {

			String query = "SELECT product_id FROM bid_history WHERE buyer_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			int id = UserDAO.findUser(userEmail).getId();
			pre.setInt(1, id);
			rs = pre.executeQuery();

			Set<Integer> ids = new HashSet<Integer>();

			while (rs.next()) {
				ids.add(rs.getInt("product_id"));
			}

			for (int i : ids) {
				YourListDTO product = new YourListDTO();

				Product cardProduct = ProductDAO.list(i);

				product.setProductId(cardProduct.getProductId());
				product.setName(cardProduct.getName());
				product.setImage(AssetsDAO.findFirstAssetByProductId(i));
				productArray.add(product);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return productArray;
	}

}
