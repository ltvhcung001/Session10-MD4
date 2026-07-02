package com.rikkeiedu.pharmacyservice.controller;

import com.rikkeiedu.pharmacyservice.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
@RefreshScope
public class OrderController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @PostMapping("/checkout")
    public String checkout(@RequestBody OrderEvent orderEvent) {
        orderEvent.setTimestamp(LocalDateTime.now());
        // Gửi sự kiện vào topic được cấu hình với key là medicineId
        kafkaTemplate.send(defaultTopic, orderEvent.getMedicineId(), orderEvent);
        return "Thông tin thanh toán thành công.";
    }
}
