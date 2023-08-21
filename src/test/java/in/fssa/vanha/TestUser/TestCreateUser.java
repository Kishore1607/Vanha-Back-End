package in.fssa.vanha.TestUser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.exception.*;
import in.fssa.vanha.UserRandomGenerator;

public class TestCreateUser {
	
	@Test
	public void testCreateUserWithValidData() {
	    UserService us = new UserService();
	    User newUser = new User();
	    UserRandomGenerator gene = new UserRandomGenerator();
	    newUser.setName(gene.nameGenerator());
	    newUser.setEmail(gene.emailGenerator());
	    newUser.setPassword(gene.passwordGenerator());
	    newUser.setNumber(gene.numberGenenrator());
	    newUser.setLocation(gene.locationGenerator());
	    
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
	    
	    assertThrows(ValidationException.class, () -> {
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
	    
	    assertThrows(ValidationException.class, () -> {
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
	    
	    assertThrows(ValidationException.class, () -> {
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
	    
	    assertThrows(ValidationException.class, () -> {
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
	    
	    assertThrows(ValidationException.class, () -> {
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
	    assertThrows(ServiceException.class, () -> {
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

	    assertThrows(ValidationException.class, () -> {
	        us.create(newUser);
	    });
	}

	@Test
	public void testCreateUserWithNullInput() {
	    UserService us = new UserService();
	    User newUser = null;

	    assertThrows(ValidationException.class, () -> {
	        us.create(newUser);
	    });
	}

}
