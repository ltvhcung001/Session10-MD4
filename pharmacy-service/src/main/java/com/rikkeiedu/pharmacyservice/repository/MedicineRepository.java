package com.rikkeiedu.pharmacyservice.repository;

import com.rikkeiedu.pharmacyservice.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {
}
