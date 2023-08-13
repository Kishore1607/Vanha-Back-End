package in.fssa.vanha.service;

import java.util.Set;

import in.fssa.vanha.dao.AssetsDAO;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.validator.AssetValidator;

public class AssetsService {

	static AssetsDAO assetsDao = new AssetsDAO();

	public int create(Assets newAsset) throws Exception {
		AssetValidator.createValidate(newAsset);
		return assetsDao.create(newAsset);
	}
	
	public static Set<Assets> findAllAssetsByProductId(String productId) throws Exception {
		AssetValidator.findAssetValidate(productId);
		Set<Assets> assetsList = assetsDao.findAllAssetsByProductId(productId);
	    return assetsList;
	}
	
	public void updateAssets(Assets newAsset) throws Exception {
		AssetValidator.updateValidate(newAsset);
		assetsDao.updateAssetsByAssetId(newAsset);
	}
}
