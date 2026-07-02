package com.rikkeiedu.pharmacyservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InsuranceService {

    @TimeLimiter(name = "insuranceTL", fallbackMethod = "fallbackVerifyInsurance")
    @CircuitBreaker(name = "insuranceCB", fallbackMethod = "fallbackVerifyInsurance")
    @Retry(name = "insuranceRetry", fallbackMethod = "fallbackVerifyInsurance")
    public CompletableFuture<String> verifyInsurance(String medicineName, double price) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate slow insurance server
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Bảo hiểm hợp lệ. Giá sau chiết khấu: " + (price * 0.8);
        });
    }

    public CompletableFuture<String> fallbackVerifyInsurance(String medicineName, double price, Throwable t) {
        return CompletableFuture.completedFuture(
                "Giá thuốc chưa chiết khấu: " + price + ". Ghi chú: Xác thực bảo hiểm sau."
        );
    }
}
