package in.fssa.vanha.util;

import java.util.Random;

public class UserRandomGenerator {

	String alphabet = "abcdefghijklmnopqrstuvwyz";

	/**
	 * Generates a random email address consisting of 10 characters followed by
	 * "@gmail.com".
	 *
	 * @return A randomly generated email address in the format
	 *         "xxxx.xxxx@gmail.com".
	 */
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

	/**
	 * Generates a random name consisting of ten characters, including uppercase
	 * letters and spaces.
	 *
	 * @return A randomly generated name.
	 */
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

	/**
	 * Generates a random password with a minimum length of 8 characters. The
	 * password will contain at least one lowercase letter, one uppercase letter,
	 * one digit, and one special character from the set of '@', '#', '$', '%', and
	 * '&'.
	 *
	 * @return A randomly generated password as a String.
	 */
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

	/**
	 * Generates a random 10-digit numeric value with specific constraints.
	 *
	 * This method generates a random 10-digit numeric value with the following
	 * constraints: - The first digit must be one of the characters in "9876". - The
	 * remaining nine digits can be any of the characters in "0123456789".
	 *
	 * @return A randomly generated 10-digit long value.
	 */
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

	/**
	 * Generates a random location name from a predefined list of places.
	 *
	 * @return A randomly selected location name.
	 */
	public String locationGenerator() {
		String[] placeNames = { "New York", "Los Angeles", "London", "Paris", "Tokyo", "Sydney", "Rome", "Berlin",
				"Moscow", "Rio de Janeiro" };

		Random random = new Random();
		int randomIndex = random.nextInt(placeNames.length);
		String randomPlaceName = placeNames[randomIndex];

		return randomPlaceName;
	}

}
