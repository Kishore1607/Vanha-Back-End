package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.BidHistory;
import in.fssa.vanha.service.*;
import in.fssa.vanha.util.ConnectionUtil;

public class BidHistoryDAO {
	
	String newDateFormat = "yyyy-MM-dd HH:mm:ss"; 
    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);

    /**
     * 
     * @param newBid
     * @throws PersistenceException
     * @throws ServiceException
     */
	public void create(BidHistory newBid) throws PersistenceException, ServiceException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "Insert Into bid_history (bid_amount, bid_date, buyer_id, product_id, status) "
					+ "Values(?, ?, ?, ?, 1)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, newBid.getBidAmount());
			
	        String nowDate = "" + LocalDateTime.now();
	        LocalDateTime dateTime = LocalDateTime.parse(nowDate);
	        String formattedDateTime = targetFormatter.format(dateTime);
			
			pre.setString(2, formattedDateTime);
			
			int buyer = UserService.findUserByEmail(newBid.getBuyerUnique()).getId();
			pre.setInt(3, buyer);
			
			int product = ProductService.findByProductId(newBid.getProductUnique()).getId();
			pre.setInt(4, product);

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}  catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
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
	public Set<BidHistory> findAllBidsByProductId(String productId) throws PersistenceException, ServiceException, ValidationException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		
		int product = ProductService.findByProductId(productId).getId();

		Set<BidHistory> bidHistoryArray = new HashSet<>();
		
		try {
			String query = "Select * From bid_history Where status = 1 AND product_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, product);
			rs = pre.executeQuery();
			while (rs.next()) {
				BidHistory bidHistory = new BidHistory();
				bidHistory.setBidId(rs.getInt("bid_id"));
				bidHistory.setBidAmount(rs.getInt("bid_amount"));
				bidHistory.setBidDate(rs.getString("bid_date"));
				bidHistory.setBuyerId(rs.getInt("buyer_id"));
				bidHistory.setProductId(rs.getInt("product_id"));
				bidHistoryArray.add(bidHistory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistenceException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new ServiceException(e);
		}finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return bidHistoryArray;
	}

}
