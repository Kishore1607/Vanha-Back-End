package in.fssa.vanha.testUser;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;

public class TestLoginUser {

	@Test
	public void testLoginUserWithvalidEmail() {

		User newUser = new User();
		newUser.setEmail("karkuvel@gmail.com");
		newUser.setPassword("Kark#123");

		UserService userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testLoginUserWithInvalidEmail() {

		User newUser = new User();
		newUser.setEmail("Invalid email");
		newUser.setPassword("Kark#123");

		UserService userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testLoginUserWithInvalidPassword() {

		User newUser = new User();
		newUser.setEmail("karkuvel@gmail.com");
		newUser.setPassword("Kark#1234567kiasgu");

		UserService userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testLoginUserWithEmptyEmail() {

		User newUser = new User();
		newUser.setEmail("");
		newUser.setPassword("Kark#123");

		UserService userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testLoginUserWithEmptyPassword() {

		User newUser = new User();
		newUser.setEmail("karkuvel@gmail.com");
		newUser.setPassword("");

		UserService userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
}
