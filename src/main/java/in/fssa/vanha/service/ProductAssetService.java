package in.fssa.vanha.service;

import in.fssa.vanha.dao.ProductAssetDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.model.ProductAsset;

public class ProductAssetService {
	ProductAssetDAO productAssetsDao = new ProductAssetDAO();

	/**
	 * Creates a new ProductAsset by storing it in the database. This method
	 * associates a product with an asset by adding their IDs to the product_asset
	 * table.
	 *
	 * @param newProductAsset The ProductAsset to be created and stored in the
	 *                        database.
	 * @throws ServiceException     If an error occurs while creating and storing
	 *                              the ProductAsset.
	 * @throws PersistenceException If there is an issue with the persistence layer,
	 *                              such as database connectivity.
	 */
	public void create(ProductAsset newProductAsset) throws ServiceException, PersistenceException {
		try {
			productAssetsDao.create(newProductAsset);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while storing id's of product and asset in product_asset table");
		}
	}
}
