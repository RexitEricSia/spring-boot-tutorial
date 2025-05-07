package com.rexit.tutorial.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.dto.LoginRequestDTO;
import com.rexit.tutorial.dto.LoginResponseDTO;
import com.rexit.tutorial.dto.LogoutRequestDTO;
import com.rexit.tutorial.dto.RefreshRequestDTO;
import com.rexit.tutorial.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        return ResponseEntity.ok(authenticationService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody RefreshRequestDTO req) {
        return ResponseEntity.ok(authenticationService.refresh(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody LogoutRequestDTO req) {
        authenticationService.logout(req);

        Map<String, String> res = new HashMap<>();
        res.put("message", "Logout successful");

        return ResponseEntity.ok(res);
    }
}
