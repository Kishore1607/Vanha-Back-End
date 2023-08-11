package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.ProductDAO;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.model.Product;
import in.fssa.vanha.validator.AssetValidator;
import in.fssa.vanha.validator.ProductValidator;

public class ProductService {
	
	ProductDAO productDao = new ProductDAO();

	public void create(Product newProduct, Assets newAsset) throws Exception {
		ProductValidator.createValidate(newProduct);
		AssetValidator.createValidate(newAsset);
		productDao.create(newProduct, newAsset);
	}

	public Set<Product> findAllProductsBySellerId(String sellerId) throws Exception {
		ProductValidator.findUserValidate(sellerId);
		Set<Product> productList = productDao.findAllProductsBySellerId(sellerId);
	    return productList;
	}

	public void update(Product updateProduct) throws Exception {
		ProductValidator.updateValidate(updateProduct);
		productDao.update(updateProduct);
	}

	public void delete(String productId) throws Exception {
		ProductValidator.deleteValidate(productId);
		productDao.delete(productId);
	}
	
	public static Product findByProductId(String productId) throws Exception {
		ProductValidator.findProductValidate(productId);
		return ProductDAO.findProductByProductId(productId);
	}

}
