package com.sodabottle.stext.service;

import com.sodabottle.stext.clients.RestClient;
import com.sodabottle.stext.configurations.PushOverConfig;
import com.sodabottle.stext.models.dtos.GenericDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Async
@Slf4j
@Service
public class AsyncPushOverService implements ExternalService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private PushOverConfig pushOverConfig;

    @Override
    public <T extends GenericDto> void postMessage(T genericDto) {
        genericDto.setToken(pushOverConfig.getToken().trim());
        genericDto.setUser(pushOverConfig.getUser().trim());

        try {
            restClient.post(pushOverConfig.getUrl(), Collections.singletonMap("Content-Type", "application/json"), genericDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
