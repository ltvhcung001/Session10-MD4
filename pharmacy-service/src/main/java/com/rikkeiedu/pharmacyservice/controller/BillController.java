package com.rikkeiedu.pharmacyservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bill")
@RefreshScope
public class BillController {

    @Value("${pharmacy.vat-rate:0}")
    private double vatRate;

    @PostMapping
    public Map<String, Object> calculateBill(@RequestParam("totalAmount") double totalAmount) {
        double tax = totalAmount * (vatRate / 100.0);
        double finalAmount = totalAmount + tax;

        Map<String, Object> response = new HashMap<>();
        response.put("originalAmount", totalAmount);
        response.put("vatRate", vatRate);
        response.put("taxAmount", tax);
        response.put("finalAmount", finalAmount);

        return response;
    }
}
