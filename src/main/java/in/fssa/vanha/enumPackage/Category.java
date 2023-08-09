package in.fssa.vanha.enumPackage;

public enum Category {
    CAR('c'),
    BIKE('b'),
    COMPUTER('s'),
    MOBILE('m');

    private final char value;

    Category(char c) {
        this.value = c;
    }

    public char getValue() {
        return value;
    }

    public static char getCate(String input) {
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
                throw new IllegalArgumentException("Invalid input string for Category: " + input);
        }
    }
    
    public static String getCategoryString(char input) {
        for (Category category : Category.values()) {
            if (category.getValue() == input) {
                return category.name().toLowerCase();
            }
        }
        throw new IllegalArgumentException("Invalid input char for Category: " + input);
    }
}