package com.devision.jm.eureka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for Eureka Server
 *
 * Secures the Eureka dashboard and service registration endpoints
 * with HTTP Basic authentication to prevent unauthorized service registration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for Eureka clients to register
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/eureka/**"))

            // Configure authorization
            .authorizeHttpRequests(auth -> auth
                // Actuator health endpoints are public
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/actuator/info").permitAll()
                // All other endpoints require authentication
                .anyRequest().authenticated())

            // Use HTTP Basic auth for service registration
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
