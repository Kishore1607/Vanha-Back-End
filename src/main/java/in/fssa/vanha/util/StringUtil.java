package in.fssa.vanha.util;

import in.fssa.vanha.exception.ValidationException;

public class StringUtil {

	/**
	 * Validates a string to ensure it is not null or empty and throws a
	 * ValidationException if it is.
	 *
	 * @param input The string to be validated.
	 * @param name  A descriptive name for the input, used in the exception message
	 *              if validation fails.
	 *
	 * @throws ValidationException if the input string is null or empty.
	 */
	public static void RegectIfInvalidString(String input, String name) throws ValidationException {
		if (input == null || ("").equals(input.trim())) {
			throw new ValidationException(name.concat(" cannot be null or empty"));
		}
	}

}
