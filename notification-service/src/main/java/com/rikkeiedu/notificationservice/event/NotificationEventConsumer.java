package com.rikkeiedu.notificationservice.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationEventConsumer {

    @KafkaListener(topics = "medicine-stock-events", groupId = "notification-group")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("Đã gửi đơn hàng: " + event.getOrderId() + " với Key: " + event.getMedicineId());
        // Simulating email send
        System.out.println("Hóa đơn cho đơn hàng " + event.getOrderId() + " đã được gửi tới khách hàng");
        System.out.println("Đã gửi thông báo tới email khách hàng");
    }
}
