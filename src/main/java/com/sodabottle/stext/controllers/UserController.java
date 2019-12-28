package com.sodabottle.stext.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sodabottle.stext.models.User;
import com.sodabottle.stext.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	@GetMapping()
	public List<User> getUsers() {
		log.info("Get All Users");
		return userRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") Long id) {
		if(null == id) {
			log.info("Get User Details - ID cannot be null");
			return null;
		}
		log.info("Get User Details for ID: " + id);
		return userRepo.findById(id).get();
	}
	
	@PostMapping()
	public String postUsers(@RequestBody User user){
		userRepo.save(user);
		log.info("User Created with email: " + user.getEmail() + " name: " + user.getName() + "mobile: " + user.getName());
		return "User Created Successfully!";
	}
}
