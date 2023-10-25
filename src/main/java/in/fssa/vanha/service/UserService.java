package in.fssa.vanha.service;

import in.fssa.vanha.model.User;
import in.fssa.vanha.dao.UserDAO;
import in.fssa.vanha.exception.PersistenceException;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.validator.UserValidator;

public class UserService {

	static UserDAO userDAO = new UserDAO();

	/**
	 * Creates a new user based on the provided user object after performing
	 * validation.
	 *
	 * @param newUser The user object to be created.
	 * @return The created user object if successful.
	 * @throws ValidationException If the provided user fails validation checks.
	 * @throws ServiceException    If an error occurs during the user creation
	 *                             process.
	 */
	public User create(User newUser) throws ValidationException, ServiceException {
		User user;
		try {
			UserValidator.createValidate(newUser);
			user = userDAO.create(newUser);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while creating user");
		}
		return user;
	}

	/**
	 * Updates the user information in the system.
	 *
	 * This method updates the user's information in the data store by first
	 * performing validation on the provided user object using the
	 * {@link UserValidator#updateValidate(User)} method. If the validation is
	 * successful, the user's information is updated using the
	 * {@link UserDAO#update(User)} method. If there's a persistence error during
	 * the update, a {@link ServiceException} is thrown.
	 *
	 * @param updateUser The user object containing the updated information.
	 * @throws ValidationException if the provided user information is invalid.
	 * @throws ServiceException    if there's an error while updating the user
	 *                             information.
	 */
	public void update(User updateUser) throws ValidationException, ServiceException {

		try {
			UserValidator.updateValidate(updateUser);
			userDAO.update(updateUser);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while updating user");
		}

	}

	/**
	 * Updates the user's image.
	 *
	 * This method updates the image associated with a user. It performs validation
	 * on the provided User object and then updates the image in the data access
	 * layer (DAO).
	 *
	 * @param updateImage The User object containing the updated image information.
	 * @throws ValidationException If the provided User object fails validation.
	 * @throws ServiceException    If an error occurs while updating the image in
	 *                             the DAO.
	 */
	public void updateImage(User updateImage) throws ValidationException, ServiceException {

		try {
			UserValidator.updateImageValidate(updateImage);
			userDAO.updateImage(updateImage);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while updating image");
		}

	}

	/**
	 * Finds a user by their email address.
	 *
	 * This method validates the email address, and if it's valid, it queries the
	 * database to find the user associated with the provided email.
	 *
	 * @param email The email address of the user to find.
	 * @return The User object corresponding to the given email address, or null if
	 *         the user is not found.
	 * @throws ValidationException If the provided email address is not valid.
	 * @throws ServiceException    If an error occurs while finding the user in the
	 *                             database.
	 */
	public static User findUserByEmail(String email) throws ValidationException, ServiceException {
		try {
			UserValidator.findUserValidate(email);
			UserDAO userDAO = new UserDAO();
			return userDAO.findUser(email);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while finding user");
		}

	}

	/**
	 * Logs in a user by performing user login validation and retrieving user
	 * details.
	 *
	 * This method performs the following steps: 1. Validates the user login
	 * information using the UserValidator class. 2. Attempts to log in the user by
	 * calling the loginUser method of the userDAO. 3. If successful, returns the
	 * user details. 4. If there is a PersistenceException, it is caught and
	 * re-thrown as a ServiceException.
	 *
	 * @param user The User object containing login information.
	 * @return The User object representing the logged-in user.
	 * @throws ValidationException If the user login information is invalid.
	 * @throws ServiceException    If an error occurs while attempting to log in the
	 *                             user.
	 */
	public User loginUser(User user) throws ValidationException, ServiceException {

		User userDetail = null;
		try {
			UserValidator.loginValidation(user);
			userDetail = userDAO.loginUser(user);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while updating user");
		}

		return userDetail;

	}

	/**
	 * Retrieves user details by their ID from the database.
	 *
	 * @param id The unique identifier of the user to retrieve.
	 * @return A User object containing the user's details if found, or null if the
	 *         user doesn't exist.
	 * @throws ValidationException If the provided ID is invalid or doesn't meet the
	 *                             required criteria.
	 * @throws ServiceException    If an error occurs while finding the user in the
	 *                             database.
	 */
	public static User userdetail(int id) throws ValidationException, ServiceException {
		try {
			UserDAO userDAO = new UserDAO();
			return userDAO.userDetail(id);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while finding user");
		}

	}

}
