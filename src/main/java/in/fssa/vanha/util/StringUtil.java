package in.fssa.vanha.util;

import in.fssa.vanha.exception.ValidationException;

public class StringUtil {
	
	/**
	 * 
	 * @param input
	 * @param name
	 * @throws RuntimeException
	 * @throws ValidationException 
	 */
	public static void RegectIfInvalidString(String input, String name) throws ValidationException {
		if (input == null || ("").equals(input.trim())) {
			throw new ValidationException(name.concat(" cannot be null or empty"));
		}
	}

}
