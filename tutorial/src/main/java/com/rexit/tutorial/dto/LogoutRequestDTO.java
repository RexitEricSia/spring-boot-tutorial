package com.rexit.tutorial.dto;

import lombok.Data;

@Data
public class LogoutRequestDTO {
    private String refreshToken;
}