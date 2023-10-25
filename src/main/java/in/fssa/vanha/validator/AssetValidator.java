package in.fssa.vanha.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.util.StringUtil;

public class AssetValidator {

	public static void createValidate(List<Assets> newAsset) throws ValidationException {
		for (Assets asset : newAsset) {
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

	public static void updateValidate(Assets newAsset, int id)
			throws ValidationException, PersistenceException, ServiceException {

		String assetValue = newAsset.getValue();
		StringUtil.RegectIfInvalidString(assetValue, "Url");

		String urlRegex = "^(https?://).*";
		Pattern urlPattern = Pattern.compile(urlRegex);

		Matcher urlMatcher = urlPattern.matcher(assetValue);
		if (!urlMatcher.matches()) {
			throw new ValidationException("Invalid URL pattern of the asset");
		}
	}

	public static void findAssetValidate(int id) throws ValidationException {

		if (id <= 0) {
			throw new ValidationException("Invalid Product id");
		}

	}

}
