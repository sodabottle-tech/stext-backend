package com.sodabottle.stext.service;

import com.sodabottle.stext.clients.RestClient;
import com.sodabottle.stext.models.dtos.PushOverDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Async
@Slf4j
@Service
public class AsyncPushOverService {
    @Value("${pushover.url}")
    private String pushOverUrl;

    @Value("${pushover.user}")
    private String pushOverUser;

    @Value("${pushover.token}")
    private String pushOverToken;

    @Autowired
    private RestClient restClient;


    public void postMessage(final PushOverDto pushOverDto) {
        pushOverDto.setToken(pushOverToken.trim());
        pushOverDto.setUser(pushOverUser.trim());

        try {
            restClient.post(pushOverUrl, Collections.singletonMap("Content-Type", "application/json"), pushOverDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
