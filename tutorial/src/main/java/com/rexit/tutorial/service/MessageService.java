package com.rexit.tutorial.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public String sendMessage(String userName) {
        return "Hi, " + userName + ". Welcome to Spring Boot.";
    }
}
