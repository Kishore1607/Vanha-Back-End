package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import in.fssa.vanha.model.Assets;
import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.util.ConnectionUtil;

public class AssetsDAO {

	public int create(Assets newAsset) {
	    Connection conn = null;
	    PreparedStatement pre = null;
	    int generatedKey = -1; // Initialize to a default value

	    try {
	        String query = "INSERT INTO assets (url) VALUES (?)";
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
	        throw new RuntimeException(e);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally {
	        ConnectionUtil.close(conn, pre);
	    }

	    return generatedKey;
	}
	
	public Set<Assets> findAllAssetsByProductId(String productId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<Assets> assetsArray = new HashSet<>();
		

		try {
			
			int product = ProductService.findByProductId(productId).getId();

			String query = "SELECT * FROM product_assets AS pa "
				    + "INNER JOIN assets AS a ON pa.asset_id = a.id "
				    + "WHERE pa.product_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, product);
			rs = pre.executeQuery();
			while (rs.next()) {
				Assets assets = new Assets();
				assets.setId(rs.getInt("id"));
				assets.setValue(rs.getString("url"));
				assetsArray.add(assets);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return assetsArray;
	}


}
