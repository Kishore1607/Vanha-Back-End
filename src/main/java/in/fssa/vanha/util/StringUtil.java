package in.fssa.vanha.util;

public class StringUtil {
	
	/**
	 * 
	 * @param input
	 * @param name
	 * @throws RuntimeException
	 */
	public static void RegectIfInvalidString(String input, String name) throws RuntimeException {
		if (input == null || ("").equals(input.trim())) {
			throw new RuntimeException(name.concat(" cannot be null or empty"));
		}
	}

}
