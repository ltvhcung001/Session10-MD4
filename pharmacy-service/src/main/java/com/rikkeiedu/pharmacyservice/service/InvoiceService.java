package com.rikkeiedu.pharmacyservice.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    @Retry(name = "invoiceRetry", fallbackMethod = "fallbackExportInvoice")
    @RateLimiter(name = "invoiceRateLimiter", fallbackMethod = "fallbackExportInvoice")
    public String exportInvoice() {
        // Simulate a network error occasionally, or always for testing retry
        System.out.println("Attempting to export invoice...");
        throw new RuntimeException("Network error during invoice export");
    }

    public String fallbackExportInvoice(Exception e) {
        return "Hệ thống đang quá tải hoặc gặp lỗi mạng. Vui lòng thử lại sau. Lý do: " + e.getMessage();
    }
}
