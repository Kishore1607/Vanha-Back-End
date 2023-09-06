package in.fssa.vanha.testUser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.MocValue;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;

public class TestUpdateUser {

	@Test
	public void testUpdateValidateWithValidData() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName(MocValue.userName);
		updateUser.setEmail("Karukvel@gmail.com");
		updateUser.setNumber(MocValue.number);
		updateUser.setLocation(MocValue.location);
		updateUser.setImage("https://source.unsplash.com/featured/200x200?people");

		assertDoesNotThrow(() -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithNull() {
		UserService userService = new UserService();
		User updateUser = new User();

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidEmail() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Valid Name");
		updateUser.setEmail("invalid-email");
		updateUser.setNumber(9234567890l);
		updateUser.setLocation("Valid Location");
		updateUser.setImage(null);

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithEmptyEmail() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Karukvel");
		updateUser.setEmail("");
		updateUser.setNumber(8870825039l);
		updateUser.setLocation("Chennai");
		updateUser.setImage("https://source.unsplash.com/featured?people");

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithEmptyName() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("");
		updateUser.setEmail("wuww.ibpdci@gmail.com");
		updateUser.setNumber(8870825039l);
		updateUser.setLocation("Chennai");
		updateUser.setImage("https://source.unsplash.com/featured?people");

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithEmptyLocation() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Karukvel");
		updateUser.setEmail("wuww.ibpdci@gmail.com");
		updateUser.setNumber(8870825039l);
		updateUser.setLocation("");
		updateUser.setImage("https://source.unsplash.com/featured?people");
		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidName() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("#@123");
		updateUser.setEmail("valid@example.com");
		updateUser.setNumber(9234567890l);
		updateUser.setLocation("Valid Location");
		updateUser.setImage(null);
		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidLocation() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("kishore");
		updateUser.setEmail("valid@example.com");
		updateUser.setNumber(9234567890l);
		updateUser.setLocation("#123$#._");
		updateUser.setImage(null);
		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidNumber() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Valid Name");
		updateUser.setEmail("valid@example.com");
		updateUser.setNumber(-1);
		updateUser.setLocation("Valid Location");
		updateUser.setImage(null);

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidImage() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Valid Name");
		updateUser.setEmail("valid@example.com");
		updateUser.setNumber(8870825039l);
		updateUser.setLocation("Valid Location");
		updateUser.setImage("Invalid image url");

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidNameLength() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("ThisNameIsTooLongAndInvalidBecauseItExceedsFiftyCharacters");
		updateUser.setEmail("valid@example.com");
		updateUser.setNumber(9234567890l);
		updateUser.setLocation("Valid Location");
		updateUser.setImage(null);

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithInvalidLocationLength() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Name");
		updateUser.setEmail("valid@example.com");
		updateUser.setNumber(9234567890l);
		updateUser.setLocation(
				"anucnancuancjandjcnjanjdnccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
		updateUser.setImage(null);

		assertThrows(ValidationException.class, () -> {
			userService.update(updateUser);
		});
	}

	@Test
	public void testUpdateValidateWithNonExistentUser() {
		UserService userService = new UserService();
		User updateUser = new User();
		updateUser.setName("Valid Name");
		updateUser.setEmail("nonexistent@example.com");
		updateUser.setNumber(9234567890l);
		updateUser.setLocation("Valid Location");
		updateUser.setImage(null);

		assertThrows(ServiceException.class, () -> {
			userService.update(updateUser);
		});
	}

}
