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
	 * 
	 * @param newUser
	 * @throws ValidationException
	 * @throws ServiceException
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
	 * 
	 * @param updateUser
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public void update(User updateUser) throws ValidationException, ServiceException {

		try {
			UserValidator.updateValidate(updateUser);
			userDAO.update(updateUser);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while updating user");
		}

	}

	public void updateImage(User updateImage) throws ValidationException, ServiceException {

		try {
			UserValidator.updateImageValidate(updateImage);
			userDAO.updateImage(updateImage);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while updating image");
		}

	}
	
	/**
	 * 
	 * @param email
	 * @return User
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static User findUserByEmail(String email) throws ValidationException, ServiceException {
		try {
			UserValidator.findUserValidate(email);
			return UserDAO.findUser(email);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while finding user");
		}

	}

	/**
	 * 
	 * @param updateUser
	 * @throws ValidationException
	 * @throws ServiceException
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
	
	public static User userdetail(int id) throws ValidationException, ServiceException {
		try {
			return UserDAO.userDetail(id);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while finding user");
		}

	}


}
