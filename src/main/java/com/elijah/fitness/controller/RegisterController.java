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

import com.elijah.fitness.service.AccountService;
import com.elijah.fitness.viewModel.AccountRegister;

@Controller
@RequestMapping("register")
public class RegisterController {
	
	@Autowired
	AccountService accountService;

	@GetMapping
	public String getRegisterPage(HttpSession session, Model model) {

		if(session.getAttribute("userId") != null)
			return "redirect:home";
		
		model.addAttribute("account", new AccountRegister() );
		return "register";
	}
	
	@PostMapping
	public String submitAccount(@ModelAttribute("account") AccountRegister account, BindingResult result, HttpSession session) {
		
		validateRegistration(account, result);
		
		if(result.hasErrors()) {
			
			account.setPassword("");
			account.setConfirmPassword("");
			
			return "register";
		}
		
		if(accountService.isUsernameTaken(account.getUsername())) {
			result.rejectValue("username", "account.username.taken", "Username already in use");
			return "register";
		}
		
		accountService.registerUser(
				account.getFirstName(),
				account.getLastName(),
				account.getUsername(),
				account.getPassword());
		
		return "redirect:login";
	}

	private void validateRegistration(AccountRegister account, BindingResult result) {
		
		if(account.getFirstName().length() < 3 || account.getFirstName().length() > 10)
			result.rejectValue("firstName", "account.firstName.invalidLength", "Your first name must be between 3 and 10 characters");
		if(account.getLastName().length() < 3 || account.getLastName().length() > 10)
			result.rejectValue("lastName", "account.lastName.invalidLength", "Your last name must be between 3 and 10 characters");
		if(account.getUsername().length() < 3 || account.getUsername().length() > 10)
			result.rejectValue("username", "account.username.invalidLength", "Your username must be between 3 and 15 characters");
		if(account.getPassword().isEmpty())
			result.rejectValue("password", "account.password.empty", "Password cannot be empty");
		if(! account.getPassword().equals(account.getConfirmPassword()))
			result.rejectValue("password", "account.password.invalidMatch", "Both passwords must match");
	}
}
