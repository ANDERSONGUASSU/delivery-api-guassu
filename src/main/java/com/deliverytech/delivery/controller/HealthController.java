package com.deliverytech.delivery.controller;

import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "version", "1.0.0",
            "timestamp", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "Java Version", System.getProperty("java.version"),
            "Spring Boot Version", System.getProperty("spring.boot.version", "3.5.4")
        );
    }

    @GetMapping("/info")
    public AppInfo info() {
        return new AppInfo(
            "Delivery Tech API", 
            "1.0.0",
            "Anderson Guassu",
            "JDK 21",
            "Spring Boot 3.5.4"
        );
    }
    // Record para demostrar recurso do Java 14+ (disponivel no JDK 21)
    public record AppInfo(
        String application,
        String version,
        String developer,
        String javaVersion,
        String framework
    ) {}
}
