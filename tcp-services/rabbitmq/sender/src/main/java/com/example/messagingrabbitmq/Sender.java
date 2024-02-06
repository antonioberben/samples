package com.example.messagingrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    Binding binding;

    @Scheduled(fixedDelay = 5000)
    public void send() {
        LOGGER.info("Sending message to the queue.");
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), "Hello from: " + hostname);
        LOGGER.info("Message sent successfully to the queue!!!");
    }

}