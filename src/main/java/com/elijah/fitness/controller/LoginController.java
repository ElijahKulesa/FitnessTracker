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

import com.elijah.fitness.model.User;
import com.elijah.fitness.service.AccountService;
import com.elijah.fitness.viewModel.AccountLogin;

@Controller
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	AccountService accountService;

	@GetMapping
	public String GetLoginPage(HttpSession session, Model model) {
		if(session.getAttribute("userId") != null)
			return "redirect:home";
		
		model.addAttribute("accountLogin", new AccountLogin());
		return "login";
	}
	
	@PostMapping
	public String Login(@ModelAttribute("accountLogin") AccountLogin account, BindingResult result, HttpSession session) {
		
		if(accountService.isCorrectPassword(account.getUsername(), account.getPassword())) {
			
			setupSession(account, session);
			
			return "redirect:home";
		}
		else {
			
			result.reject("account.incorrectLogin", "Incorrect username and/or password");
			
			account.setPassword("");
			return "login";
		}
	}

	private void setupSession(AccountLogin account, HttpSession session) {
		User user = accountService.getUser(account.getUsername());
		
		session.setAttribute("userId", user.getId());
		session.setAttribute("firstName", user.getFirstName());
		session.setAttribute("lastName", user.getLastName());
		session.setAttribute("username", user.getUsername());
	}
}
