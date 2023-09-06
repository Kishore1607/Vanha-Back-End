package in.fssa.vanha.testUser;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.MocValue;
import in.fssa.vanha.exception.ServiceException;
import in.fssa.vanha.exception.ValidationException;
import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;

public class TestCreateUser {
	private UserService userService;

	@Test
	public void testCreateUserWithNull() {

		User newUser = new User();
		
		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithExistingEmail() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail("karukvel@gmail.com");
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage(null);

		userService = new UserService();
		assertThrows(ServiceException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithValidData() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail("karukvel@gmail.com");
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertDoesNotThrow(() -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithInvalidEmail() {

		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail("invalidemail");
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");
		
		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithInvalidName() {

		User newUser = new User();
		newUser.setName("#@Name");
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithInvalidPassword() {

		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword("short");
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithInvalidNumber1() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(1234567890l);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

	@Test
	public void testCreateUserWithInvalidNumber2() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(98765432109l);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithInvalidLocation() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation("#USA");
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithInvalidImage() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("Invalid URL pattern");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithEmptyEmail() {

		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail("");
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");
		
		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithEmptyPassword() {

		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword("");
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");
		
		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithEmptyName() {
		User newUser = new User();
		newUser.setName("");
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithEmptyLocation() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation("");
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithInvalidEmailLength() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail("qqqqqqqqjhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeewwwwwwwwwwwwww@gmail.com");
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithInvalidNameLength() {
		User newUser = new User();
		newUser.setName("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwweeeeeeeeeeeeeee");
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithInvalidPasswordLength() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword("#1234567890Kishore");
		newUser.setNumber(MocValue.number);
		newUser.setLocation(MocValue.location);
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}
	
	@Test
	public void testCreateUserWithInvalidLocationLength() {
		User newUser = new User();
		newUser.setName(MocValue.userName);
		newUser.setEmail(MocValue.email);
		newUser.setPassword(MocValue.password);
		newUser.setNumber(MocValue.number);
		newUser.setLocation("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwweeeeeeeeeee");
		newUser.setImage("https://source.unsplash.com/featured/?people");

		userService = new UserService();
		assertThrows(ValidationException.class, () -> {
			userService.create(newUser);
		});
	}

}
