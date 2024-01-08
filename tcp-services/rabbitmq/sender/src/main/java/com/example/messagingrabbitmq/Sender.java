package com.example.messagingrabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    private final String queueName;

    @Autowired
    public Sender(RabbitTemplate rabbitTemplate, @Value("${QUEUE_NAME}") String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    @Scheduled(fixedDelay = 2000)
    public void send() {
        rabbitTemplate.convertAndSend(queueName, "Hello, RabbitMQ!");
    }

}