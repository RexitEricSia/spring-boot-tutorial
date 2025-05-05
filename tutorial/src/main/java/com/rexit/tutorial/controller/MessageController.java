package com.rexit.tutorial.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.service.MessageService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String getMessage(@RequestParam(name = "name", required = false, defaultValue = "Guest") String userName) {
        return messageService.sendMessage(userName);
    }
}
