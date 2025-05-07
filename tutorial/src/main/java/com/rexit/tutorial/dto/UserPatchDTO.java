package com.rexit.tutorial.dto;

import com.rexit.tutorial.enums.UserRole;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPatchDTO {
    private String username;
    private String password;
    private UserRole role;

    @NonNull
    @Min(value = 1, message = "Invalid age.")
    private Integer age;

    
    @NonNull
    @Email(message = "Invalid email format")
    private String email;
    
    private String refreshToken;
    private String ipAddress;
    private String userAgent;
}