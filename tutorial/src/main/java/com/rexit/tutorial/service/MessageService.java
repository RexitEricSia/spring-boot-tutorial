package com.rexit.tutorial.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    // this is thailand project
    public String sendMessage(String userName) {
        return "Hello, " + userName + ". Welcome to Spring Boot.";
    }
}
