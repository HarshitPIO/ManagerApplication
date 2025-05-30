package com.pio.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    Logger logger = LoggerFactory.getLogger(Producer.class);
    private final JmsTemplate jmsTemplate;

    public Producer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String task) {
        try {
            logger.info("Sending task: {}", task);
            jmsTemplate.convertAndSend("task-queue", task);
            logger.info("Task sent: {}", task);
        } catch (Exception e) {
            logger.error("Error in Sending Task: {}", e.getMessage());
        }
    }
}
