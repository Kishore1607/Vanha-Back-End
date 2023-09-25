package in.fssa.vanha.util;

import java.util.Random;

public class UserRandomGenerator {

	String alphabet = "abcdefghijklmnopqrstuvwyz";

	public String emailGenerator() {
		String email = "";

		for (int i = 0; i < 10; i++) {
			if (i == 4) {
				email += ".";
			}
			int alph = (int) (Math.random() * alphabet.length());
			char alp = alphabet.charAt(alph);
			email += alp;
			if (i == 9) {
				email += "@gmail.com";
			}
		}
		return email;
	}

	public String nameGenerator() {
		String name = "";

		for (int i = 0; i < 10; i++) {
			if (i == 0 || i == 6) {
				int alph = (int) (Math.random() * alphabet.length());
				char alp = alphabet.charAt(alph);
				char upperCaseAlp = Character.toUpperCase(alp);
				name += upperCaseAlp;
			} else if (i == 5) {
				name += " ";
			} else {
				int alph = (int) (Math.random() * alphabet.length());
				char alp = alphabet.charAt(alph);
				name += alp;
			}
		}
		return name;
	}

	public String passwordGenerator() {
		String lowercase = "abcdefghijklmnopqrstuvwxyz";
		String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";
		String specialChars = "@#$%&";

		String password = "";

		password += lowercase.charAt((int) (Math.random() * lowercase.length()));
		password += uppercase.charAt((int) (Math.random() * uppercase.length()));
		password += numbers.charAt((int) (Math.random() * numbers.length()));
		password += specialChars.charAt((int) (Math.random() * specialChars.length()));

		int remainingLength = 8 - password.length();
		String combinedChars = lowercase + uppercase + numbers + specialChars;
		for (int i = 0; i < remainingLength; i++) {
			int index = (int) (Math.random() * combinedChars.length());
			char character = combinedChars.charAt(index);
			password += character;
		}
		return password;

	}

	public long numberGenenrator() {
		String nNum = "0123456789";
		String fNum = "9876";
		String num = "";
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				num += fNum.charAt((int) (Math.random() * fNum.length()));
			} else {
				num += nNum.charAt((int) (Math.random() * nNum.length()));
			}
		}
		long numericValue = Long.parseLong(num);
		return numericValue;
	}

	public String locationGenerator() {
		String[] placeNames = { "New York", "Los Angeles", "London", "Paris", "Tokyo", "Sydney", "Rome", "Berlin",
				"Moscow", "Rio de Janeiro" };

		Random random = new Random();
		int randomIndex = random.nextInt(placeNames.length);
		String randomPlaceName = placeNames[randomIndex];

		return randomPlaceName;
	}

}
