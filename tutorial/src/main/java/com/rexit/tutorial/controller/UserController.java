package com.rexit.tutorial.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.model.User;
import com.rexit.tutorial.service.UserService;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // This endpoint is just for demo purpose, so the encrypted password can be store to the DB    
    @GetMapping("/get-pw")
    public void getPw() {
        List<String> usernames = Arrays.asList(
            "johndoe", "janesmith", "emilyjones", "michaelbrown", 
            "chrisdavis", "lisawilson", "danielmiller", "sarahmoore", 
            "davidtaylor", "lauraanderson"
        );

        for (String username : usernames) {
            System.out.println(passwordEncoder.encode(username));
        }
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
