package com.rikkeiedu.pharmacyservice.service;

import com.rikkeiedu.pharmacyservice.entity.Medicine;
import com.rikkeiedu.pharmacyservice.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Chạy mỗi 30 giây để kiểm tra
    @Scheduled(fixedRate = 30000)
    public void checkLowStockMedicines() {
        // Giả sử số lượng < 10 là sắp hết hàng
        List<Medicine> allMedicines = medicineRepository.findAll();
        List<String> lowStockIds = allMedicines.stream()
                .filter(m -> m.getQuantity() != null && m.getQuantity() < 10)
                .map(Medicine::getId)
                .collect(Collectors.toList());

        if (!lowStockIds.isEmpty()) {
            System.out.println("BatchService: Phát hiện " + lowStockIds.size() + " loại thuốc sắp hết. Đang gửi cảnh báo...");
            kafkaTemplate.send("pharmacy-notifications", "LOW_STOCK", lowStockIds);
        }
    }
}
