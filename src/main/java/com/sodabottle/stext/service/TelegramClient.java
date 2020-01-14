package com.sodabottle.stext.service;

import com.sodabottle.stext.clients.RestClient;
import com.sodabottle.stext.configurations.TelegramConfig;
import com.sodabottle.stext.models.dtos.GenericDto;
import com.sodabottle.stext.models.dtos.TelegramDto;
import com.sodabottle.stext.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@Async
public class TelegramClient implements ExternalService {

    @Autowired
    private TelegramConfig telegramConfig;

    @Autowired
    private RestClient restClient;

    @Override
    public <T extends GenericDto> void postMessage(T genericDto) {
        TelegramDto telegramDto = (TelegramDto) genericDto;
        telegramDto.setChat_id(telegramConfig.getChatId());

        String telegramPostUrl = prepareTelegramPostUrl(telegramConfig);

        if (telegramDto.getText().length() > 4096) {
            telegramDto.setText(telegramDto.getText().substring(0, 4095));
        }

        LogUtils.logMessage("telegram url : " + telegramPostUrl, log, LogUtils.LogState.INFO);
        try {
            restClient.post(telegramPostUrl, Collections.singletonMap("Content-Type", "application/json"), telegramDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String prepareTelegramPostUrl(TelegramConfig telegramConfig) {
        return telegramConfig.getUrl() + "/" + telegramConfig.getBotId() + "/sendMessage";
    }
}
