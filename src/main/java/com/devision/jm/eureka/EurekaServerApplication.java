package com.devision.jm.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server Application - Service Discovery
 *
 * This server acts as a service registry where all microservices register themselves.
 * Other services can discover and communicate with registered services through this registry.
 *
 * Architecture Role: Ultimo Level (D.3.1)
 * - Enables dynamic service discovery
 * - Supports load balancing
 * - Provides health monitoring
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
