package com.rexit.tutorial.model;

import com.rexit.tutorial.enums.UserRole;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false, unique = true)
    private String username;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NonNull
    private UserRole role;

    @NonNull
    @Min(value = 1, message = "Invalid age.")
    private int age;

    @NonNull
    @Email(message = "Invalid email format")
    private String email;

    @Column
    private String refreshToken;

    @Column
    private String ipAddress;

    @Column
    private String userAgent;
}
