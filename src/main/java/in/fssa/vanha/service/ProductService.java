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
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public void create(Product newProduct, Assets newAsset) throws ServiceException, PersistenceException, ValidationException {
		ProductValidator.createValidate(newProduct);
		AssetValidator.createValidate(newAsset);
		productDao.create(newProduct, newAsset);
	}

	/**
	 * 
	 * @param sellerId
	 * @return Set<Product>
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public Set<Product> findAllProductsBySellerId(String sellerId) throws ServiceException, PersistenceException, ValidationException {
		ProductValidator.findUserValidate(sellerId);
		Set<Product> productList = productDao.findAllProductsBySellerId(sellerId);
	    return productList;
	}

	/**
	 * 
	 * @param updateProduct
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public void update(Product updateProduct) throws ServiceException, PersistenceException, ValidationException {
		ProductValidator.updateValidate(updateProduct);
		productDao.update(updateProduct);
	}

	/**
	 * 
	 * @param productId
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public void delete(String productId) throws ServiceException, PersistenceException, ValidationException {
		ProductValidator.deleteValidate(productId);
		productDao.delete(productId);
	}
	
	/**
	 * 
	 * @param productId
	 * @return Product
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public static Product findByProductId(String productId) throws ServiceException, PersistenceException, ValidationException {
		ProductValidator.findProductValidate(productId);
		return ProductDAO.findProductByProductId(productId);
	}
	
	/**
	 * 
	 * @param category
	 * @return Set<Product>
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public Set<Product> findAllProductsByCategory(String category) throws ServiceException, PersistenceException, ValidationException{
		ProductValidator.findAllProductValidate(category);
		Set<Product> productList = productDao.findAllProductsByCategory(category);
	    return productList;
	}

}
