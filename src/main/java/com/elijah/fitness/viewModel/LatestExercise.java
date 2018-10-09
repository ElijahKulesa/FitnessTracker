package com.elijah.fitness.viewModel;

import java.sql.Date;

import com.elijah.fitness.model.Exercise;

public class LatestExercise {

	private int exerciseMinutes;
	private Date exerciseDate;
	private int goalMinutes;
	
	public LatestExercise() {
		this.setExerciseDate(new Date(System.currentTimeMillis()));
	}
	
	public LatestExercise(int minutes, Date date, int goal) {
		this.setExerciseMinutes(minutes);
		if(date != null)
			this.setExerciseDate(date);
		else
			this.setExerciseDate(new Date(System.currentTimeMillis()));
		this.setGoalMinutes(goal);
	}
	
	public LatestExercise(Exercise e) {
		this(e.getExerciseMinutes(), e.getDate(), e.getGoalMinutes());
	}
	
	public int getExerciseMinutes() {
		return exerciseMinutes;
	}
	public void setExerciseMinutes(int excerciseMinutes) {
		this.exerciseMinutes = excerciseMinutes;
	}
	public Date getExerciseDate() {
		return exerciseDate;
	}
	public void setExerciseDate(Date exercisedate) {
		this.exerciseDate = exercisedate;
	}
	public int getGoalMinutes() {
		return goalMinutes;
	}
	public void setGoalMinutes(int goal) {
		this.goalMinutes = goal;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof LatestExercise))
			return false;
		LatestExercise other = (LatestExercise) o;
		
		if( 	   this.getExerciseDate() != null
				&& other.getExerciseDate() != null
				&& this.getExerciseDate().equals(other.getExerciseDate())
				
				&& this.getExerciseMinutes() == other.getExerciseMinutes()
				&& this.getGoalMinutes() == other.getGoalMinutes())
			return true;
		else
			return false;
	}
}
