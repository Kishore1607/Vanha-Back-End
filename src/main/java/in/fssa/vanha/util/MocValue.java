package in.fssa.vanha.util;

public class MocValue {

	// Moc product value

	static ProductRandomGenerator prod = new ProductRandomGenerator();

	public static String id = prod.idGenerator();
	public static String category = prod.categoryGenerator();
	public static int usedPeriod = prod.usedPeriodGenerator();
	public static String usedDuration = prod.usedDurationGenerator();
	public static String description = prod.textGenerator();
	public static String productName = prod.nameGenerator(category);
	public static int price = prod.priceGenerator();
	public static int minPrice = prod.minPriceGenerator();
	public static String asset = prod.assetGenerator(category);

	// Moc user value

    static UserRandomGenerator user = new UserRandomGenerator();

    public static String userName = user.nameGenerator();
    public static String email = user.emailGenerator();
    public static String password = user.passwordGenerator();
    public static long number = user.numberGenenrator();
    public static String location = user.locationGenerator();

}
