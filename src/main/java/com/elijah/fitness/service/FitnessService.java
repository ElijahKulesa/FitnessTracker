package com.elijah.fitness.service;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elijah.fitness.data.FitnessDAO;
import com.elijah.fitness.model.Exercise;

@Service("fitnessService")
public class FitnessService {

	@Autowired
	FitnessDAO fitnessDAO;
	
	@Transactional
	public Exercise getLatestRecord(int userId) {
		
		Exercise exercise = fitnessDAO.getLatestExerciseForUser(userId);
		
		return exercise;
	}

	@Transactional
	public void setRecord(int userId, int exerciseMinutes, int goalMinutes, Date date) {
		if(date == null) {
			throw new NullPointerException("Date cannot be null");
		}
		
		Exercise exercise = fitnessDAO.getExerciseByDate(userId, date);
		
		if(exercise == null) {
			exercise = new Exercise();
		}
		else if(exercise.getDate().equals(date)
				&& exercise.getExerciseMinutes() == exerciseMinutes
				&& exercise.getGoalMinutes() == goalMinutes) {
			return;
		}
		
		exercise.setDate(date);
		exercise.setExerciseMinutes(exerciseMinutes);
		exercise.setUserId(userId);
		exercise.setGoalMinutes(goalMinutes);
		
		fitnessDAO.saveExercise(exercise);
	}
}
