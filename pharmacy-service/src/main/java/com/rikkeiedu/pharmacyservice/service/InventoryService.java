package com.rikkeiedu.pharmacyservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @CircuitBreaker(name = "warehouseCB", fallbackMethod = "checkWarehouseFallback")
    public String checkInventory() {
        // Simulate a call to warehouse-service that fails
        throw new RuntimeException("Warehouse service is down");
    }

    public String checkWarehouseFallback(Exception e) {
        return "Không thể kết nối kho tổng. Hệ thống sẽ sử dụng dữ liệu tồn kho cục bộ để tiếp tục giao dịch";
    }
}
