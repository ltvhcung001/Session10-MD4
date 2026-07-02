package com.rikkeiedu.pharmacyservice.controller;

import com.rikkeiedu.pharmacyservice.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/insurance")
public class InsuranceController {

    @Autowired
    private InsuranceService insuranceService;

    @GetMapping("/verify")
    public CompletableFuture<String> verifyInsurance(@RequestParam String medicineName, @RequestParam double price) {
        return insuranceService.verifyInsurance(medicineName, price);
    }
}
