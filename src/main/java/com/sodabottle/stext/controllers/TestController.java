package com.sodabottle.stext.controllers;

import com.sodabottle.stext.models.dtos.PushOverDto;
import com.sodabottle.stext.models.dtos.TelegramDto;
import com.sodabottle.stext.service.AsyncPushOverService;
import com.sodabottle.stext.service.TelegramClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
@Slf4j
public class TestController {
    @Autowired
    private AsyncPushOverService pushOverService;

    @Autowired
    private TelegramClient telegramClient;

    @GetMapping("/testSuccess")
    public void test() {
        pushOverService.postMessage(PushOverDto.builder().title("****** User Registration ******").message("test user").build());
        telegramClient.postMessage(TelegramDto.builder().text("******* Test Telegram via testSuccess API *******").build());
    }

    @GetMapping("/testError")
    public void testError() {
        throw new RuntimeException(" test error ");
    }
}
