package com.rikkeiedu.pharmacyservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "medicinse")
public class Medicine {
    @Id
    private String id;

    private String description;

    @Column(name = "medicine_name")
    private String medicineName;

    private Double price;

    private Integer quantity;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
