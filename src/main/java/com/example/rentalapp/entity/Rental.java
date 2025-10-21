package com.example.rentalapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer rooms;

    @Column(name = "monthly_price", nullable = false)
    private BigDecimal monthlyPrice;

    public Rental() {}

    // Конструкторы по желанию
    public Rental(String address, Integer rooms, BigDecimal monthlyPrice) {
        this.address = address;
        this.rooms = rooms;
        this.monthlyPrice = monthlyPrice;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getRooms() { return rooms; }
    public void setRooms(Integer rooms) { this.rooms = rooms; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; }
}
