package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.fssa.vanha.model.ProductAsset;
import in.fssa.vanha.util.ConnectionUtil;

public class ProductAssetDAO {

	public void create(ProductAsset newProductAsset) {
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
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

}
