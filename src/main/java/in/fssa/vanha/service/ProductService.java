package in.fssa.vanha.service;

import java.util.List;
import java.util.Set;

import in.fssa.vanha.dao.ProductDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.ListProductDTO;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.model.ProductDetailDTO;
import in.fssa.vanha.validator.AssetValidator;
import in.fssa.vanha.validator.ProductValidator;

public class ProductService {

	ProductDAO productDao = new ProductDAO();

	/**
	 * 
	 * @param newProduct
	 * @param newAsset
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void create(Product newProduct, String userEmail)
			throws ServiceException, ValidationException {
		try {
			ProductValidator.createValidate(newProduct, userEmail);
			productDao.create(newProduct, userEmail);
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while creating product");
		}

	}

	/**
	 * 
	 * @param sellerId
	 * @return Set<Product>
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public List<ListProductDTO> findAllProductsBySellerId(String sellerId) throws ServiceException, ValidationException {

		try {
			ProductValidator.findUserValidate(sellerId);
			List<ListProductDTO> productList = productDao.findAllProductsBySellerId(sellerId);
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while finding product by seller id");
		}
	}

	/**
	 * 
	 * @param productId
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public static ProductDetailDTO productdetail(String productId) throws ServiceException, ValidationException {

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
	 * 
	 * @param updateProduct
	 * @throws ServiceException
	 * @throws ValidationException
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
	 * 
	 * @param productId
	 * @throws ServiceException
	 * @throws ValidationException
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
	 * 
	 * @param category
	 * @return Set<Product>
	 * @throws ServiceException
	 * @throws ValidationException
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
	
	public Set<ListProductDTO> findAllProductsByCategory(String category)
			throws ServiceException, ValidationException {

		try {
			ProductValidator.findAllProductValidate(category);
			Set<ListProductDTO> productList = productDao.findAllProductsByCategory(category);
			return productList;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while products by category");
		}
	}

	/**
	 * 
	 * @param userEmail
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
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
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
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
	 * 
	 * @param productId
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public static Product ifExistsOrNot(String productId) throws ServiceException, ValidationException {

		try {
			Product product = ProductDAO.methodForValidation(productId);
			return product;
		} catch (PersistenceException e) {
			throw new ServiceException("Errro while finding all products");
		}
	}

}
