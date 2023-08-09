package in.fssa.vanha.TestUser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;

public class TestCreateUser {
	
	@Test
	public void testCreateUserWithValidData() {
	    UserService us = new UserService();
	    User newUser = new User();
	    
	    newUser.setName("Nataliya Dyer");
	    newUser.setEmail("nat.dyr@example.com");
	    newUser.setPassword("Pass#456");
	    newUser.setNumber(9878987650l);
	    newUser.setLocation("Chennai.");
	    
	    assertDoesNotThrow(() -> {
	        us.create(newUser);
	    });
	}
	
	@Test
	public void testCreateUserWithAllStringEmpty() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("");
	    newUser.setEmail("");
	    newUser.setPassword("");
	    newUser.setNumber(9835467892l);
	    newUser.setLocation("");
	    
	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}
	
	@Test
	public void testCreateUserWithInvalidEmailPattern() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("John Doe");
	    newUser.setEmail("Invalid email");
	    newUser.setPassword("Pass#123");
	    newUser.setNumber(9835467892l);
	    newUser.setLocation("New York, USA");
	    
	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithInvalidNamePattern() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("John@*");
	    newUser.setEmail("john.doe@example.com");
	    newUser.setPassword("Pass#123");
	    newUser.setNumber(9835467892l);
	    newUser.setLocation("New York, USA");
	    
	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithInvalidPasswordPattern() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("John Doe");
	    newUser.setEmail("john.doe@example.com");
	    newUser.setPassword("Passwrd");
	    newUser.setNumber(9835467892l);
	    newUser.setLocation("New York, USA");
	    
	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithInvalidLocationPattern() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("John Doe");
	    newUser.setEmail("john.doe@example.com");
	    newUser.setPassword("Pass#123");
	    newUser.setNumber(9835467892l);
	    newUser.setLocation("# Invalid lcation");
	    
	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithExistingEmail() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("John Doe");
	    newUser.setEmail("john.doe@example.com");
	    newUser.setPassword("Pass#123");
	    newUser.setNumber(9835467892l);
	    newUser.setLocation("New York, USA");

	    // Assume the email "john.doe@example.com" already exists in the database
	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithInvalidPhoneNumber() {
	    UserService us = new UserService();
	    User newUser = new User();

	    newUser.setName("John Doe");
	    newUser.setEmail("john.doe@example.com");
	    newUser.setPassword("Pass#123");
	    newUser.setNumber(1234567890l);
	    newUser.setLocation("New York, USA");

	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithNullInput() {
	    UserService us = new UserService();
	    User newUser = null;

	    assertThrows(RuntimeException.class, () -> {
	        us.create(newUser);
	    });
	}

}