package com.rexit.tutorial.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    // FOR THE SAKE OF testing cicd actions
    public String sendMessage(String userName) {
        return "Hello, " + userName + ". Welcome to Spring Boot.";
    }
}
