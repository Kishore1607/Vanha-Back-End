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

}