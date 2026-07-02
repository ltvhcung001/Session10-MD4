package com.rikkeiedu.pharmacyservice.event;

import java.time.LocalDateTime;

public class OrderEvent {
    private String orderId;
    private String medicineId;
    private int quantity;
    private LocalDateTime timestamp;

    public OrderEvent() {}

    public OrderEvent(String orderId, String medicineId, int quantity, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
