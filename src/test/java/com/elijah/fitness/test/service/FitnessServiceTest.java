package com.elijah.fitness.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.elijah.fitness.data.FitnessDAO;
import com.elijah.fitness.model.Exercise;
import com.elijah.fitness.service.FitnessService;

public class FitnessServiceTest {
	
	@Mock private FitnessDAO fitnessDAO;
	
	@InjectMocks
	private FitnessService fitnessService;
	
	/*
	 * Our test data has:
	 *  Associated user id of 0
	 *  Latest exercise id of 15
	 * 	100 minutes exercised on Jan 1st, 2018
	 * 	Goal of 200
	 */
	
	private final int id = 0;
	private final int exerciseId = 15;
	private final int exerciseMinutes = 100;
	private final Date date = new Date(1514764800000L); //Long is Jan 1st, 2018 0hr 0min 0sec UTC
	private final int goalMinutes = 200;
	
	//Factory Methods	
	private Exercise TestExercise() {
		Exercise e = new Exercise();
		e.setDate(date);
		e.setId(exerciseId);
		e.setExerciseMinutes(exerciseMinutes);
		e.setGoalMinutes(goalMinutes);
		
		return e;
	}
	
	//Test Setup
	@Before public void setupMocks() {
		fitnessService = new FitnessService();
		
		MockitoAnnotations.initMocks(this);
		
		when(fitnessDAO.getLatestExerciseForUser(id)).thenAnswer((i) -> {
			getResult = TestExercise();
			
			return getResult;
		});
		
		when(fitnessDAO.getExerciseByDate(id, date)).thenAnswer((i) -> {
			getResult = TestExercise();
			
			return getResult;
		});
		
		doAnswer((i) -> {
			setResult = i.getArgumentAt(0, Exercise.class);
			
			return null;
		}).when(fitnessDAO).saveExercise(any(Exercise.class));
	}
	
	@Before public void clearResults() {
		setResult = null;
		getResult = null;
	}

	
	//FitnessService.getLatestRecord
	@Test public void getLatestRecord_returnsValidExercise() {
		
		Exercise result = fitnessService.getLatestRecord(id);
		
		assertThat(result, equalTo(TestExercise()));
	}

	//FitnessService.setLatestRecord
	private Exercise setResult;
	private Exercise getResult;
	
	@Test public void setRecord_createsNewExerciseFromNewDate() {
		
		fitnessService.setRecord(id, exerciseMinutes, goalMinutes, new Date(System.currentTimeMillis()));
		
		assertThat(getResult, not(sameInstance(setResult)));
	}
	
	@Test public void setRecord_updatesExerciseFromNewMinutes() {
		
		fitnessService.setRecord(id, 50, goalMinutes, date);
		
		assertThat(setResult, sameInstance(getResult));
		assertThat(setResult.getExerciseMinutes(), equalTo(50));
	}
	
	@Test public void setRecord_updatesExerciseFromNewGoal() {
		
		fitnessService.setRecord(id, exerciseMinutes, 50, date);
		
		assertThat(setResult, sameInstance(getResult));
		assertThat(setResult.getGoalMinutes(), equalTo(50));
	}
}
