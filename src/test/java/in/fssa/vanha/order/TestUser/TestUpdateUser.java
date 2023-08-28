package in.fssa.vanha.order.TestUser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import in.fssa.vanha.model.User;
import in.fssa.vanha.service.UserService;
import in.fssa.vanha.MocValue;
import in.fssa.vanha.exception.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUpdateUser {

	@Test
	@Order(2)
	public void testUpadeUserWithValidData() {

		UserService us = new UserService();
		User updateUser = new User();
		
		updateUser.setName("Updated Name");
		updateUser.setEmail(MocValue.email);
		updateUser.setNumber(9835467892l);
		updateUser.setLocation("New York, USA");
		
		assertDoesNotThrow(()-> {
			us.update(updateUser);
		});
	}
	
	@Test
	public void testUpdateNonExistentUser() {
	    UserService userService = new UserService();
	    User nonExistentUser = new User();
	    nonExistentUser.setName("Non Existent");
	    nonExistentUser.setEmail("non.existent@example.com");
	    nonExistentUser.setNumber(9876543210L);
	    nonExistentUser.setLocation("Paris, France");
	    
	    assertThrows(ServiceException.class, () -> {
	        userService.update(nonExistentUser);
	    });
	}
	
	@Test
	public void testUpdateUserWithInvalidEmailPattern() {
	    UserService userService = new UserService();
	    User user = new User();
	    user.setName("Invalid Email");
	    user.setEmail("invalid.email.com"); // Invalid email pattern
	    user.setNumber(9876543210L);
	    user.setLocation("London, UK");
	    
	    assertThrows(ValidationException.class, () -> {
	        userService.update(user);
	    });
	}
	
	@Test
	public void testUpdateUserWithInvalidNumber() {
	    UserService userService = new UserService();
	    User user = new User();
	    user.setName("Invalid Number");
	    user.setEmail("invalid.number@example.com");
	    user.setNumber(55555555L); // Invalid number
	    
	    assertThrows(ValidationException.class, () -> {
	        userService.update(user);
	    });
	}
	
	@Test
	public void testUpdateUserWithInvalidNamePattern() {
	    UserService userService = new UserService();
	    User user = new User();
	    user.setName("Inv@lid Nam3"); // Invalid name pattern
	    user.setEmail("invalid.name@example.com");
	    user.setNumber(9876543210L);
	    user.setLocation("Berlin, Germany");
	    
	    assertThrows(ValidationException.class, () -> {
	        userService.update(user);
	    });
	}
	
	@Test
	public void testUpdateUserWithInvalidLocationPattern() {
	    UserService userService = new UserService();
	    User user = new User();
	    user.setName("Invalid Location");
	    user.setEmail("invalid.location@example.com");
	    user.setNumber(9876543210L);
	    user.setLocation("Invalid Location123"); // Invalid location pattern
	    
	    assertThrows(ValidationException.class, () -> {
	        userService.update(user);
	    });
	}
	
}
