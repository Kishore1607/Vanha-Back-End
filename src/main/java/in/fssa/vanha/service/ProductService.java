package in.fssa.vanha.service;

import java.util.List;
import java.util.Set;

import in.fssa.vanha.dao.ProductDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.ListProductDTO;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.model.ProductDetailDTO;
import in.fssa.vanha.validator.ProductValidator;

public class ProductService {

	ProductDAO productDao = new ProductDAO();

	/**
	 * Creates a new product in the system.
	 *
	 * This method validates the provided product using the ProductValidator class
	 * and then stores it in the database using the productDao. The user's email is
	 * required for auditing purposes.
	 *
	 * @param newProduct The product to be created.
	 * @param userEmail  The email of the user performing the creation.
	 *
	 * @throws ServiceException    If an error occurs while creating the product.
	 *                             This typically indicates an issue with the
	 *                             service layer.
	 * @throws ValidationException If the provided newProduct or userEmail is
	 *                             invalid, based on the validation rules specified
	 *                             in the ProductValidator.
	 */
	public void create(Product newProduct, String userEmail) throws ServiceException, ValidationException {
		try {
			ProductValidator.createValidate(newProduct, userEmail);
			productDao.create(newProduct, userEmail);
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while creating product");
		}

	}

	/**
	 * Retrieves a list of products associated with a given seller ID.
	 *
	 * This method fetches a list of products from the data source that are
	 * associated with the specified seller ID. It performs validation of the seller
	 * ID before making the database query to ensure data integrity.
	 *
	 * @param sellerId The unique identifier of the seller whose products are to be
	 *                 retrieved.
	 * @return A List of ListProductDTO objects representing the products associated
	 *         with the seller.
	 * @throws ServiceException    if an error occurs during the process.
	 * @throws ValidationException if the provided seller ID is invalid or not
	 *                             found.
	 */
	public List<ListProductDTO> findAllProductsBySellerId(String sellerId)
			throws ServiceException, ValidationException {

		try {
			ProductValidator.findUserValidate(sellerId);
			List<ListProductDTO> productList = productDao.findAllProductsBySellerId(sellerId);
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while finding product by seller id");
		}
	}

	/**
	 * Retrieves detailed information about a product by its unique identifier.
	 *
	 * @param productId The unique identifier of the product.
	 * @return A ProductDetailDTO object containing information about the product.
	 * @throws ServiceException    If an error occurs while processing the request.
	 * @throws ValidationException If the provided productId is invalid or doesn't
	 *                             exist.
	 */
	public ProductDetailDTO productdetail(String productId) throws ServiceException, ValidationException {

		try {
			ProductValidator.productIdValidate(productId);
			ProductDAO productDAO = new ProductDAO();
			ProductDetailDTO product = productDAO.findProductByProductId(productId);

			return product;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while finding products");
		}
	}

	/**
	 * Updates a product in the system.
	 *
	 * This method is responsible for validating the product data and then updating
	 * it in the data store. If the validation fails, a ValidationException is
	 * thrown, and if there is an issue with the data store, a ServiceException is
	 * thrown.
	 *
	 * @param updateProduct The product to be updated in the system.
	 *
	 * @throws ServiceException    If an error occurs while updating the product in
	 *                             the data store.
	 * @throws ValidationException If the provided product data is invalid.
	 */
	public void update(Product updateProduct) throws ServiceException, ValidationException {

		try {
			ProductValidator.updateValidate(updateProduct);
			productDao.update(updateProduct);
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while updating product");
		}
	}

	/**
	 * Sells a product by updating its associated bid.
	 *
	 * This method sells a product by associating it with a bid, effectively marking
	 * it as sold. It updates the product's status and bid association in the data
	 * store using the ProductDao.
	 *
	 * @param productId The unique identifier of the product to be sold.
	 * @param bidId     The unique identifier of the bid with which the product will
	 *                  be associated.
	 * @throws ServiceException    If an error occurs while updating the product, a
	 *                             ServiceException is thrown.
	 * @throws ValidationException If the input parameters are invalid or if the
	 *                             product or bid do not exist, a
	 *                             ValidationException is thrown.
	 */
	public void sellProduct(int productId, int bidId) throws ServiceException, ValidationException {

		try {
			productDao.sell(productId, bidId);
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while updating product");
		}
	}

	/**
	 * Deletes a product from the system based on its unique identifier.
	 *
	 * @param productId The unique identifier of the product to be deleted.
	 * @throws ServiceException    If an error occurs while deleting the product.
	 * @throws ValidationException If the productId is not valid.
	 */
	public void delete(String productId) throws ServiceException, ValidationException {
		try {
			ProductValidator.productIdValidate(productId);
			productDao.delete(productId);
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while deleting product");
		}
	}

	/**
	 * Retrieves a set of product data transfer objects (DTOs) for products within a
	 * specified category without including the email addresses of the users
	 * associated with the products.
	 *
	 * @param category The category of products to retrieve.
	 * @return A set of ListProductDTO objects representing products within the
	 *         given category.
	 * @throws ServiceException    If there is a service-related issue during the
	 *                             operation.
	 * @throws ValidationException If the input category is invalid or fails
	 *                             validation.
	 */
	public Set<ListProductDTO> findAllProductsByCategoryWithoutEmail(String category)
			throws ServiceException, ValidationException {

		try {
			ProductValidator.findAllProductValidateWithoutEmail(category);
			Set<ListProductDTO> productList = productDao.findAllProductsByCategoryWithoutEmail(category);
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while products by category");
		}
	}

	/**
	 * Retrieves a set of products that belong to a specific category for a given
	 * user.
	 *
	 * This method validates the input parameters, then queries the data source to
	 * retrieve the products associated with the specified category and user.
	 *
	 * @param category  The category of products to retrieve.
	 * @param userEmail The email of the user for whom the products are being
	 *                  retrieved.
	 * @return A set of ListProductDTO objects representing the products in the
	 *         specified category.
	 * @throws ServiceException    If there is a service-related error during the
	 *                             process.
	 * @throws ValidationException If the input parameters fail validation.
	 */
	public Set<ListProductDTO> findAllProductsByCategory(String category, String userEmail)
			throws ServiceException, ValidationException {

		try {
			ProductValidator.findAllProductValidate(category, userEmail);
			Set<ListProductDTO> productList = productDao.findAllProductsByCategory(category, userEmail);
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while products by category");
		}
	}

	/**
	 * Retrieves a set of products associated with a user's email address.
	 *
	 * This method queries the database to find all products associated with the
	 * given user's email and returns them as a set of ListProductDTO objects.
	 *
	 * @param userEmail The email address of the user for whom products should be
	 *                  retrieved.
	 * @return A set of ListProductDTO objects representing the products associated
	 *         with the user.
	 * @throws ServiceException    If there is an issue with the service or
	 *                             database.
	 * @throws ValidationException If the input email is invalid or does not exist
	 *                             in the database.
	 */
	public Set<ListProductDTO> findAllProducts(String userEmail) throws ServiceException, ValidationException {

		try {
			Set<ListProductDTO> productList = productDao.findAllProducts(userEmail);
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while products by category");
		}
	}

	/**
	 * Retrieves a set of product DTOs before a user logs in. This method fetches a
	 * list of products from the database using the productDao and returns them as a
	 * set of ListProductDTOs.
	 *
	 * @return A set of ListProductDTO objects representing the products.
	 * @throws ServiceException    if there is an error while retrieving the
	 *                             products.
	 * @throws ValidationException if there is a validation error during the
	 *                             operation.
	 */
	public Set<ListProductDTO> beforeLoginProducts() throws ServiceException, ValidationException {

		try {
			Set<ListProductDTO> productList = productDao.findAllProducts();
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while products by category");
		}
	}

	/**
	 * Checks if a product with the given product ID exists in the database and
	 * returns it if found.
	 *
	 * @param productId The unique identifier of the product to check.
	 * @return The Product object associated with the given product ID, or null if
	 *         not found.
	 * @throws ServiceException    if an error occurs while attempting to find the
	 *                             product.
	 * @throws ValidationException if the provided product ID is invalid or does not
	 *                             meet validation criteria.
	 */
	public static Product ifExistsOrNot(String productId) throws ServiceException, ValidationException {

		try {
			ProductDAO productDAO = new ProductDAO();
			Product product = productDAO.methodForValidation(productId);
			return product;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while finding all products");
		}
	}

	/**
	 * Retrieves detailed information about a product based on its unique
	 * identifier.
	 *
	 * This method queries the product data using the provided 'id' and returns a
	 * detailed product object if found.
	 *
	 * @param id The unique identifier of the product to retrieve.
	 * @return A Product object containing detailed information about the product.
	 * @throws ServiceException    if there's an issue with the service layer.
	 * @throws ValidationException if the 'id' parameter is invalid or out of range.
	 */
	public Product productDetail(int id) throws ServiceException, ValidationException {

		try {
			ProductDAO productDAO = new ProductDAO();
			Product product = productDAO.list(id);
			return product;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while finding all products");
		}
	}
}
