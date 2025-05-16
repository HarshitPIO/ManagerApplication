package com.pio.manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    List<String> taskList = new ArrayList<>();

    @JmsListener(destination = "task-queue")
    public void receive(String task) {
        try {
            logger.info("{}", task);
            taskList.add(task);
        } catch (Exception e) {
            logger.error("Error in receiving task: {}", e.getMessage());
        }
    }

    public List<String> showTask() {
        try {
            logger.info("Returning list of task to the employee");
            return taskList;
        } catch (Exception e) {
            logger.error("Error in returning list of task: {}", e.getMessage());
            return List.of("Error in showing list");
        }
    }
}
