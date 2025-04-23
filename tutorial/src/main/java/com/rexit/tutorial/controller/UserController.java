package com.rexit.tutorial.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.model.User;
import com.rexit.tutorial.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    

    @PostMapping("/rollback-demo")
    public void rollbackTesting(@RequestBody User newUser) {
        try {
            userService.rollbackTesting(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
