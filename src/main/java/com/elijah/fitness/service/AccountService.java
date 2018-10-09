package com.elijah.fitness.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.elijah.fitness.data.FitnessDAO;
import com.elijah.fitness.model.User;
import com.elijah.fitness.model.UserAndPassword;

@Service("accountService")
public class AccountService {
	
	@Autowired
	public FitnessDAO fitnessDAO;

	@Transactional
	public User getUser(String username) {
		return fitnessDAO.getUser(username);
	}

	@Transactional
	public boolean isCorrectPassword(String username, String password) {
		if(username == null
				|| password == null)
			return false;
		
		String passwordHash = fitnessDAO.getPasswordHash(username);
		
		if(passwordHash == null)
			return false;
		
		boolean result = BCrypt.checkpw(password, passwordHash);
		
		return result;
	}

	@Transactional
	public boolean isUsernameTaken(String username) {
		if(username == null)
			return true;
		
		int count = fitnessDAO.getUsernameCount(username);
		
		return count != 0;
	}

	@Transactional
	public void registerUser(String firstName, String lastName, String username, String password) throws NullPointerException{
		if(firstName == null) {
			throw new NullPointerException("First name cannot be null");
		}
		if(lastName == null) {
			throw new NullPointerException("Last name cannot be null");
		}
		if(username == null) {
			throw new NullPointerException("Username cannot be null");
		}
		if(password == null) {
			throw new NullPointerException("Password cannot be null");
		}
		
		UserAndPassword user = new UserAndPassword();
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		
		String hash = BCrypt.hashpw(password, BCrypt.gensalt());
		
		user.setPasswordHash(hash);
		
		fitnessDAO.saveUserAccount(user);
	}
}
