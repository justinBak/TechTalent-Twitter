package com.tts.techtalenttwitter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.techtalenttwitter.model.Tweet;
import com.tts.techtalenttwitter.model.UserProfile;
import com.tts.techtalenttwitter.service.TweetService;
import com.tts.techtalenttwitter.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TweetService tweetService;

	@GetMapping(value = "/users/{username}")
	public String getUser(@PathVariable(value="username") String username, Model model) {
		UserProfile loggedInUser = userService.getLoggedInUser();
		UserProfile user = userService.findByUsername(username);
		List<Tweet> tweets = tweetService.findAllByUser(user);
		
		List<UserProfile>following = loggedInUser.getFollowing();
		boolean isFollowing = false;
		for(UserProfile followedUser : following) {
			if(followedUser.getUsername().equals(username)) {
				isFollowing = true;
			}
		}
		boolean isSelfPage = loggedInUser.getUsername().equals(username);
		model.addAttribute("isSelfPage", isSelfPage);
		model.addAttribute("following", isFollowing);
		model.addAttribute("tweetList", tweets);
		model.addAttribute("user", user);
		return "user";
	}
	
	@GetMapping(value="/users")
	public String getUsers(Model model) {
		List<UserProfile>users = userService.findAll();
		model.addAttribute("users", users);
		
		UserProfile loggedInUser = userService.getLoggedInUser();
		List<UserProfile> usersFollowing = loggedInUser.getFollowing();
		SetFollowingStatus(users, usersFollowing, model);
		SetTweetCounts(users, model);
		return "users";
	}
	
	private void SetFollowingStatus(List<UserProfile> users, List<UserProfile> usersFollowing, Model model) {
		Map<String, Boolean> followingStatus = new HashMap<>();
		String username = userService.getLoggedInUser().getUsername();
		for (UserProfile user: users) {
			if(usersFollowing.contains(user)) {
				followingStatus.put(user.getUsername(), true);
			} else if(!user.getUsername().equals(username)) {
				followingStatus.put(user.getUsername(), false);
			}
		}
		model.addAttribute("followingStatus", followingStatus);
	}
	
	private void SetTweetCounts(List<UserProfile> users, Model model) {
		Map<String, Integer> tweetCounts = new HashMap<>();
		// Find tweetcounts here. TODO!
		for(UserProfile user : users) {
			List<Tweet> tweets = tweetService.findAllByUser(user);
			tweetCounts.put(user.getUsername(), tweets.size());
		}
		
		model.addAttribute("tweetCounts", tweetCounts);
	}
	
}
