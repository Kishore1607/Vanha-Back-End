package in.fssa.vanha.service;

import in.fssa.vanha.dao.ProductAssetDAO;
import in.fssa.vanha.model.ProductAsset;

public class ProductAssetService {
	ProductAssetDAO productAssetsDao = new ProductAssetDAO();

	public void create(ProductAsset newProductAsset) throws Exception {
		productAssetsDao.create(newProductAsset);
	}
}
