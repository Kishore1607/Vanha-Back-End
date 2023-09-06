package in.fssa.vanha.validator;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.util.StringUtil;

public class AssetValidator {

	/**
	 * 
	 * @param newAsset
	 * @throws ValidationException
	 */
	public static void createValidate(Set<Assets> newAssets) throws ValidationException {
		for (Assets asset : newAssets) {
			String assetValue = asset.getValue();

			StringUtil.RegectIfInvalidString(assetValue, "Url");

			if (assetValue.length() > 50) {
				throw new ValidationException("Invalid asset value length");
			}

			String urlRegex = "^(https?://).*";
			Pattern urlPattern = Pattern.compile(urlRegex);

			Matcher urlMatcher = urlPattern.matcher(assetValue);
			if (!urlMatcher.matches()) {
				throw new ValidationException("Invalid URL pattern");
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @param updateAsset
	 * @throws ValidationException
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public static void updateValidate(Set<Assets> updateAssets, int id)
			throws ValidationException, PersistenceException, ServiceException {

		for (Assets asset : updateAssets) {
			String assetValue = asset.getValue();
			StringUtil.RegectIfInvalidString(assetValue, "Url");

			if (assetValue.length() > 50) {
				throw new ValidationException("Invalid asset value length");
			}

			String urlRegex = "^(https?://).*";
			Pattern urlPattern = Pattern.compile(urlRegex);

			Matcher urlMatcher = urlPattern.matcher(assetValue);
			if (!urlMatcher.matches()) {
				throw new ValidationException("Invalid URL pattern of the asset");
			}
		}
	}

	public static void findAssetValidate(int id) throws ValidationException {

		if (id <= 0) {
			throw new ValidationException("Invalid Product id");
		}

	}

}
