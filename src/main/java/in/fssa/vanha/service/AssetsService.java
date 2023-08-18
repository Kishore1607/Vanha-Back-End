package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.AssetsDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.validator.AssetValidator;

public class AssetsService {

	static AssetsDAO assetsDao = new AssetsDAO();

	/**
	 * 
	 * @param newAsset
	 * @return int
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public int create(Assets newAsset) throws ServiceException, PersistenceException, ValidationException {
		AssetValidator.createValidate(newAsset);
		return assetsDao.create(newAsset);
	}
	
	/**
	 * 
	 * @param productId
	 * @return Set<List>
	 * @throws Exception
	 */
	public static Set<Assets> findAllAssetsByProductId(String productId) throws Exception {
		AssetValidator.findAssetValidate(productId);
		Set<Assets> assetsList = assetsDao.findAllAssetsByProductId(productId);
	    return assetsList;
	}
	
	/**
	 * 
	 * @param newAsset
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public void updateAssets(Assets newAsset) throws ServiceException, PersistenceException, ValidationException {
		AssetValidator.updateValidate(newAsset);
		assetsDao.updateAssetsByAssetId(newAsset);
	}
}
