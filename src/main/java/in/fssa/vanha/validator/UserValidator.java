package in.fssa.vanha.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.dao.UserDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.util.StringUtil;

public class UserValidator {
	static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	static String nameRegex = "^(?!\\s)[a-zA-Z\\s]+$";
	static String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,24}$";
	static String locationRegex = "^(?!\\s)[a-zA-Z\\s,]+\\.?$";
	static String imageRegex = "^(https?://).*";

	static Pattern emailPattern = Pattern.compile(emailRegex);
	static Pattern namePattern = Pattern.compile(nameRegex);
	static Pattern passwordPattern = Pattern.compile(passwordRegex);
	static Pattern locationPattern = Pattern.compile(locationRegex);
	static Pattern imagePattern = Pattern.compile(imageRegex);

	/**
	 * 
	 * @param newUser
	 * @throws ValidationException
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public static void createValidate(User newUser) throws ValidationException, ServiceException, PersistenceException {

		// null or empty checking

		if (newUser == null) {
			throw new ValidationException("Invalid user input");
		}

		StringUtil.RegectIfInvalidString(newUser.getEmail(), "Email");

		StringUtil.RegectIfInvalidString(newUser.getPassword(), "Password");

		StringUtil.RegectIfInvalidString(newUser.getName(), "Name");

		StringUtil.RegectIfInvalidString(newUser.getLocation(), "Location");

		if (newUser.getNumber() < 6000000000l || newUser.getNumber() > 9999999999l) {
			throw new ValidationException("Invalid number input");
		}

		// Length checking

		if (newUser.getEmail().length() > 100) {
			throw new ValidationException("Invalid email length");
		}
		if (newUser.getPassword().length() > 8) {
			throw new ValidationException("Invalid password length");
		}
		if (newUser.getName().length() > 25) {
			throw new ValidationException("Invalid name length");

		}
		if (newUser.getLocation().length() > 50) {
			throw new ValidationException("Invalid location length");

		}
		// pattern checking

		Matcher emailMatcher = emailPattern.matcher(newUser.getEmail());
		Matcher nameMatcher = namePattern.matcher(newUser.getName());
		Matcher passwordMatcher = passwordPattern.matcher(newUser.getPassword());
		Matcher locationMatcher = locationPattern.matcher(newUser.getLocation());

		if (newUser.getImage() != null) {
			Matcher imageMatcher = imagePattern.matcher(newUser.getImage());
			if (!imageMatcher.matches()) {
				throw new ValidationException("Invalid image pattern");
			}
		}

		if (!emailMatcher.matches()) {
			throw new ValidationException("Invalid email pattern");
		}

		if (!nameMatcher.matches()) {
			throw new ValidationException("Invalid name pattern");
		}

		if (!passwordMatcher.matches()) {
			throw new ValidationException("Invalid password pattern");
		}

		if (!locationMatcher.matches()) {
			throw new ValidationException("Invalid location pattern");
		}

		if (UserService.findUserByEmail(newUser.getEmail()) != null) {
			throw new ServiceException("User already exists");
		}

	}

	/**
	 * 
	 * @param updateUser
	 * @throws ValidationException
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public static void updateValidate(User updateUser)
			throws ValidationException, ServiceException, PersistenceException {

		// null or empty checking

		if (updateUser == null) {
			throw new ValidationException("Invalid user input");
		}

		StringUtil.RegectIfInvalidString(updateUser.getEmail(), "Email");

		StringUtil.RegectIfInvalidString(updateUser.getName(), "Name");

		StringUtil.RegectIfInvalidString(updateUser.getLocation(), "Location");

		if (updateUser.getNumber() < 6000000000l || updateUser.getNumber() > 9999999999l) {
			throw new ValidationException("Invalid number input");
		}

		// Length checking

		if (updateUser.getName().length() > 50) {
			throw new ValidationException("Invalid name length");

		}
		if (updateUser.getLocation().length() > 50) {
			throw new ValidationException("Invalid location length");
		}

		// pattern checking

		Matcher emailMatcher = emailPattern.matcher(updateUser.getEmail());
		Matcher nameMatcher = namePattern.matcher(updateUser.getName());
		Matcher locationMatcher = locationPattern.matcher(updateUser.getLocation());

		if (updateUser.getImage() != null) {
			Matcher imageMatcher = imagePattern.matcher(updateUser.getImage());
			if (!imageMatcher.matches()) {
				throw new ValidationException("Invalid image pattern");
			}
		}

		if (!emailMatcher.matches()) {
			throw new ValidationException("Invalid email pattern");
		}

		if (!nameMatcher.matches()) {
			throw new ValidationException("Invalid name pattern");
		}

		if (!locationMatcher.matches()) {
			throw new ValidationException("Invalid location pattern");
		}

		// exists checking

		User user = UserDAO.findUser(updateUser.getEmail());

		if (user == null) {
			throw new ServiceException("User doesn't exists");
		}

	}

	/**
	 * 
	 * @param email
	 * @throws ValidationException
	 */
	public static void findUserValidate(String email) throws ValidationException {
		if (email == null) {
			throw new ValidationException("Email can't be null");
		}

		StringUtil.RegectIfInvalidString(email, "Email");
	}

	/**
	 * 
	 * @param email
	 * @throws ValidationException
	 */
	public static void loginValidation(User user) throws ValidationException {
		if (user == null) {
			throw new ValidationException("input can't be null");
		}

		StringUtil.RegectIfInvalidString(user.getEmail(), "Email");
		StringUtil.RegectIfInvalidString(user.getPassword(), "password");

		// pattern checking

		Matcher emailMatcher = emailPattern.matcher(user.getEmail());
		Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());

		if (!emailMatcher.matches()) {
			throw new ValidationException("Invalid email pattern");
		}

		if (!passwordMatcher.matches()) {
			throw new ValidationException("Invalid password pattern");
		}
	}

}
