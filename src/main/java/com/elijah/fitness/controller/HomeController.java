package com.elijah.fitness.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.elijah.fitness.model.Exercise;
import com.elijah.fitness.service.FitnessService;
import com.elijah.fitness.viewModel.LatestExercise;

@Controller
@RequestMapping(value = {"/", "home",})
public class HomeController {
	
	@Autowired
	FitnessService fitnessService;
	
	@GetMapping
	public String home(HttpSession session, Model model) {

		if(session.getAttribute("userId") == null)
			return "redirect:login";
		
		int id = (Integer) session.getAttribute("userId");
		
		Exercise exercise = fitnessService.getLatestRecord(id);
		
		LatestExercise viewModel;
		if(exercise == null) {
			viewModel = new LatestExercise();
		}
		else {
			viewModel = new LatestExercise(exercise);
		}
		
		model.addAttribute("home", viewModel);
		
		return "home";
	}
	
	@PostMapping
	public String updateToday(HttpSession session, @ModelAttribute("home") LatestExercise latestExercise, BindingResult result) {

		if(session.getAttribute("userId") == null)
			return "home";
		
		validateLatestExercise(latestExercise, result);
		
		if(result.hasErrors())
			return "home";
		
		int userId = (Integer) session.getAttribute("userId");
		
		fitnessService.setRecord(userId, 
				latestExercise.getExerciseMinutes(),
				latestExercise.getGoalMinutes(),
				latestExercise.getExerciseDate());
		
		return "home";
	}

	private void validateLatestExercise(LatestExercise latestExercise, BindingResult result) {
		if(latestExercise.getExerciseMinutes() < 0 || latestExercise.getExerciseMinutes() > 1440)
			result.reject("exercise.invalidMinutes", "Minutes exercised must be between 0 and 1440");
		if(latestExercise.getExerciseDate() == null)
			result.reject("exercise.invalidDate", "Date must be a valid date");
		if(latestExercise.getGoalMinutes() < 0 || latestExercise.getGoalMinutes() > 1440)
			result.reject("goal.invalidMinutes", "Your goal must be between 0 and 1440 minutes");
	}
}
