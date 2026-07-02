package com.rikkeiedu.pharmacyservice.repository;

import com.rikkeiedu.pharmacyservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
