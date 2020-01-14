package com.sodabottle.stext.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram")
@Getter
@Setter
public class TelegramConfig {
    private String chatId;
    private String botId;
    private String url;
}
