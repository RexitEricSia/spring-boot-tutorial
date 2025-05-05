package com.rexit.tutorial.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    
    private String accessToken;
    private String refreshToken;
}
