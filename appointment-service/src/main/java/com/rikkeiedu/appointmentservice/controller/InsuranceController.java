package com.rikkeiedu.appointmentservice.controller;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/insurance")
public class InsuranceController {

    @GetMapping("/{id}")
    @TimeLimiter(name = "insuranceTimeout", fallbackMethod = "insuranceFallback")
    public CompletableFuture<ResponseEntity<?>> checkInsurance(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate slow insurance service response
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Map<String, Object> response = new HashMap<>();
            response.put("data", "Bảo hiểm hợp lệ");
            response.put("message", "Success");
            response.put("status", 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    public CompletableFuture<ResponseEntity<?>> insuranceFallback(Long id, Throwable ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", null);
        response.put("message", "Dịch vụ bảo hiểm đang bận , vui lòng thanh toán trực tiếp.");
        response.put("status", 200);
        return CompletableFuture.completedFuture(new ResponseEntity<>(response, HttpStatus.OK));
    }
}
