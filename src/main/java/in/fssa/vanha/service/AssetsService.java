package in.fssa.vanha.service;

import java.util.List;

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
	public int[] create(List<Assets> newAsset) throws ServiceException, ValidationException {
		try {
			AssetValidator.createValidate(newAsset);
			return assetsDao.create((List<Assets>) newAsset);

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in creating asset in asset DAO");
		}

	}

	/**
	 * 
	 * @param productId
	 * @return Set<List>
	 * @throws Exception
	 */
	public List<Assets> findAllAssetsByProductId(int id) throws ServiceException, ValidationException {
		try {
			AssetValidator.findAssetValidate(id);
			List<Assets> assetsList = assetsDao.findAllAssetsByProductId(id);
			return assetsList;

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in finding asset in asset DAO");
		}
	}

	/**
	 * 
	 * @param newAsset
	 * @throws ServiceException
	 * @throws PersistenceException
	 * @throws ValidationException
	 */
	public void updateAssets(Assets newAsset, int id) throws ServiceException, ValidationException {

		try {
			AssetValidator.updateValidate(newAsset, id);
			assetsDao.updateAssetsByAssetId(newAsset, id);
		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in updating asset in asset DAO");
		}
	}

	public String findAssetByProductId(int id) throws ServiceException, ValidationException {
		try {
			AssetValidator.findAssetValidate(id);
			String asset = AssetsDAO.findFirstAssetByProductId(id);
			return asset;

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in finding asset in asset DAO");
		}
	}

}
