package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.model.ProductAsset;
import in.fssa.vanha.util.ConnectionUtil;

public class ProductAssetDAO {

	/**
	 * 
	 * @param newProductAsset
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public void create(ProductAsset newProductAsset) throws PersistenceException{
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "Insert Into product_assets (product_id, asset_id) " + "Values(?, ?)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);

			pre.setInt(1, newProductAsset.getProductId());
			pre.setInt(2, newProductAsset.getAssetId());

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

}
