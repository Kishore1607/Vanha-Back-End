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
}