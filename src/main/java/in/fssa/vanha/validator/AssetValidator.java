package in.fssa.vanha.validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.util.ConnectionUtil;
import in.fssa.vanha.util.StringUtil;

public class AssetValidator {
	
	/**
	 * 
	 * @param newAsste
	 * @throws ValidationException
	 */
	public static void createValidate(Assets newAsste) throws ValidationException {

		StringUtil.RegectIfInvalidString(newAsste.getValue(), "Asset");

		if (newAsste.getValue().length() > 50) {
			throw new ValidationException("Invalid product id length");
		}

		String urlRegex = "^(http|https)://([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$";

		Pattern urlPattern = Pattern.compile(urlRegex);

		Matcher urlMatcher = urlPattern.matcher(newAsste.getValue());

		if (!urlMatcher.matches()) {
			throw new ValidationException("Invalid url pattern");
		}
	}

	/**
	 * 
	 * @param productId
	 * @throws ValidationException
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public static void findAssetValidate(String productId) throws ValidationException, ServiceException{

		StringUtil.RegectIfInvalidString(productId, "Product Id");

		if (ProductService.findByProductId(productId) == null) {
			throw new ServiceException("Product does not exists");
		}
	}

	/**
	 * 
	 * @param updateAsset
	 * @throws ValidationException
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public static void updateValidate(Assets updateAsset) throws ValidationException, PersistenceException, ServiceException {

		StringUtil.RegectIfInvalidString(updateAsset.getOldValue(), "Old Asset value");

		StringUtil.RegectIfInvalidString(updateAsset.getValue(), "New Asset");

		StringUtil.RegectIfInvalidString(updateAsset.getProductId(), "Product id");

		if (updateAsset.getOldValue().length() > 50) {
			throw new ValidationException("Invalid product id length");
		}
		if (updateAsset.getValue().length() > 50) {
			throw new ValidationException("Invalid product id length");
		}

		String urlRegex = "^(http|https)://([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$";

		Pattern urlPattern = Pattern.compile(urlRegex);

		Matcher urlMatcher = urlPattern.matcher(updateAsset.getValue());

		if (!urlMatcher.matches()) {
			throw new ValidationException("Invalid url pattern of new asset");
		}

		if (ProductService.findByProductId(updateAsset.getProductId()) == null) {
			throw new ServiceException("Product does not exists");
		}

		Connection conn = null;
		PreparedStatement pre = null;
		ResultSet rs = null;

		try {

			String query = "SELECT * FROM assets where url = ? AND status = 1";
			conn = ConnectionUtil.getConnection();
			pre = conn.prepareStatement(query);
			pre.setString(1, updateAsset.getOldValue());
			rs = pre.executeQuery();
			if (!rs.next()) {
				throw new ServiceException("Old asset does not exists");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new PersistenceException(e);
		} finally {
			ConnectionUtil.close(conn, pre, rs);
		}

		if (updateAsset.getOldValue().equals(updateAsset.getValue())) {
			throw new ValidationException("Old asset and new asset cannot be same");
		}
	}
}
