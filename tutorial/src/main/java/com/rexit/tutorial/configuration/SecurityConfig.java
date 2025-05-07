package com.rexit.tutorial.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rexit.tutorial.filter.RoleFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RoleFilter roleFilter;

    public SecurityConfig(RoleFilter roleFilter) {
        this.roleFilter = roleFilter;
    }

    @Value("${allowed-origin}")
    private String allowedOrigin;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedOrigin));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Ensure no session is created
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(roleFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/campaign/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/campaign/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/campaign/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/campaign/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/campaign/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/campaign/**").hasRole("ADMIN")
                        .requestMatchers("/generate-pdf/**").authenticated()
                        .requestMatchers("/message").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                16, // salt length
                32, // hash length
                1, // parallelism
                65536, // memory (in KB)
                3 // iterations
        );
    }
}
