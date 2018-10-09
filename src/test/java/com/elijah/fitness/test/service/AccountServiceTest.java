package com.elijah.fitness.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.elijah.fitness.data.FitnessDAO;
import com.elijah.fitness.model.User;
import com.elijah.fitness.model.UserAndPassword;
import com.elijah.fitness.service.AccountService;

public class AccountServiceTest {

	@Mock private FitnessDAO fitnessDAO;
	
	@InjectMocks
	private AccountService accountService = new AccountService();
	
	/*
	 * Some default values:
	 * password is test123, and the PasswordHash is a hash of that value
	 * username is Deere
	 * first name is Jon, last name Doe
	 * id 2
	 */
	
	private final int id = 2;
	private final String firstName = "Jon";
	private final String lastName = "Doe";
	private final String username = "Deere";
	private final String password = "test123";
	private final String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
	
	//Factory Methods
	private User TestUser() {
		User u = new User();
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setUsername(username);
		u.setId(id);
		
		return u;
	}
	
	//Test Setup	
	@Before public void setupMocks() {
		MockitoAnnotations.initMocks(this);
		
		when(fitnessDAO.getPasswordHash(username)).thenReturn(passwordHash);
		
		when(fitnessDAO.getUser(username)).thenReturn(TestUser());
		
		when(fitnessDAO.getUsernameCount(username)).thenReturn(0);
		
		when(fitnessDAO.getUsernameCount("incorrect321")).thenReturn(1);
		
		doAnswer((i) -> { 
			saveUserAccountResult = i.getArgumentAt(0, UserAndPassword.class);
			
			return null;
		}).when(fitnessDAO).saveUserAccount(any(UserAndPassword.class));
	}
	
	@Before public void clearResults() {
		saveUserAccountResult = null;
	}
	
	//getUser() Tests
	@Test public void getUser_returnsUser() {
		User result = accountService.getUser(username);
		
		assertThat(result, equalTo(TestUser()));
	}
	
	@Test public void getUser_returnsOnNull() {
		User result = accountService.getUser("notDeere");
		
		assertThat(result, equalTo(null));
	}
	
	//isCorrectPassword() Tests
	@Test public void isCorrectPassword_returnsTrueOnCorrect() {
		
		boolean result = accountService.isCorrectPassword(username, password);
		
		assertThat(result, is(true));
	}
	
	@Test public void isCorrectPassword_returnsFalseOnIncorrect() {
		
		boolean result = accountService.isCorrectPassword(username, "incorrect321");
		
		assertThat(result, is(false));
		
	}
	
	@Test public void isCorrectPassword_returnsFalseOnNullUsername() {
		boolean result = accountService.isCorrectPassword(null, password);
		
		assertThat(result, is(false));
	}
	
	@Test public void isCorrectPassword_returnsFalseOnNullPassword() {
		boolean result = accountService.isCorrectPassword(username, null);
		
		assertThat(result, is(false));
	}
	
	@Test public void isCorrectPassword_returnsFalseOnNullUsernameAndPassword() {
		boolean result = accountService.isCorrectPassword(null, null);
		
		assertThat(result, is(false));
	}
	
	@Test public void isCorrectPassword_returnsFalseOnNullGetPassword() {
		when(fitnessDAO.getPasswordHash(any(String.class))).thenReturn(null);
		
		boolean result = accountService.isCorrectPassword(username, password);
		
		assertThat(result, is(false));
	}

	//usernameTaken() Tests
	@Test public void usernameTaken_returnsFalseOnZero() {
		boolean result = accountService.isUsernameTaken(username);
		
		assertThat(result, is(false));
	}
	
	@Test public void usernameTaken_returnsTrueOnOneOrGreater() {
		boolean result = accountService.isUsernameTaken("incorrect321");
		
		assertThat(result, is(true));
	}
	
	@Test public void usernameTaken_returnsTrueOnNullArg() {
		boolean result = accountService.isUsernameTaken(null);
		
		assertThat(result, is(true));
	}
	
	//registerUser() Tests
	UserAndPassword saveUserAccountResult;
		
	@Test public void registerUser_savesCorrectUser() {
		
		accountService.registerUser(firstName, lastName, username, password);
		
		assertThat(saveUserAccountResult.getFirstName(), equalTo(firstName));
		assertThat(saveUserAccountResult.getLastName(), equalTo(lastName));
		assertThat(saveUserAccountResult.getUsername(), equalTo(username));
		
		boolean correctHash = BCrypt.checkpw(password, saveUserAccountResult.getPasswordHash());
		assertThat(correctHash, is(true));
		
	}
	
	//Helper function for following tests 
 	private boolean doesRegisterUserThrow(String firstName, String lastName, String username, String password) {
		try {
			accountService.registerUser(firstName, lastName, username, password);
		}
		catch(Exception e) {
			return true;
		}
		
		return false;
	}
	
	@Test public void registerUser_throwsOnNullFirstName() {
		boolean result = doesRegisterUserThrow(null, lastName, username, password);
		
		assertThat(result, is(true));
	}
	
	@Test public void registerUser_throwsOnNullLastName() {
		boolean result = doesRegisterUserThrow(firstName, null, username, password);
		
		assertThat(result, is(true));
	}
	
	@Test public void registerUser_throwsOnNullUsername() {
		boolean result = doesRegisterUserThrow(firstName, lastName, null, password);
		
		assertThat(result, is(true));
	}
	
	@Test public void registerUser_throwsOnNullPassword() {
		boolean result = doesRegisterUserThrow(firstName, lastName, username, null);
		
		assertThat(result, is(true));
	}
}
