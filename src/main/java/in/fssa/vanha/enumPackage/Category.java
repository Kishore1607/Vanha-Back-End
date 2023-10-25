package in.fssa.vanha.enumPackage;

import in.fssa.vanha.exception.ValidationException;

public enum Category {
	CAR("c"), BIKE("b"), COMPUTER("s"), MOBILE("m");

	private final String value;

	Category(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Converts a category input string to its corresponding value.
	 *
	 * @param input The input string representing a category (e.g., "car", "bike").
	 * @return The value associated with the input category.
	 * @throws ValidationException If the input category is not valid.
	 */
	public static String getCate(String input) throws ValidationException {
		String lowerCaseInput = input.toLowerCase();
		switch (lowerCaseInput) {
		case "car":
			return CAR.value;
		case "bike":
			return BIKE.value;
		case "computer":
			return COMPUTER.value;
		case "mobile":
			return MOBILE.value;
		default:
			return "non";
		}
	}

	/**
	 * Converts a category value to its corresponding string representation.
	 *
	 * @param input The value of a category.
	 * @return The lowercase string representation of the category.
	 * @throws ValidationException If the input value is not valid.
	 */
	public static String getCategoryString(String input) throws ValidationException {
		for (Category category : Category.values()) {
			if (category.getValue().equals(input)) {
				return category.name().toLowerCase();
			}
		}
		return "non";
	}
}