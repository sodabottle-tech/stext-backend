package com.sodabottle.stext.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sodabottle.stext.models.User;
import com.sodabottle.stext.repos.UserRepo;
import com.sodabottle.stext.utils.LogUtils;
import com.sodabottle.stext.utils.LogUtils.LogState;

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
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		LogUtils.appender(log);
		if(StringUtils.isEmpty(id)) {
			log.info("Get User Details - ID cannot be null");
			return ResponseEntity.badRequest().build();
		}
		
		LogUtils.logMessage("Get User Details for ID: " + id, log, LogState.INFO);
		User user = userRepo.findById(id).get();
		LogUtils.logMessage("User Retrieved Successfully with user: " + user, log, LogState.INFO);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping()
	public ResponseEntity<String> postUsers(@RequestBody User user){
		LogUtils.appender(log);
		LogUtils.logMessage("Creating new User - Request: " + user, log, LogState.INFO);
		userRepo.save(user);
		LogUtils.logMessage("User Created with email: " + user.getEmail() + " name: " + user.getName() + "mobile: " + user.getName(), log, LogState.INFO);
		LogUtils.appender(log);
		return ResponseEntity.ok().body("User Created Successfully!");
	}
}
