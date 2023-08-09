package in.fssa.vanha.enumPackage;

public enum UsedDuration {
    YEAR('y'),
    MONTH('m');

    private final char value;

    UsedDuration(char value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static char used(String input) {
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