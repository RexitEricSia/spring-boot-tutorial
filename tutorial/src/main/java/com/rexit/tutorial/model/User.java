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
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "\"user\"")
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
    private int age;

    @NonNull
    private String email;

}
