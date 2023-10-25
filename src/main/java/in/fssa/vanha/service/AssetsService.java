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
	 * Creates new assets based on the provided list of asset data.
	 *
	 * This method performs validation on the provided list of assets using the
	 * AssetValidator class and then delegates the creation operation to the
	 * assetsDao. If the validation is successful, the assets are created and an
	 * array of integers representing the IDs of the newly created assets is
	 * returned.
	 *
	 * @param newAsset A list of Asset objects containing data for the assets to be
	 *                 created.
	 * @return An array of integers representing the IDs of the newly created
	 *         assets.
	 * @throws ServiceException    If there is an error during the creation process,
	 *                             a ServiceException is thrown.
	 * @throws ValidationException If the provided list of assets fails validation,
	 *                             a ValidationException is thrown.
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
	 * Retrieves a list of assets associated with a given product ID.
	 *
	 * This method validates the provided product ID, retrieves the list of assets
	 * associated with the specified product, and returns the list of assets if
	 * found.
	 *
	 * @param id The unique identifier of the product for which assets are to be
	 *           retrieved.
	 * @return A list of Assets associated with the specified product ID. The list
	 *         may be empty if no assets are found.
	 * @throws ServiceException    If an error occurs during the process of finding
	 *                             assets, such as a validation error or a
	 *                             persistence error.
	 * @throws ValidationException If the provided product ID is invalid or does not
	 *                             exist.
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
	 * Updates an existing asset with the provided information.
	 *
	 * This method updates the asset with the given ID using the new asset
	 * information.
	 *
	 * @param newAsset The new asset information to update.
	 * @param id       The ID of the asset to be updated.
	 * @throws ServiceException    If an error occurs during the update operation.
	 * @throws ValidationException If the new asset information is not valid.
	 */
	public void updateAssets(Assets newAsset, int id) throws ServiceException, ValidationException {

		try {
			AssetValidator.updateValidate(newAsset, id);
			assetsDao.updateAssetsByAssetId(newAsset, id);
		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in updating asset in asset DAO");
		}
	}

	/**
	 * Finds and returns an asset by its product ID.
	 *
	 * This method validates the product ID, retrieves the asset from the database,
	 * and returns it as a string.
	 *
	 * @param id The product ID of the asset to find.
	 * @return A string representing the asset, or null if no asset is found.
	 * @throws ServiceException    If there is an issue with the service or business
	 *                             logic.
	 * @throws ValidationException If the input validation fails.
	 */
	public String findAssetByProductId(int id) throws ServiceException, ValidationException {
		try {
			AssetValidator.findAssetValidate(id);
			AssetsDAO assetDAO = new AssetsDAO();
			String asset = assetDAO.findFirstAssetByProductId(id);
			return asset;

		} catch (PersistenceException e) {
			throw new ServiceException("Error occured in finding asset in asset DAO");
		}
	}

}
