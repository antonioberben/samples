package com.example.messagingrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SenderApplication {

	static final String topicExchangeName = "spring-boot-exchange";

	static final String queueName = "spring-boot";

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SenderApplication.class, args).close();
	}

}