package com.rikkeiedu.pharmacyservice.controller;

import com.rikkeiedu.pharmacyservice.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/sell")
    public String sellMedicine(@RequestParam String medicineId, @RequestParam int quantity) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId("ORD-" + System.currentTimeMillis());
        orderEvent.setMedicineId(medicineId);
        orderEvent.setQuantity(quantity);
        orderEvent.setTimestamp(LocalDateTime.now());
        
        kafkaTemplate.send("medicine-stock-events", medicineId, orderEvent);
        return "Thanh toán thành công! Sự kiện đã được gửi tới Kafka.";
    }
}
