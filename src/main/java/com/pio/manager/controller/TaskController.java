package com.pio.manager.controller;

import com.pio.manager.service.Consumer;
import com.pio.manager.service.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private final Producer producer;
    private final Consumer consumer;

    public TaskController(Producer producer, Consumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    Logger logger = LoggerFactory.getLogger(TaskController.class);
    int count = 0;

    @PostMapping("/manager/send")
    public ResponseEntity<String> send(@RequestBody String request) {
        try {
            if (request == null || request.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Task cannot be null or empty");
            }
            count++;
            String message = "Task" + count + ": " + request;
            producer.sendMessage(message);
            return ResponseEntity.ok("sent: " + request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending: " + e.getMessage());
        }
    }

    @GetMapping("employee/showList")
    public ResponseEntity<List<String>> showList() {
        try {
            return ResponseEntity.ok(consumer.showTask());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of("Error in showing list of task"));
        }
    }
}
