package com.tts.techtalenttwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.techtalenttwitter.model.UserProfile;
import com.tts.techtalenttwitter.service.UserService;

@Controller
public class AuthorizationController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(value="/login") 
	public String login(){
		return "login";
	}
	
	@GetMapping(value="/signup")
	public String registration(Model model) {
		UserProfile user = new UserProfile();
		model.addAttribute("user", user);
		return "registration";
	}
	
	@PostMapping(value="/signup")
	public String createNewUser( @Valid UserProfile user, BindingResult bindingResult, Model model) {
		UserProfile userExists = userService.findByUsername(user.getUsername());
		if(userExists != null) {
			bindingResult.rejectValue("username", "error.user", "Username is already taken");
		}
		if(!bindingResult.hasErrors()) {
			userService.saveNewUser(user);
			model.addAttribute("success", "Sign up successful");
			model.addAttribute("user", new UserProfile());
		}
		return "registration";
	}
}
