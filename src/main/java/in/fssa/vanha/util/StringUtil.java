package in.fssa.vanha.util;

public class StringUtil {
	public static void RegectIfInvalidString(String input, String name) throws RuntimeException {
		if (input == null || ("").equals(input.trim())) {
			throw new RuntimeException(name.concat(" cannot be null or empty"));
		}
	}

//	public static boolean IsvalidString(String input, String name) {
//		if (input == null || ("").equals(input.trim())) {
//			return false;
//		}
//		return true;
//	}
//
//	public static boolean IsInvalidString(String input, String name) {
//		if (input == null || ("").equals(input.trim())) {
//			return true;
//		}
//		return false;
//	}
}
