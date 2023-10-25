package in.fssa.vanha.enumPackage;

public enum UsedDuration {
    YEAR("y"),
    MONTH("m");

    private final String value;

    UsedDuration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Converts a string input to its corresponding used duration value.
     *
     * @param input The input string (e.g., "year", "month").
     * @return The value associated with the input used duration.
     * @throws IllegalArgumentException If the input string is not valid.
     */
    public static String used(String input) {
        String lowerCaseInput = input.toLowerCase();
        switch (lowerCaseInput) {
            case "year":
                return YEAR.value;
            case "month":
                return MONTH.value;
            default:
                throw new IllegalArgumentException("Invalid input string for UsedDuration: " + input);
        }
    }
    /**
     * Converts a string input (short form) to its corresponding full string representation.
     *
     * @param input The input string (e.g., "y", "m").
     * @return The full string representation of the input string.
     * @throws IllegalArgumentException If the input string is not valid.
     */
    public static String getFullValue(String input) {
        String lowerCaseInput = input.toLowerCase();
        switch (lowerCaseInput) {
            case "y":
                return "year";
            case "m":
                return "month";
            default:
                throw new IllegalArgumentException("Invalid input string for UsedDuration: " + input);
        }
    }

}