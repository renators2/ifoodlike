package com.seuprojeto.ifoodlike.controller.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Endpoints de verificação de saúde da aplicação")
public class HealthCheckController {

    @GetMapping
    @Operation(summary = "Health check", description = "Verifica se a aplicação está funcionando")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "application", "iFood-Like API"
        ));
    }
}
