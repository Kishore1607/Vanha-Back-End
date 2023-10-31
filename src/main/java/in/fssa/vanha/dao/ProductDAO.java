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
import in.fssa.vanha.model.ProductDetailDTO;
import in.fssa.vanha.util.ConnectionUtil;

public class ProductDAO {

	String newDateFormat = "yyyy-MM-dd HH:mm:ss"; // DATETIME format
	SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(newDateFormat);

	/**
	 * Creates a new product in the database associated with the given user.
	 *
	 * @param newProduct The Product to be created.
	 * @param userEmail  The email of the user creating the product.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public void create(Product newProduct, String userEmail)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet generatedKeys = null;

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

			UserDAO userDAO = new UserDAO();
			int id = userDAO.findUser(userEmail).getId();
			pre.setInt(9, id);

			String nowDate = "" + LocalDateTime.now();
			LocalDateTime dateTime = LocalDateTime.parse(nowDate);
			String formattedDateTime = targetFormatter.format(dateTime);

			pre.setString(10, formattedDateTime);
			pre.setString(11, formattedDateTime);

			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, generatedKeys);
		}

	}

	/**
	 * Retrieves a ProductDetailDTO for a product based on its product ID.
	 *
	 * @param productId The product ID to search for.
	 * @return A ProductDetailDTO containing detailed product information.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
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

				BidHistoryDAO bidHistoryDAO = new BidHistoryDAO();
				List<BidDTO> bids = bidHistoryDAO.findAllBidsByProductId(id);
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
	 * Retrieves a set of ListProductDTO objects for all products associated with a
	 * user (excluding their own products).
	 *
	 * @param userEmail The email of the user.
	 * @return A set of ListProductDTO representing products associated with the
	 *         user.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
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

				AssetsDAO assetsDAO = new AssetsDAO();
				product.setAsset(assetsDAO.findFirstAssetByProductId(productId));

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
	 * Retrieves a set of ListProductDTO objects for all products in the database.
	 *
	 * @return A set of ListProductDTO representing all products in the database.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public Set<ListProductDTO> findAllProducts() throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<ListProductDTO> productArray = new HashSet<>();

		try {

			String query = "SELECT p.id, p.product_id, p.name, p.price, p.created_at, \r\n"
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
				product.setDate(rs.getString("created_at"));

				product.setSellerName(rs.getString("username"));
				product.setSellerLocation(rs.getString("location"));
				product.setSellerImage(rs.getString("image"));

				AssetsDAO assetsDAO = new AssetsDAO();
				product.setAsset(assetsDAO.findFirstAssetByProductId(productId));

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
	 * Retrieves a list of ListProductDTO objects for all products associated with a
	 * user.
	 *
	 * @param userEmail The email of the user.
	 * @return A list of ListProductDTO representing products associated with the
	 *         user.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ValidationException  If validation of the input data fails.
	 * @throws ServiceException     If a service-related issue occurs.
	 */
	public List<ListProductDTO> findAllProductsBySellerId(String userEmail)
			throws PersistenceException, ValidationException, ServiceException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		List<ListProductDTO> productArray = new ArrayList<>();

		try {

			String query = "SELECT id, product_id, name, price, status, created_at, bid_id FROM products WHERE seller_id = ? AND status IN ('a', 's');";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			UserDAO userDAO = new UserDAO();
			int id = userDAO.findUser(userEmail).getId();
			pre.setInt(1, id);
			rs = pre.executeQuery();
			while (rs.next()) {

				int productId = rs.getInt("id");

				ListProductDTO product = new ListProductDTO();

				product.setProductId(rs.getString("product_id"));
				product.setProductName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setStatus(rs.getString("status"));
				product.setDate(rs.getString("created_at"));
				product.setBidID(rs.getInt("bid_id"));

				AssetsDAO assetsDAO = new AssetsDAO();
				product.setAsset(assetsDAO.findFirstAssetByProductId(productId));

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
	 * Updates a product in the database with the provided product details.
	 *
	 * @param updateProduct The updated product information.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public void update(Product updateProduct) throws PersistenceException, ServiceException, ValidationException {
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
	 * Sets the status of a product to "sold" and associates it with a bid.
	 *
	 * @param productId The ID of the product to be marked as sold.
	 * @param bidId     The ID of the associated bid.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public void sell(int productId, int bidId) throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;

		try {
			String query = "UPDATE products SET bid_id=?, status = 's' " + "WHERE id=?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, bidId);
			pre.setInt(2, productId);
			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre);
		}
	}

	/**
	 * Marks a product as deleted in the database.
	 *
	 * @param productId The product ID to be deleted.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ValidationException  If validation of the input data fails.
	 * @throws ServiceException     If a service-related issue occurs.
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
	 * Retrieves a set of ListProductDTO objects for all products in a specific
	 * category associated with a user (excluding their own products).
	 *
	 * @param category  The category of products to retrieve.
	 * @param userEmail The email of the user.
	 * @return A set of ListProductDTO representing products in the specified
	 *         category associated with the user.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public Set<ListProductDTO> findAllProductsByCategory(String category, String userEmail)
			throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		Set<ListProductDTO> productArray = new HashSet<>();

		UserDAO userDAO = new UserDAO();
		int id = userDAO.findUser(userEmail).getId();
		String cate = Category.getCate(category);

		try {
			String query = "SELECT " + "p.id, " + "p.product_id, " + "p.name, " + "p.price, " + "p.seller_id, "
					+ "u.username, " + "u.location, " + "u.image " + "FROM " + "products AS p " + "INNER JOIN "
					+ "users AS u ON p.seller_id = u.id " + "WHERE " + "p.status = ? " + "AND p.category = ? "
					+ "AND u.id <> ?";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, "a");
			pre.setString(2, cate);
			pre.setInt(3, id);
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

				AssetsDAO assetsDAO = new AssetsDAO();
				product.setAsset(assetsDAO.findFirstAssetByProductId(productId));

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
	 * Retrieves a set of ListProductDTO objects for all products in a specific
	 * category.
	 *
	 * @param category The category of products to retrieve.
	 * @return A set of ListProductDTO representing products in the specified
	 *         category.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public Set<ListProductDTO> findAllProductsByCategoryWithoutEmail(String category)
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

				AssetsDAO assetsDAO = new AssetsDAO();
				product.setAsset(assetsDAO.findFirstAssetByProductId(productId));

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
	 * Retrieves the ID of a product based on its product ID.
	 *
	 * @param productId The product ID to search for.
	 * @return The ID of the product, or -1 if not found.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public int findId(String productId) throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		int id = -1;

		try {
			String query = "SELECT id FROM products WHERE product_id = ? AND status = 'a';";

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
	/**
	 * Retrieves a Product object for validation purposes based on the product ID.
	 *
	 * @param productId The product ID to retrieve for validation.
	 * @return A Product object containing information for validation.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public Product methodForValidation(String productId)
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

	/**
	 * Checks if a product with the given ID is active (status is 'a') in the
	 * database.
	 *
	 * @param productId The product ID to check for activity.
	 * @return True if the product is active, false otherwise.
	 * @throws PersistenceException If there is an issue with database persistence.
	 */
	public boolean isActive(int productId) throws PersistenceException {
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

	/**
	 * Retrieves information about a product for listing purposes.
	 *
	 * @param productId The ID of the product to list.
	 * @return A Product object containing information for listing.
	 * @throws PersistenceException If there is an issue with database persistence.
	 * @throws ServiceException     If a service-related issue occurs.
	 * @throws ValidationException  If validation of the input data fails.
	 */
	public Product list(int productId) throws PersistenceException, ServiceException, ValidationException {
		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;
		Product product = null;

		try {
			String query = "SELECT product_id, name, price, status FROM products WHERE id = ? AND status IN ('a', 's');";

			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setInt(1, productId);
			rs = pre.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setName(rs.getString("name"));
				product.setStatus(rs.getString("status"));
				product.setPrice(rs.getInt("price"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}

		return product;
	}

}
