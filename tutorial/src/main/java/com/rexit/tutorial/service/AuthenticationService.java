package com.rexit.tutorial.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rexit.tutorial.dto.LoginRequestDTO;
import com.rexit.tutorial.dto.LoginResponseDTO;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.User;
import com.rexit.tutorial.util.JwtUtil;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userService.getUserByUsernameWithLock(loginRequestDTO.getUsername());
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(null);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return jwtUtil.generateToken(user.getUsername(), claims);
    }

    // public LoginResponseDTO refreshToken(LoginRequestDTO loginRequestDTO) {
    //     User user = userService.getUserByUsernameWithLock(loginRequestDTO.getUsername());
    //     if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
    //         throw new BusinessException(null);
    //     }

    //     //generate tokens

    //     return new LoginResponseDTO();
    // }
}
