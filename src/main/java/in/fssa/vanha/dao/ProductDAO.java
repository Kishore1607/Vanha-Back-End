package in.fssa.vanha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.fssa.vanha.enumPackage.Category;
import in.fssa.vanha.enumPackage.UsedDuration;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.BidDTO;
import in.fssa.vanha.model.ListProductDTO;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.model.ProductAsset;
import in.fssa.vanha.model.ProductDetailDTO;
import in.fssa.vanha.service.AssetsService;
import in.fssa.vanha.service.ProductAssetService;
import in.fssa.vanha.util.ConnectionUtil;

public class ProductDAO {

	String newDateFormat = "yyyy-MM-dd HH:mm:ss"; // DATETIME format
	SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);

	/**
	 * 
	 * @param newProduct
	 * @param newAsset
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void create(Product newProduct, List<Assets> newAsset, String userEmail)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet generatedKeys = null;
		int generatedProductId = -1;

		try {
			String query = "INSERT INTO products (product_id,  name,  description, price, used_period, used_duration, category, min_price, seller_id, status, created_at, modified_at) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'a', ?, ?)";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pre.setString(1, newProduct.getProductId());
			pre.setString(2, newProduct.getName());
			pre.setString(3, newProduct.getDescription());
			pre.setInt(4, newProduct.getPrice());
			pre.setInt(5, newProduct.getUsedPeriod());
			pre.setString(6, UsedDuration.used(newProduct.getUsedDuration()));
			pre.setString(7, Category.getCate(newProduct.getCategory()));

			pre.setInt(8, newProduct.getMinPrice());

			int id = UserDAO.findUser(userEmail).getId();
			pre.setInt(9, id);

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(10, formattedDateTime);
			pre.setString(11, formattedDateTime);

			pre.executeUpdate();

			generatedKeys = pre.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedProductId = generatedKeys.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, generatedKeys);
		}

		int pId = generatedProductId;

		AssetsService assetsService = new AssetsService();

		int[] sId = assetsService.create(newAsset);

		for (int assetId : sId) {
			ProductAsset productAsset = new ProductAsset();
			productAsset.setProductId(pId);
			productAsset.setAssetId(assetId);
			ProductAssetService PaService = new ProductAssetService();
			PaService.create(productAsset);
		}

	}

	/**
	 * 
	 * @param productId
	 * @return Product
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public ProductDetailDTO findProductByProductId(String productId)
			throws PersistenceException, ServiceException, ValidationException {
		ProductDetailDTO result = null;
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {
			String query = "SELECT p.id, p.product_id, p.used_period, p.used_duration, p.description, p.name, p.price, p.min_price, p.category, "
					+ "p.seller_id, u.username, u.email, u.location, u.image " + "FROM products p "
					+ "INNER JOIN users u ON p.seller_id = u.id " + "WHERE p.product_id = ? AND p.status = 'a';";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, productId);
			rs = pre.executeQuery();

			if (rs.next()) {
				result = new ProductDetailDTO();
				int id = rs.getInt("id");
				result.setId(id);
				result.setProductId(rs.getString("product_id"));
				result.setUsedPeriod(rs.getInt("used_period"));
				String duration = UsedDuration.getFullValue(rs.getString("used_duration"));
				result.setUsedDuration(duration);
				result.setDescription(rs.getString("description"));
				result.setProductName(rs.getString("name"));
				result.setPrice(rs.getInt("price"));
				result.setMinPrice(rs.getInt("min_price"));
				String category = Category.getCategoryString(rs.getString("category"));
				result.setCategory(category);

				result.setSellerName(rs.getString("username"));
				result.setSellerLocation(rs.getString("location"));
				result.setSellerImage(rs.getString("image"));

				AssetsDAO assetDAO = new AssetsDAO();
				List<Assets> assets = assetDAO.findAllAssetsByProductId(id);
				result.setAssets(assets);

				List<BidDTO> bids = BidHistoryDAO.findAllBidsByProductId(id);
				result.setBids(bids);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}

		return result;
	}

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public Set<ListProductDTO> findAllProducts(String userEmail)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<ListProductDTO> productArray = new HashSet<>();

		try {

			String query = "SELECT p.id, p.product_id, p.name, p.price, "
					+ "       p.seller_id, u.username, u.location, u.image " + "FROM products p "
					+ "INNER JOIN users u ON p.seller_id = u.id " + "WHERE p.status = 'a' AND NOT u.email = ?;";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, userEmail);
			rs = pre.executeQuery();

			while (rs.next()) {

				int productId = rs.getInt("id");

				ListProductDTO product = new ListProductDTO();

				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));

				product.setSellerName(rs.getString("username"));
				product.setSellerLocation(rs.getString("location"));
				product.setSellerImage(rs.getString("image"));

				product.setAsset(AssetsDAO.findFirstAssetByProductId(productId));

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

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public Set<ListProductDTO> findAllProducts() throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<ListProductDTO> productArray = new HashSet<>();

		try {

			String query = "SELECT p.id, p.product_id, p.name, p.price, \r\n"
					+ "       p.seller_id, u.username, u.location, u.image\r\n" + "FROM products p\r\n"
					+ "INNER JOIN users u ON p.seller_id = u.id\r\n" + "WHERE p.status = 'a';";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			rs = pre.executeQuery();

			while (rs.next()) {

				int productId = rs.getInt("id");

				ListProductDTO product = new ListProductDTO();

				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));

				product.setSellerName(rs.getString("username"));
				product.setSellerLocation(rs.getString("location"));
				product.setSellerImage(rs.getString("image"));

				product.setAsset(AssetsDAO.findFirstAssetByProductId(productId));

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

	/**
	 * 
	 * @param sellerId
	 * @return Set<Product>
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public List<ListProductDTO> findAllProductsBySellerId(String userEmail)
			throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<ListProductDTO> productArray = new ArrayList<>();

		try {

			String query = "SELECT id, product_id, name, price  FROM products WHERE seller_id = ? AND status = 'a';";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			int id = UserDAO.findUser(userEmail).getId();
			pre.setInt(1, id);
			rs = pre.executeQuery();
			while (rs.next()) {

				int productId = rs.getInt("id");

				ListProductDTO product = new ListProductDTO();

				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));

				product.setAsset(AssetsDAO.findFirstAssetByProductId(productId));

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

	/**
	 * 
	 * @param updateProduct
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public void update(Product updateProduct)
			throws PersistenceException, ServiceException, ValidationException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "UPDATE products SET used_period=?, used_duration=? , description=?, name=?, price=?, min_price=?, modified_at=? "
					+ "WHERE product_id=?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, updateProduct.getUsedPeriod());
			pre.setString(2, UsedDuration.used(updateProduct.getUsedDuration()));
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
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * 
	 * @param productId
	 * @throws PersistenceException
	 */
	@SuppressWarnings("resource")
	public void delete(String productId) throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;

		int id = -1;

		try {
			conn = ConnectionUtil.getConnection();
			conn.setAutoCommit(false);

			String productIdQuery = "SELECT id FROM products WHERE product_id = ?";
			pre = conn.prepareStatement(productIdQuery);
			pre.setString(1, productId);
			ResultSet resultSet = pre.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}

			if (id != -1) {
				String updateProductsQuery = "UPDATE products SET status = 'd' WHERE product_id = ?";
				pre = conn.prepareStatement(updateProductsQuery);
				pre.setString(1, productId);
				pre.executeUpdate();

				String updateBidHistoryQuery = "UPDATE bid_history SET status = 0 WHERE product_id = ?";
				pre = conn.prepareStatement(updateBidHistoryQuery);
				pre.setInt(1, id);
				pre.executeUpdate();

				conn.commit(); // Commit the changes
			} else {
				throw new ServiceException("Product Does not exists");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * 
	 * @param category
	 * @return Set<Product>
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public Set<ListProductDTO> findAllProductsByCategory(String category, String userEmail)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<ListProductDTO> productArray = new HashSet<>();

		int id = UserDAO.findUser(userEmail).getId();
		String cate = Category.getCate(category);

		try {
			String query = "SELECT p.id, p.product_id, p.name, p.price,\r\n"
					+ "       p.seller_id, u.username, u.location, u.image\r\n" + "FROM products p\r\n"
					+ "INNER JOIN users u ON p.seller_id = u.id\r\n"
					+ "WHERE p.status = 'a' AND p.category = ? AND NOT u.email = ?;";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, cate);
			pre.setInt(2, id);
			rs = pre.executeQuery();

			while (rs.next()) {

				int productId = rs.getInt("id");

				ListProductDTO product = new ListProductDTO();

				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));

				product.setSellerName(rs.getString("username"));
				product.setSellerLocation(rs.getString("location"));
				product.setSellerImage(rs.getString("image"));

				product.setAsset(AssetsDAO.findFirstAssetByProductId(productId));

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

	public Set<ListProductDTO> findAllProductsByCategory(String category)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<ListProductDTO> productArray = new HashSet<>();

		String cate = Category.getCate(category);

		try {
			String query = "SELECT p.id, p.product_id, p.name, p.price,\r\n"
					+ "       p.seller_id, u.username, u.location, u.image\r\n" + "FROM products p\r\n"
					+ "INNER JOIN users u ON p.seller_id = u.id\r\n" + "WHERE p.status = 'a' AND p.category = ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, cate);
			rs = pre.executeQuery();

			while (rs.next()) {

				int productId = rs.getInt("id");

				ListProductDTO product = new ListProductDTO();

				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));

				product.setSellerName(rs.getString("username"));
				product.setSellerLocation(rs.getString("location"));
				product.setSellerImage(rs.getString("image"));

				product.setAsset(AssetsDAO.findFirstAssetByProductId(productId));

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

	/**
	 * 
	 * @param productId
	 * @return
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public static int findId(String productId) throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		int id = -1;

		try {
			String query = "SELECT id" + " FROM products " + "WHERE product_id = ? AND status = 'a';";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, productId);
			rs = pre.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}

		return id;
	}

	// Extra method for Validation

	public static Product methodForValidation(String productId)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Product product = null;

		try {
			String query = "SELECT id, product_id, min_price, seller_id, name, price, description, used_period, used_duration"
					+ " FROM products " + "WHERE product_id = ? AND status = 'a';";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, productId);
			rs = pre.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setProductId(rs.getString("product_id"));
				product.setMinPrice(rs.getInt("min_price"));
				product.setSellerId(rs.getInt("seller_id"));
				product.setPrice(rs.getInt("price"));
				product.setUsedPeriod(rs.getInt("used_period"));
				product.setUsedDuration(UsedDuration.getFullValue(rs.getString("used_duration")));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}

		return product;
	}

	public static boolean isActive(int productId) throws PersistenceException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {
			String query = "SELECT 1 FROM products WHERE status = 'a' AND id = ?";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}
		return false;
	}

}
