package com.sodabottle.stext.controllers;

import com.sodabottle.stext.models.User;
import com.sodabottle.stext.models.dtos.PushOverDto;
import com.sodabottle.stext.models.dtos.TelegramDto;
import com.sodabottle.stext.repos.UserRepo;
import com.sodabottle.stext.service.AsyncPushOverService;
import com.sodabottle.stext.service.TelegramClient;
import com.sodabottle.stext.utils.LogUtils;
import com.sodabottle.stext.utils.LogUtils.LogState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AsyncPushOverService pushOverService;

    @Autowired
    private TelegramClient telegramClient;

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
    public ResponseEntity<String> postUsers(@RequestBody User user) {
        LogUtils.appender(log);
        LogUtils.logMessage("Creating new User - Request: " + user, log, LogState.INFO);
        userRepo.save(user);
        LogUtils.logMessage("User Created with email: " + user.getEmail() + " name: " + user.getName() + "mobile: " + user.getName(), log, LogState.INFO);
        LogUtils.appender(log);

        pushOverService.postMessage(PushOverDto.builder().title("****** User Registration ******").message(user.toString()).build());
        telegramClient.postMessage(TelegramDto.builder().text(" ****** User Registration ****** \n \n : " + user.toString()).build());
        return ResponseEntity.ok().body("User Created Successfully!");
    }


}
