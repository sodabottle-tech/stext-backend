package com.sodabottle.stext.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sodabottle.stext.models.User;
import com.sodabottle.stext.repos.UserRepo;
import com.sodabottle.stext.utils.LogUtils;
import com.sodabottle.stext.utils.LogUtils.LogState;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	public User findUserById(Long userId) {
		return userRepo.findById(userId).get();
	}

	public User save(User user) {
		User userExsiting = userRepo.findByEmailOrMobile(user.getEmail(), user.getMobile());
		if(null == userExsiting){
			LogUtils.logMessage("User Created with email: " + user.getEmail() + " name: " + user.getName() + "mobile: " + user.getName(), log, LogState.INFO);
			return userRepo.save(user);
		}
		LogUtils.logMessage("Existing User Signed In with email: " + user.getEmail() + " name: " + user.getName() + "mobile: " + user.getName(), log, LogState.INFO);
		return userExsiting;
	}

}
