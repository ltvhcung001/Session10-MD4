package com.rikkeiedu.pharmacyservice.event;

import com.rikkeiedu.pharmacyservice.entity.Medicine;
import com.rikkeiedu.pharmacyservice.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotificationConsumer {

    @Autowired
    private MedicineRepository medicineRepository;

    @KafkaListener(topics = "pharmacy-notifications", groupId = "pharmacy-group")
    @Transactional
    public void consumeNotification(List<String> lowStockIds) {
        System.out.println("Nhận được thông báo có " + lowStockIds.size() + " loại thuốc sắp hết hạn/hết hàng.");
        
        for (String id : lowStockIds) {
            medicineRepository.findById(id).ifPresent(medicine -> {
                medicine.setStatus("Cần nhập hàng");
                medicineRepository.save(medicine);
                System.out.println("Đã cập nhật trạng thái 'Cần nhập hàng' cho thuốc: " + medicine.getMedicineName());
            });
        }
        
        System.out.println("Gửi email cho quản lý: Hiện tại có " + lowStockIds.size() + " loại thuốc đang sắp hết hạn/hết hàng. Vui lòng kiểm tra và nhập thêm.");
    }
}
