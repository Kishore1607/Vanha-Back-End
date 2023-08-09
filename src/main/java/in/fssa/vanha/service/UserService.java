package in.fssa.vanha.service;

import in.fssa.vanha.model.User;
import in.fssa.vanha.dao.UserDAO;
import in.fssa.vanha.validator.UserValidator;

public class UserService {

	static UserDAO userDAO = new UserDAO();

	public void create(User newUser) throws Exception {
		UserValidator.createValidate(newUser);
		userDAO.create(newUser);
	}

	public void update(User updateUser) throws Exception {
		UserValidator.updateValidate(updateUser);
		userDAO.update(updateUser);
	}
	
	public static User findUserByEmail (String email) throws Exception {
		UserValidator.findUserValidate(email);
		return userDAO.findUserByEmail(email);
	}

}
