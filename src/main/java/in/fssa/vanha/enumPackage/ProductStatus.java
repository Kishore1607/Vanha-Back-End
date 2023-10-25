package in.fssa.vanha.enumPackage;

public enum ProductStatus {
    ACTIVE('a'),
    SOLD('s'),
    DELETED('d');

    private final char value;

    ProductStatus(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    /**
     * Converts a string input to its corresponding character value.
     *
     * @param input The input string (e.g., "active", "sold").
     * @return The character value associated with the input string.
     * @throws IllegalArgumentException If the input string is not valid.
     */
    public static char fromString(String input) {
        String lowerCaseInput = input.toLowerCase();
        switch (lowerCaseInput) {
            case "active":
                return ACTIVE.value;
            case "sold":
                return SOLD.value;
            case "deleted":
                return DELETED.value;
            default:
                throw new IllegalArgumentException("Invalid input string for ProductStatus: " + input);
        }
    }
    
    /**
     * Converts a character input to its corresponding full string representation.
     *
     * @param input The character input (e.g., 'a', 's').
     * @return The full string representation of the input character.
     * @throws IllegalArgumentException If the input character is not valid.
     */
    public static String getFullValue(char input) {
        switch (input) {
            case 'a':
                return "active";
            case 's':
                return "sold";
            case 'd':
                return "deleted";
            default:
                throw new IllegalArgumentException("Invalid input input for UsedDuration: " + input);
        }
    }
}