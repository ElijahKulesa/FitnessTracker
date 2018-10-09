package com.elijah.fitness.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("logout")
public class LogoutController {	
	@GetMapping
	public String logoutRequest(HttpSession session) {
		
		if(session.getAttribute("userId") != null)
			session.invalidate();
			
			
		return "redirect:login";
		
	}
}
