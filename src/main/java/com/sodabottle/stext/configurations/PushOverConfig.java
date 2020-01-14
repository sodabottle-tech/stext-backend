package com.sodabottle.stext.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pushover")
@Getter
@Setter
public class PushOverConfig {
    private String user;
    private String token;
    private String url;
}
