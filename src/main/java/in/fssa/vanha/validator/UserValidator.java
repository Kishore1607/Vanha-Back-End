package in.fssa.vanha.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.dao.UserDAO;
import in.fssa.vanha.util.StringUtil;

public class UserValidator {
	static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	static String nameRegex = "^(?!\\s)[a-zA-Z\\s]+$";
	static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,24}$";
	static String locationRegex = "^(?!\\s)[a-zA-Z\\s,]+\\.?$";

	static Pattern emailPattern = Pattern.compile(emailRegex);
	static Pattern namePattern = Pattern.compile(nameRegex);
	static Pattern passwordPattern = Pattern.compile(passwordRegex);
	static Pattern locationPattern = Pattern.compile(locationRegex);

	public static void createValidate(User newUser) throws Exception {

		// null or empty checking

		if (newUser == null) {
			throw new RuntimeException("Invalid user input");
		}

		StringUtil.RegectIfInvalidString(newUser.getEmail(), "Email");

		StringUtil.RegectIfInvalidString(newUser.getPassword(), "Password");

		StringUtil.RegectIfInvalidString(newUser.getName(), "Name");

		StringUtil.RegectIfInvalidString(newUser.getLocation(), "Location");

		if (newUser.getNumber() < 6000000000l || newUser.getNumber() > 9999999999l) {
			throw new RuntimeException("Invalid number input");
		}

		// pattern checking

		Matcher emailMatcher = emailPattern.matcher(newUser.getEmail());
		Matcher nameMatcher = namePattern.matcher(newUser.getName());
		Matcher passwordMatcher = passwordPattern.matcher(newUser.getPassword());
		Matcher locationMatcher = locationPattern.matcher(newUser.getLocation());

		if (!emailMatcher.matches()) {
			throw new RuntimeException("Invalid email pattern");
		}

		if (!nameMatcher.matches()) {
			throw new RuntimeException("Invalid name pattern");
		}

		if (!passwordMatcher.matches()) {
			throw new RuntimeException("Invalid password pattern");
		}

		if (!locationMatcher.matches()) {
			throw new RuntimeException("Invalid location pattern");
		}

		UserService us = new UserService();
		if(us.findUserByEmail(newUser.getEmail()) != null) {
			throw new RuntimeException("User already exists");
		}

	}

	public static void updateValidate(User updateUser) throws Exception {

		// null or empty checking

		if (updateUser == null) {
			throw new RuntimeException("Invalid user input");
		}

		StringUtil.RegectIfInvalidString(updateUser.getEmail(), "Email");

		StringUtil.RegectIfInvalidString(updateUser.getName(), "Name");

		StringUtil.RegectIfInvalidString(updateUser.getLocation(), "Location");

		if (updateUser.getNumber() < 6000000000l || updateUser.getNumber() > 9999999999l) {
			throw new RuntimeException("Invalid number input");
		}

		// pattern checking

		Matcher emailMatcher = emailPattern.matcher(updateUser.getEmail());
		Matcher nameMatcher = namePattern.matcher(updateUser.getName());
		Matcher locationMatcher = locationPattern.matcher(updateUser.getLocation());

		if (!emailMatcher.matches()) {
			throw new RuntimeException("Invalid email pattern");
		}

		if (!nameMatcher.matches()) {
			throw new RuntimeException("Invalid name pattern");
		}

		if (!locationMatcher.matches()) {
			throw new RuntimeException("Invalid location pattern");
		}

		// exists checking

		UserDAO findUser = new UserDAO();

		User user = findUser.findUserByEmail(updateUser.getEmail());

		if (user == null) {
			throw new RuntimeException("User doesn't exists");
		}

	}
	
	public static void findUserValidate(String email) throws Exception {
		if (email == null) {
			throw new RuntimeException("Email can't be null");
		}
		
		StringUtil.RegectIfInvalidString(email, "Email");
	}

}
