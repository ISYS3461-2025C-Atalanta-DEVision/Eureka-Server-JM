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
 *
 * FLOW:
 * 1. A request comes in (e.g., microservice registering, or accessing dashboard)
 * 2. Spring Security intercepts the request and runs through this filter chain
 * 3. First, CSRF check is applied (disabled for /eureka/** endpoints)
 * 4. Then, authorization rules are checked in order:
 *    - If request matches /actuator/health/** or /actuator/info -> allow without login
 *    - For all other requests -> require authentication
 * 5. If authentication is required, HTTP Basic Auth prompts for username/password
 * 6. Credentials are validated against those defined in application.yml
 * 7. If valid -> request proceeds; If invalid -> 401 Unauthorized returned
 */
@Configuration  // Marks this class as a Spring configuration (provides beans)
@EnableWebSecurity  // Enables Spring Security's web security support
public class SecurityConfig {

    @Bean  // Registers this method's return value as a Spring-managed bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // STEP 1: CSRF (Cross-Site Request Forgery) Configuration
            // CSRF protection prevents malicious websites from making requests on behalf of logged-in users
            // We disable it for /eureka/** because microservices (not browsers) call these endpoints
            // Microservices don't have CSRF tokens, so they would fail registration without this
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/eureka/**"))

            // STEP 2: Authorization Rules (checked in order, first match wins)
            .authorizeHttpRequests(auth -> auth
                // Rule 1: Health endpoints are PUBLIC (no login required)
                // Used by load balancers, Kubernetes, monitoring tools to check if server is alive
                .requestMatchers("/actuator/health/**").permitAll()
                // Rule 2: Info endpoint is PUBLIC
                // Returns app metadata (version, name, etc.)
                .requestMatchers("/actuator/info").permitAll()
                // Rule 3: Everything else (dashboard, service registration, discovery APIs)
                // requires the user to be authenticated (logged in with valid credentials)
                // This includes: /, /eureka/apps, /eureka/apps/{appId}, etc.
                .anyRequest().authenticated())

            // STEP 3: Authentication Method
            // HTTP Basic Auth: credentials sent in "Authorization: Basic base64(username:password)" header
            // Customizer.withDefaults() uses default settings (realm name, entry point, etc.)
            // The actual username/password are defined in application.yml under spring.security.user
            .httpBasic(Customizer.withDefaults());

        // Build and return the configured security filter chain
        return http.build();
    }
}
