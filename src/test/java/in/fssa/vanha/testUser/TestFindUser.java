package in.fssa.vanha.testUser;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.service.UserService;

public class TestFindUser {

	@Test
	public void testCreateUserWithValidEmail() {

		String email = "karkuvel@gmail.com";
		assertDoesNotThrow(() -> {
			UserService.findUserByEmail(email);
		});
	}

	@Test
	public void testCreateUserWithEmptyEmail() {

		String email = "";
		assertThrows(ValidationException.class, () -> {
			UserService.findUserByEmail(email);
		});
	}

	@Test
	public void testCreateUserWithNullEmail() {

		String email = null;
		assertThrows(ValidationException.class, () -> {
			UserService.findUserByEmail(email);
		});
	}
}
