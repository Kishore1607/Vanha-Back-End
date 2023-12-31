package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	 * Creates a new bid entry in the bid history for a specified amount and
	 * product.
	 *
	 * @param amount    The bid amount to be placed.
	 * @param email     The email of the user placing the bid.
	 * @param productID The unique identifier of the product being bid on.
	 *
	 * @throws PersistenceException If there is an issue with the database
	 *                              persistence.
	 * @throws ValidationException  If there is a validation error in the input
	 *                              data.
	 * @throws ServiceException     If a service-level error occurs during bid
	 *                              creation.
	 */
	public void create(int amount, String eamil, String productID)
			throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "Insert Into bid_history (bid_amount, bid_date, buyer_id, product_id, list_no, status) "
					+ "Values(?, ?, ?, ?, ?, 1)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, amount);

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(2, formattedDateTime);

			UserDAO userDAO = new UserDAO();
			int buyer = userDAO.findUser(eamil).getId();
			pre.setInt(3, buyer);

			ProductDAO productDAO = new ProductDAO();
			int product = productDAO.findId(productID);
			pre.setInt(4, product);

			BidHistoryDAO bidHistoryDAO = new BidHistoryDAO();
			int num = bidHistoryDAO.findListNo(product);
			pre.setInt(5, num + 1);

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * Retrieves a list of bid history entries for a specific product by its product
	 * ID.
	 *
	 * @param productId The unique identifier of the product.
	 * @return A list of BidDTO objects representing bid history entries for the
	 *         specified product.
	 *
	 * @throws PersistenceException If there is an issue with the database
	 *                              persistence.
	 * @throws ServiceException     If a service-level error occurs during data
	 *                              retrieval.
	 * @throws ValidationException  If there is a validation error in the input
	 *                              data.
	 */
	public List<BidDTO> findAllBidsByProductId(int productId)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<BidDTO> bidHistoryArray = new ArrayList<>();

		try {
			String query = "SELECT b.id, b.bid_amount, b.bid_date, b.list_no, u.username, u.image, u.email "
					+ "FROM bid_history b " + "INNER JOIN users u ON b.buyer_id = u.id "
					+ "WHERE b.status = 1 AND b.product_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();
			while (rs.next()) {
				BidDTO bidHistory = new BidDTO();
				bidHistory.setId(rs.getInt("b.id"));
				bidHistory.setAmount(rs.getInt("b.bid_amount"));
				bidHistory.setBuyerName(rs.getString("u.username"));
				bidHistory.setBuyerImage(rs.getString("u.image"));
				bidHistory.setBuyerEmail(rs.getString("u.email"));
				Timestamp timestamp = rs.getTimestamp("b.bid_date");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = dateFormat.format(new Date(timestamp.getTime()));
				bidHistory.setDate(dateString);
				bidHistory.setListNo(rs.getInt("b.list_no"));

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

	/**
	 * Retrieves a list of bid history entries for a specific product by its product
	 * ID, including essential bid details.
	 *
	 * @param productId The unique identifier of the product.
	 * @return A list of BidDTO objects representing bid history entries for the
	 *         specified product, including bid amount, buyer username, and buyer
	 *         image.
	 *
	 * @throws PersistenceException If there is an issue with the database
	 *                              persistence.
	 * @throws ServiceException     If a service-level error occurs during data
	 *                              retrieval.
	 * @throws ValidationException  If there is a validation error in the input
	 *                              data.
	 */
	public List<BidDTO> findAllBids(int productId) throws PersistenceException, ServiceException, ValidationException {
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

	/**
	 * Retrieves a list of products that a user has placed bids on, based on their
	 * email.
	 *
	 * @param userEmail The email of the user.
	 * @return A list of YourListDTO objects representing products the user has
	 *         placed bids on.
	 *
	 * @throws PersistenceException If there is an issue with the database
	 *                              persistence.
	 * @throws ValidationException  If there is a validation error in the input
	 *                              data.
	 * @throws ServiceException     If a service-level error occurs during data
	 *                              retrieval.
	 */
	public List<YourListDTO> myProductList(String userEmail)
			throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<YourListDTO> productArray = new ArrayList<>();

		try {

			String query = "SELECT product_id FROM bid_history WHERE buyer_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			UserDAO userDAO = new UserDAO();
			int id = userDAO.findUser(userEmail).getId();
			pre.setInt(1, id);
			rs = pre.executeQuery();

			Set<Integer> ids = new HashSet<Integer>();

			while (rs.next()) {
				ids.add(rs.getInt("product_id"));
			}

			for (int i : ids) {
				YourListDTO product = new YourListDTO();

				ProductDAO productDAO = new ProductDAO();
				Product cardProduct = productDAO.list(i);
				if (cardProduct != null) {
					product.setProductId(cardProduct.getProductId());
					product.setName(cardProduct.getName());

					AssetsDAO assetDAO = new AssetsDAO();
					product.setImage(assetDAO.findFirstAssetByProductId(i));
					product.setStatus(cardProduct.getStatus());
					productArray.add(product);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return productArray;
	}

	/**
	 * Finds the highest list number (list_no) associated with a specific product.
	 *
	 * @param productId The unique identifier of the product.
	 * @return The highest list number for the product, or 0 if no entries are
	 *         found.
	 *
	 * @throws PersistenceException If there is an issue with the database
	 *                              persistence.
	 * @throws ServiceException     If a service-level error occurs during data
	 *                              retrieval.
	 * @throws ValidationException  If there is a validation error in the input
	 *                              data.
	 */
	public int findListNo(int productId) throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {
			String query = "SELECT MAX(list_no) AS max_list_no  FROM bid_history WHERE product_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();

			if (rs.next()) {
				return rs.getInt("max_list_no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return 0;
	}

	/**
	 * Finds the product and buyer associated with a specific bid entry.
	 *
	 * @param bidID The unique identifier of the bid entry.
	 * @return A string containing the product ID and buyer ID in the format
	 *         "productID/buyerID".
	 *
	 * @throws PersistenceException If there is an issue with the database
	 *                              persistence.
	 * @throws ServiceException     If a service-level error occurs during data
	 *                              retrieval.
	 * @throws ValidationException  If there is a validation error in the input
	 *                              data.
	 */
	public String findRow(int bidID) throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		int pId = 0;
		int bId = 0;

		try {
			String query = "SELECT product_id, buyer_id FROM bid_history WHERE id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, bidID);
			rs = pre.executeQuery();

			if (rs.next()) {
				pId = rs.getInt("product_id");
				bId = rs.getInt("buyer_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return pId + "/" + bId;
	}

}
