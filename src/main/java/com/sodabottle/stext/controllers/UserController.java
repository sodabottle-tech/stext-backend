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
import com.sodabottle.stext.models.dtos.PushOverDto;
import com.sodabottle.stext.models.dtos.TelegramDto;
import com.sodabottle.stext.repos.UserRepo;
import com.sodabottle.stext.service.AsyncPushOverService;
import com.sodabottle.stext.service.TelegramClient;
import com.sodabottle.stext.service.UserService;
import com.sodabottle.stext.service.UserValidationService;
import com.sodabottle.stext.utils.LogUtils;
import com.sodabottle.stext.utils.LogUtils.LogState;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AsyncPushOverService pushOverService;

    @Autowired
    private TelegramClient telegramClient;
    
    @Autowired
    private UserValidationService userValidationService;

    @GetMapping()
    public List<User> getUsers() {
        log.info("Get All Users");
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        LogUtils.appender(log);
        if (StringUtils.isEmpty(id)) {
            log.info("Get User Details - ID cannot be null");
            return ResponseEntity.badRequest().build();
        }

        LogUtils.logMessage("Get User Details for ID: " + id, log, LogState.INFO);
        User user = userRepo.findById(id).get();
        LogUtils.logMessage("User Retrieved Successfully with user: " + user, log, LogState.INFO);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    public ResponseEntity<Object> postUsers(@RequestBody User user) {
        LogUtils.appender(log);
        LogUtils.logMessage("Creating new User - Request: " + user, log, LogState.INFO);
        
        if(!userValidationService.validateUser(user)) {
        	LogUtils.logMessage("Invalid Email/Mobile", log, LogState.ERROR);
        	return ResponseEntity.badRequest().body("Invalid Email/Mobile!");
        }
        
        user = userService.save(user);
        LogUtils.appender(log);

        pushOverService.postMessage(PushOverDto.builder().title("****** User Registration ******").message(user.toString()).build());
        telegramClient.postMessage(TelegramDto.builder().text(" ****** User Registration ****** \n \n : " + user.toString()).build());
        return ResponseEntity.ok().body(user);
    }


}
