package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller para health check da API
 */
@RestController
@RequestMapping("/health")
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "E-commerce API is running");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "E-commerce API");
        response.put("version", "1.0.0");
        response.put("description", "API REST para sistema de e-commerce");
        response.put("endpoints", Map.of(
            "products", "/api/products",
            "users", "/api/users", 
            "orders", "/api/orders",
            "payments", "/api/payments",
            "reports", "/api/reports",
            "addresses", "/api/addresses"
        ));
        return ResponseEntity.ok(response);
    }
}
