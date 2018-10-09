package com.elijah.fitness.data;

import java.sql.Date;

import com.elijah.fitness.model.Exercise;
import com.elijah.fitness.model.User;
import com.elijah.fitness.model.UserAndPassword;

public interface FitnessDAO {

	public User getUser(String username);
	
	public int getUsernameCount(String username);
	
	public Exercise getLatestExerciseForUser(int userId);
	
	public Exercise getExerciseByDate(int userId, Date date);
	
	public void saveExercise(Exercise e);
	
	public void saveUserAccount(UserAndPassword account);
	
	public String getPasswordHash(String username);
}
