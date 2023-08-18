package in.fssa.vanha.service;

import in.fssa.vanha.dao.ProductAssetDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.model.ProductAsset;

public class ProductAssetService {
	ProductAssetDAO productAssetsDao = new ProductAssetDAO();

	/**
	 * 
	 * @param newProductAsset
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public void create(ProductAsset newProductAsset) throws ServiceException, PersistenceException {
		productAssetsDao.create(newProductAsset);
	}
}
