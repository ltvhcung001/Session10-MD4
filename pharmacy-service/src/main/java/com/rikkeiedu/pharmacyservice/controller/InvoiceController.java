package com.rikkeiedu.pharmacyservice.controller;

import com.rikkeiedu.pharmacyservice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/export")
    public String exportInvoice() {
        return invoiceService.exportInvoice();
    }
}
