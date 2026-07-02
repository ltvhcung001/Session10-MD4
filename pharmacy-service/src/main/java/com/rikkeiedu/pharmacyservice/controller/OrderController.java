package com.rikkeiedu.pharmacyservice.controller;

import com.rikkeiedu.pharmacyservice.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/checkout")
    public String checkout(@RequestBody OrderEvent orderEvent) {
        orderEvent.setTimestamp(LocalDateTime.now());
        // Gửi sự kiện vào topic medicine-stock-events với key là medicineId
        kafkaTemplate.send("medicine-stock-events", orderEvent.getMedicineId(), orderEvent);
        return "Thông tin thanh toán thành công.";
    }
}
