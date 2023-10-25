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
	 * Inserts a list of new assets into the database, returning an array of
	 * generated keys for each asset.
	 *
	 * @param newAssets A list of Assets objects to be inserted.
	 * @return An array of generated keys corresponding to the inserted assets.
	 * @throws PersistenceException If there is an issue with the database
	 *                              connection or SQL operations.
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
	 * Retrieves a list of Assets objects associated with a specific product by its
	 * ID.
	 *
	 * @param id The ID of the product for which assets need to be retrieved.
	 * @return A list of Assets objects associated with the product.
	 * @throws PersistenceException If there is an issue with the database
	 *                              connection or SQL operations.
	 * @throws ServiceException     If a service-level issue occurs.
	 * @throws ValidationException  If there is an issue with input validation.
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
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return assetsArray;
	}

	/**
	 * Updates an asset by either inserting a new asset or updating an existing one,
	 * based on the asset's ID.
	 *
	 * @param updateAsset The Asset object to be updated or inserted.
	 * @param id          The ID of the product associated with the asset.
	 * @throws PersistenceException If there is an issue with the database
	 *                              connection or SQL operations.
	 * @throws RuntimeException     If a runtime issue occurs.
	 * @throws ServiceException     If a service-level issue occurs.
	 * @throws ValidationException  If there is an issue with input validation.
	 */
	public void updateAssetsByAssetId(Assets updateAsset, int id)
			throws PersistenceException, RuntimeException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre1 = null;
		PreparedStatement pre2 = null;
		PreparedStatement pre3 = null;

		if (updateAsset.getId() == 0) {
			try {
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

	/**
	 * Retrieves the URL of the first asset associated with a specific product by
	 * its ID.
	 *
	 * @param productId The ID of the product for which the first asset URL needs to
	 *                  be retrieved.
	 * @return The URL of the first asset associated with the product, or null if
	 *         none is found.
	 * @throws PersistenceException If there is an issue with the database
	 *                              connection or SQL operations.
	 * @throws ServiceException     If a service-level issue occurs.
	 * @throws ValidationException  If there is an issue with input validation.
	 */
	public String findFirstAssetByProductId(int productId)
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
