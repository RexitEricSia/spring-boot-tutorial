package com.rexit.tutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.dto.LoginRequestDTO;
import com.rexit.tutorial.dto.LoginResponseDTO;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody RefreshRequestDTO refreshRequestDTO) {
        return ResponseEntity.ok(authenticationService.refresh(refreshRequestDTO));
    }
}
