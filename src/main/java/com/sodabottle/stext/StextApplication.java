package com.sodabottle.stext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StextApplication {

	public static void main(String[] args) {
		SpringApplication.run(StextApplication.class, args);
	}

}
