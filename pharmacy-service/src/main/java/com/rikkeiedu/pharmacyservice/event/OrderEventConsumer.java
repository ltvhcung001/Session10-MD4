package com.rikkeiedu.pharmacyservice.event;

import com.rikkeiedu.pharmacyservice.entity.Medicine;
import com.rikkeiedu.pharmacyservice.entity.Order;
import com.rikkeiedu.pharmacyservice.repository.MedicineRepository;
import com.rikkeiedu.pharmacyservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderEventConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @KafkaListener(topics = "medicine-stock-events", groupId = "inventory-group")
    @Transactional
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("Nhận được sự kiện đơn hàng: " + event.getOrderId());

        Optional<Medicine> optionalMedicine = medicineRepository.findById(event.getMedicineId());
        
        if (optionalMedicine.isPresent()) {
            Medicine medicine = optionalMedicine.get();
            
            // Create and save Order
            Order order = new Order();
            order.setMedicineId(event.getMedicineId());
            order.setQuantity(event.getQuantity());
            order.setTimestamp(event.getTimestamp());
            order.setPriceSell(medicine.getPrice());
            order.setTotalAmount(medicine.getPrice() != null ? medicine.getPrice() * event.getQuantity() : 0);
            
            orderRepository.save(order);
            
            // Update Medicine stock
            medicine.setQuantity(medicine.getQuantity() - event.getQuantity());
            medicineRepository.save(medicine);
            
            System.out.println("Thêm mới đơn hàng thành công !");
        } else {
            System.out.println("Không tìm thấy thuốc với ID: " + event.getMedicineId());
        }
    }
}
