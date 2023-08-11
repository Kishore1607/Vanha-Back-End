package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import in.fssa.vanha.enumPackage.Category;
import in.fssa.vanha.enumPackage.UsedDuration;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.model.ProductAsset;
import in.fssa.vanha.service.AssetsService;
import in.fssa.vanha.service.ProductAssetService;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.util.ConnectionUtil;

public class ProductDAO {

    String newDateFormat = "yyyy-MM-dd HH:mm:ss"; // DATETIME format
    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);


    public void create(Product newProduct, Assets newAsset) throws Exception {
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet generatedKeys = null;
        int generatedProductId = -1; // Initialize to a default value

        try {
            String query = "INSERT INTO products (product_id, category, used_period, used_duration, description, name, price, min_price, seller_id, status, created_at, modified_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'a', ?, ?)";
            conn = ConnectionUtil.getConnection();
            pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pre.setString(1, newProduct.getProductId());

            char categoryChar = Category.getCate(newProduct.getCategory());
            pre.setString(2, String.valueOf(categoryChar));

            pre.setInt(3, newProduct.getUsedPeriod());

            char usedDuration = UsedDuration.used(newProduct.getUsedDuration());
            pre.setString(4, String.valueOf(usedDuration));

            pre.setString(5, newProduct.getDescription());
            pre.setString(6, newProduct.getName());
            pre.setInt(7, newProduct.getPrice());
            pre.setInt(8, newProduct.getMinPrice());

            int userId = UserService.findUserByEmail(newProduct.getSellerUnique()).getId();
            pre.setInt(9, userId);

            String nowDate = "" + LocalDateTime.now();
            LocalDateTime dateTime = LocalDateTime.parse(nowDate);
            String formattedDateTime = targetFormatter.format(dateTime);

            pre.setString(10, formattedDateTime);
            pre.setString(11, formattedDateTime);

            pre.executeUpdate();

            // Retrieve the generated product ID
            generatedKeys = pre.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedProductId = generatedKeys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionUtil.close(conn, pre, generatedKeys);
        }

        int pId = generatedProductId;
        
        AssetsService assetsService = new AssetsService();
		int sId = assetsService.create(newAsset);
		
		ProductAsset productAsset = new ProductAsset();
		
		productAsset.setProductId(pId);
		productAsset.setAssetId(sId);
		
		ProductAssetService PaService = new ProductAssetService();
		PaService.create(productAsset);
		
    }


	public static Product findProductByProductId(String productId) {

	    Product value = null;
	    Connection conn = null;
	    PreparedStatement pre = null;
	    ResultSet rs = null;

	    try {

	        String query = "Select * From products Where product_id = ? AND status = 'a'";
	        conn = ConnectionUtil.getConnection();
	        pre = conn.prepareStatement(query);
	        pre.setString(1, productId);
	        rs = pre.executeQuery();
	        if (rs.next()) {
	        	value = new Product();
	            value.setId(rs.getInt("id"));
	            value.setCategory(rs.getString("category"));
	            value.setUsedPeriod(rs.getInt("used_period"));
	            value.setUsedDuration(rs.getString("used_duration"));
	            value.setDescription(rs.getString("description"));
	            value.setName(rs.getString("name"));
	            value.setPrice(rs.getInt("price"));
	            value.setMinPrice(rs.getInt("min_price"));
	            value.setSellerId(rs.getInt("seller_id"));
	            value.setStatus(rs.getString("status").charAt(0));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println(e.getMessage());
	        throw new RuntimeException("Error while running SQL query.");
	    } finally {
	        ConnectionUtil.close(conn, pre, rs);
	    }
	    return value;
	}

	public Set<Product> findAllProductsBySellerId(String sellerId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<Product> productArray = new HashSet<>();
		

		try {
			
			int userId = UserService.findUserByEmail(sellerId).getId();

			String query = "Select * From products Where status = 'a' AND seller_id = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, userId);
			rs = pre.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setProductId(rs.getString("product_id"));	
				product.setCategory(rs.getString("category"));
				product.setUsedPeriod(rs.getInt("used_period"));
				product.setUsedDuration(rs.getString("used_duration"));
				product.setDescription(rs.getString("description"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setMinPrice(rs.getInt("min_price"));
				product.setSellerId(rs.getInt("seller_id"));
				product.setCreatedAt(rs.getString("created_at"));
				product.setModifiedAt(rs.getString("modified_at"));
				productArray.add(product);
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
		return productArray;
	}

	public void update(Product updateProduct) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "UPDATE products SET used_period=?, used_duration=? , description=?, name=?, price=?, min_price=?, modified_at=? "
					+ "WHERE product_id=?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, updateProduct.getUsedPeriod());
			
			char usedDuration = UsedDuration.used(updateProduct.getUsedDuration());
			pre.setString(2, String.valueOf(usedDuration));
			
			pre.setString(3, updateProduct.getDescription());
			pre.setString(4, updateProduct.getName());
			pre.setInt(5, updateProduct.getPrice());
			pre.setInt(6, updateProduct.getMinPrice());

			String nowDate = "" + LocalDateTime.now();
	        LocalDateTime dateTime = LocalDateTime.parse(nowDate);
	        String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(7, formattedDateTime);
			pre.setString(8, updateProduct.getProductId());
			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}
	

	public void delete(String productId) {
	    Connection conn = null;
	    PreparedStatement pre = null;

	    try {
	        String query = "UPDATE products SET status = 'd' WHERE product_id = ?";
	        conn = ConnectionUtil.getConnection();
	        pre = conn.prepareStatement(query);
	        pre.setString(1, productId);
	        pre.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally {
	        ConnectionUtil.close(conn, pre);
	    }

	    int id = findProductByProductId(productId).getId();

	    Connection conn1 = null;
	    PreparedStatement pre1 = null;

	    try {
	        String query = "UPDATE bid_history SET status = 0 WHERE product_id = ?";
	        conn1 = ConnectionUtil.getConnection();
	        pre1 = conn1.prepareStatement(query);
	        pre1.setInt(1, id);
	        pre1.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } finally {
	        ConnectionUtil.close(conn1, pre1);
	    }
	}


}
