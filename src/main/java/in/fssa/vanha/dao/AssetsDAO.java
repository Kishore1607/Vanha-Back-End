package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.util.ConnectionUtil;

public class AssetsDAO {

	/**
	 * 
	 * @param newAsset
	 * @return int
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public int[] create(List<Assets> newAssets) throws PersistenceException {
		Connection conn = null;
		PreparedStatement pre = null;
		int[] generatedKeysArray = new int[newAssets.size()];
		int index = 0;

		try {
			String query = "INSERT INTO assets (url, status) VALUES (?, 1)";
			conn = ConnectionUtil.getConnection();

			for (Assets asset : newAssets) {
				pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pre.setString(1, asset.getValue());
				pre.executeUpdate();

				try (ResultSet generatedKeys = pre.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int generatedKey = generatedKeys.getInt(1);
						generatedKeysArray[index] = generatedKey;
						index++;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}

		return generatedKeysArray;
	}

	/**
	 * 
	 * @param productId
	 * @return Set<Assets>
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public List<Assets> findAllAssetsByProductId(int id)
			throws PersistenceException, ServiceException, ValidationException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<Assets> assetsArray = new ArrayList<>();

		try {

			String query = "SELECT url, id FROM product_assets AS pa INNER JOIN assets AS a ON pa.asset_id = a.id "
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
	 * @param id
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void updateAssetsByAssetId(Assets updateAsset, int id)
			throws PersistenceException, RuntimeException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre1 = null;
		PreparedStatement pre2 = null;
		PreparedStatement pre3 = null;

		if (updateAsset.getId() == 0) {
			try {
				System.out.println(1);
				conn = ConnectionUtil.getConnection();
				pre1 = conn.prepareStatement("INSERT INTO assets (url) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				pre2 = conn.prepareStatement("INSERT INTO product_assets (product_id, asset_id) VALUES (?, ?)");

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

				// Insert into 'product_assets' table
				pre2.setInt(1, id);
				pre2.setInt(2, generatedKey);
				pre2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				throw new PersistenceException(e);
			} finally {
				ConnectionUtil.close(conn, pre1, pre2);
			}

		} else {

			try {
				conn = ConnectionUtil.getConnection();
				pre3 = conn.prepareStatement("UPDATE assets SET url = ? WHERE id = ?");
				pre3.setString(1, updateAsset.getValue());
				pre3.setInt(2, updateAsset.getId());
				pre3.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new PersistenceException(e);
			} finally {
				ConnectionUtil.close(conn, pre3);
			}

		}
	}

	public static String findFirstAssetByProductId(int productId)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		String asset = null;

		try {
			String query = "SELECT a.id, a.url FROM product_assets AS pa "
					+ "INNER JOIN assets AS a ON pa.asset_id = a.id " + "WHERE pa.product_id = ? AND pa.status = 1 "
					+ "LIMIT 1";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();

			if (rs.next()) {
				asset = rs.getString("url");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}

		return asset;
	}

}
