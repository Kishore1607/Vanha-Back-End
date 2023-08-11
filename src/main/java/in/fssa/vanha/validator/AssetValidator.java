package in.fssa.vanha.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.fssa.vanha.service.ProductService;
import in.fssa.vanha.model.Assets;
import in.fssa.vanha.util.StringUtil;

public class AssetValidator {
	public static void createValidate(Assets newAsste) throws Exception {
		
		StringUtil.RegectIfInvalidString(newAsste.getValue(), "Asset");
		
		String urlRegex = "^(http|https)://([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$";

        Pattern urlPattern = Pattern.compile(urlRegex);
		
		Matcher urlMatcher = urlPattern.matcher(newAsste.getValue());
		
		if (!urlMatcher.matches()) {
			throw new RuntimeException("Invalid url pattern");
		}
	}
	
	public static void findAssetValidate(String productId) throws Exception {
		
		StringUtil.RegectIfInvalidString(productId, "Product Id");
		 
		 if(ProductService.findByProductId(productId) == null) {
			 throw new RuntimeException("Product does not exists");
		 }
	}

}
