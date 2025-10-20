package ar.edu.utn.frc.tup.tesis.pinceletas_notification_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador para endpoints de health check y monitoreo.
 * Proporciona endpoints simples para verificar el estado del servicio.
 */
@RestController
public class HealthController {

    /**
     * Endpoint básico de health check.
     *
     * @return ResponseEntity con estado del servicio.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "notification-service"));
    }

    /**
     * Endpoint compatible con Spring Boot Actuator para health check.
     *
     * @return ResponseEntity con estado del servicio.
     */
    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, String>> actuatorHealth() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}