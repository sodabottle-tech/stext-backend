package com.sodabottle.stext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.sodabottle.stext.configurations.FileStorageConfig;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties({
	FileStorageConfig.class
})
public class StextApplication {

    public static void main(String[] args) {
        SpringApplication.run(StextApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
