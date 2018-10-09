package com.elijah.fitness.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exercise_history")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "exercise_minutes")
	private int exerciseMinutes;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "goal_minutes")
	private int goalMinutes;
	
	@Column(name = "user_id")
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExerciseMinutes() {
		return exerciseMinutes;
	}

	public void setExerciseMinutes(int minutes) {
		this.exerciseMinutes = minutes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getGoalMinutes() {
		return goalMinutes;
	}

	public void setGoalMinutes(int goal) {
		this.goalMinutes = goal;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof Exercise)) {
			return false;
		}
		
		Exercise other = (Exercise) o;
		
		if(this.getDate() != null && other.getDate() != null
				&& this.getDate().equals(other.getDate())
				&& this.getExerciseMinutes() == other.getExerciseMinutes()
				&& this.getId() == other.getId()) {
			return true;
		}
		else {
			return false;
		}
		
	}
}
