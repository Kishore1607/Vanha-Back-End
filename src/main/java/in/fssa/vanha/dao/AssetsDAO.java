package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.ProductWithAssets;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.util.ConnectionUtil;

public class AssetsDAO {

	/**
	 * 
	 * @param newAsset
	 * @return int
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public int create(Assets newAsset) throws PersistenceException{
		Connection conn = null;
		PreparedStatement pre = null;
		int generatedKey = -1; // Initialize to a default value

		try {
			String query = "INSERT INTO assets (url, status) VALUES (?, 1)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pre.setString(1, newAsset.getValue());
			pre.executeUpdate();

			// Retrieve the generated key
			ResultSet generatedKeys = pre.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedKey = generatedKeys.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}

		return generatedKey;
	}

	/**
	 * 
	 * @param productId
	 * @return Set<Assets>
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public Set<Assets> findAllAssetsByProductId(int id) throws PersistenceException, ServiceException, ValidationException{
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<Assets> assetsArray = new HashSet<>();

		try {

			String query = "SELECT * FROM product_assets AS pa INNER JOIN assets AS a ON pa.asset_id = a.id "
					+ "WHERE pa.product_id = ? And pa.status = 1";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, id);
			rs = pre.executeQuery();
			while (rs.next()) {
				Assets assets = new Assets();
				assets.setId(rs.getInt("id"));
				assets.setValue(rs.getString("url"));
				assetsArray.add(assets);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return assetsArray;
	}

	/**
	 * 
	 * @param updateAsset
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @throws ValidationException 
	 */
	public void updateAssetsByAssetId(Assets updateAsset) throws PersistenceException, RuntimeException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre1 = null;
		PreparedStatement pre2 = null;
		PreparedStatement pre3 = null;
		PreparedStatement pre4 = null;
		PreparedStatement pre5 = null;

		try {
			conn = ConnectionUtil.getConnection();
			pre1 = conn.prepareStatement("INSERT INTO assets (url) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			pre2 = conn.prepareStatement("INSERT INTO product_assets (product_id, asset_id) VALUES (?, ?)");
			pre3 = conn.prepareStatement("SELECT id FROM assets WHERE url = ?");
			pre4 = conn.prepareStatement("UPDATE product_assets SET status = 0 WHERE product_id = ? AND asset_id = ?");
			pre5 = conn.prepareStatement("UPDATE assets SET status = 0 WHERE url = ?");

			// Insert into 'assets' table
			pre1.setString(1, updateAsset.getValue());
			pre1.executeUpdate();

			int generatedKey;
			try (ResultSet generatedKeys = pre1.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					generatedKey = generatedKeys.getInt(1);
				} else {
					throw new ServiceException("Failed to get generated key.");
				}
			}
			
			ProductWithAssets product = ProductService.findByProductId(updateAsset.getProductId());
			if(product == null) {
				throw new ServiceException("Product doesn't exists in product table");
			}
			int id = product.getProduct().getId();

			// Insert into 'product_assets' table
			pre2.setInt(1, id);
			pre2.setInt(2, generatedKey);
			pre2.executeUpdate();

			// Find asset_id by old value and update status in 'product_assets' table
			pre3.setString(1, updateAsset.getOldValue());
			try (ResultSet result1 = pre3.executeQuery()) {
				if (result1.next()) {
					int assetId = result1.getInt("id");
					pre4.setInt(1, id);
					pre4.setInt(2, assetId);
					pre4.executeUpdate();
				}
			}
			try (ResultSet result2 = pre3.executeQuery()) {
				if (result2.next()) {
					pre5.setString(1, updateAsset.getOldValue());
					pre5.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		}finally {
			ConnectionUtil.close(conn, pre1, pre2, pre3, pre4, pre5);
		}
	}
	
	

}
