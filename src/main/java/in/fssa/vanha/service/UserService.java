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
	 * @throws PersistenceException
	 */
	public void create(User newUser) throws ValidationException, ServiceException, PersistenceException  {
		UserValidator.createValidate(newUser);
		userDAO.create(newUser);
	}

	/**
	 * 
	 * @param updateUser
	 * @throws ValidationException
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public void update(User updateUser) throws ValidationException, ServiceException, PersistenceException  {
		UserValidator.updateValidate(updateUser);
		userDAO.update(updateUser);
	}
	
	/**
	 * 	
	 * @param email
	 * @return User
	 * @throws ValidationException
	 * @throws ServiceException
	 * @throws PersistenceException
	 */
	public static User findUserByEmail (String email) throws ValidationException, ServiceException, PersistenceException  {
		UserValidator.findUserValidate(email);
		return userDAO.findUserByEmail(email);
	}

}
