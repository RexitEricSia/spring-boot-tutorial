package com.rexit.tutorial.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rexit.tutorial.dto.LoginRequestDTO;
import com.rexit.tutorial.dto.LoginResponseDTO;
import com.rexit.tutorial.dto.RefreshRequestDTO;
import com.rexit.tutorial.dto.UserPatchDTO;
import com.rexit.tutorial.enums.Error;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.User;
import com.rexit.tutorial.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (attributes != null) ? attributes.getRequest() : null;
    }

    private LoginResponseDTO generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        LoginResponseDTO response = jwtUtil.generateToken(user.getId(), claims);

        HttpServletRequest request = getCurrentHttpRequest();
        String ipAddress = (request != null) ? request.getRemoteAddr() : "UNKNOWN";
        String userAgent = (request != null) ? request.getHeader("User-Agent") : "UNKNOWN";

        UserPatchDTO userPatchDTO = UserPatchDTO.builder()
                .refreshToken(passwordEncoder.encode(response.getRefreshToken()))
                .ipAddress(passwordEncoder.encode(ipAddress))
                .userAgent(passwordEncoder.encode(userAgent))
                .build();

        userService.updateUser(user.getId(), userPatchDTO);

        return response;
    }

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        User user;
        try {
            user = userService.getUserByUsernameWithLock(loginRequestDTO.getUsername());
        } catch (BusinessException e) {
            throw new BusinessException(Error.AUTHENTICATION_LOGIN_FAILED);
        }

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(Error.AUTHENTICATION_LOGIN_FAILED);
        }

        return generateToken(user);
    }

    public LoginResponseDTO refresh(RefreshRequestDTO refreshRequestDTO) {

        String refreshToken = refreshRequestDTO.getRefreshToken();
        Long userId = 0L;
        User user;

        try {
            userId = Long.parseLong(jwtUtil.extractUserID(refreshToken));
        } catch (ExpiredJwtException e) {
            throw new BusinessException(Error.AUTHENTICATION_TOKEN_EXPIRED);
        } catch (SignatureException e) {
            throw new BusinessException(Error.AUTHENTICATION_INVALID_SIGNATURE);
        } catch (JwtException e) {
            throw new BusinessException(Error.AUTHENTICATION_INVALID_TOKEN);
        }

        try {
            user = userService.getUserByIDWithLock(userId);
        } catch (BusinessException e) {
            throw new BusinessException(Error.AUTHENTICATION_REFRESH_FAILED);
        }

        HttpServletRequest request = getCurrentHttpRequest();
        String ipAddress = (request != null) ? request.getRemoteAddr() : "UNKNOWN";
        String userAgent = (request != null) ? request.getHeader("User-Agent") : "UNKNOWN";

        if (!passwordEncoder.matches(refreshToken, user.getRefreshToken()) ||
                !passwordEncoder.matches(ipAddress, user.getIpAddress()) ||
                !passwordEncoder.matches(userAgent, user.getUserAgent())) {
            throw new BusinessException(Error.AUTHENTICATION_REFRESH_FAILED);
        }

        LoginResponseDTO response = generateToken(user);

        return response;
    }
}
