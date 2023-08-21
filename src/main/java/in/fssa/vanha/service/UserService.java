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
	public void create(User newUser) throws ValidationException, ServiceException{
		try {
			UserValidator.createValidate(newUser);
			userDAO.create(newUser);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while creating user");
		}
	}

	/**
	 * 
	 * @param updateUser
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public void update(User updateUser) throws ValidationException, ServiceException{

		try {
			UserValidator.updateValidate(updateUser);
			userDAO.update(updateUser);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while updating user");
		}

	}

	/**
	 * 
	 * @param email
	 * @return User
	 * @throws ValidationException
	 * @throws ServiceException
	 */
	public static User findUserByEmail(String email) throws ValidationException, ServiceException{
		try {
			UserValidator.findUserValidate(email);
			return userDAO.findUserByEmail(email);
		} catch (PersistenceException e) {
			throw new ServiceException("Error while finding user");
		}
		
	}

}
