package in.fssa.vanha;

import java.util.Random;

public class ProductRandomGenerator {

	public String idGenerator() {
		String alpha = "abcdefghijklmnopqrstuvwyz";
		String number = "0987654321";
		String id = "";
		int alpI = (int) (Math.random() * alpha.length());
		char alph = alpha.charAt(alpI);
		id += alph;
		for (int i = 0; i < 4; i++) {
			int numI = (int) (Math.random() * number.length());
			char num = number.charAt(numI);
			id += num;
		}
		return id;
	}

	public String categoryGenerator() {

		String[] cateNames = { "bike", "car", "computer", "mobile", };

		Random random = new Random();
		int randomIndex = random.nextInt(cateNames.length);
		String randomcategory = cateNames[randomIndex];

		return randomcategory;

	}

	public String usedDurationGenerator() {

		String[] durNames = { "month", "year" };

		Random random = new Random();
		int randomIndex = random.nextInt(durNames.length);
		String randomDuration = durNames[randomIndex];

		return randomDuration;

	}

	public int usedPeriodGenerator() {

		int min = 1;
		int max = 80;

		int randomValue = min + (int) (Math.random() * (max - min + 1));

		return randomValue;

	}

	int price = 0;

	public int priceGenerator() {

		int min = 10000;
		int max = 80000;

		int randomValue = min + (int) (Math.random() * (max - min + 1));

		this.price = randomValue;

		return randomValue;

	}

	public int minPriceGenerator() {

		return this.price - 1500;

	}

	public String textGenerator() {
		String alpha = "abcdefghijklmnopqrstuvwyz0987654321#$%^&*#@!";
		String text = "";
		for (int i = 0; i < 100; i++) {
			if (i % 5 == 0) {
				text += " ";
			} else {
				int alph = (int) (Math.random() * alpha.length());
				char value = alpha.charAt(alph);
				text += value;
			}
		}
		return text;
	}

	public String nameGenerator() {
		String alpha = "abcdefghijklmnopqrstuvwyz";
		String number = "0987654321";

		String name = "";
		for (int i = 0; i < 10; i++) {
			if (i < 5) {
				int alph = (int) (Math.random() * alpha.length());
				char value = alpha.charAt(alph);
				name += value;
			} else if (i == 5) {
				name += " - ";
			} else {
				int alph = (int) (Math.random() * number.length());
				char value = number.charAt(alph);
				name += value;
			}
		}
		return name;
	}

	public static String assetGenerator() {
		String alpha = "abcdefghijklmnopqrstuvwxyz0987654321";
		String asset = "https://www.";
		for (int i = 0; i < 5; i++) {
			int alph = (int) (Math.random() * alpha.length());
			char value = alpha.charAt(alph);
			asset += value;
		}
		asset += ".com";
		return asset;
	}
}
