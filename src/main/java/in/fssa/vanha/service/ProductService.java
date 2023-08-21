package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.ProductDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.Product;
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
	public void create(Product newProduct, Assets newAsset) throws ServiceException, ValidationException {
		try {
			ProductValidator.createValidate(newProduct);
			AssetValidator.createValidate(newAsset);
			productDao.create(newProduct, newAsset);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
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
	public Set<Product> findAllProductsBySellerId(String sellerId) throws ServiceException, ValidationException {

		try {
			ProductValidator.findUserValidate(sellerId);
			Set<Product> productList = productDao.findAllProductsBySellerId(sellerId);
			return productList;
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Errro while finding product by seller id");
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
			// TODO Auto-generated catch block
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
			ProductValidator.deleteValidate(productId);
			productDao.delete(productId);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Errro while deleting product");
		}
	}

	/**
	 * 
	 * @param productId
	 * @return Product
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public static Product findByProductId(String productId) throws ServiceException, ValidationException {

		try {
			ProductValidator.findProductValidate(productId);
			return ProductDAO.findProductByProductId(productId);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Errro while finding product by product id");
		}
	}

	/**
	 * 
	 * @param category
	 * @return Set<Product>
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public Set<Product> findAllProductsByCategory(String category) throws ServiceException, ValidationException {

		try {
			ProductValidator.findAllProductValidate(category);
			Set<Product> productList = productDao.findAllProductsByCategory(category);
			return productList;
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Errro while products by category");
		}
	}

}
