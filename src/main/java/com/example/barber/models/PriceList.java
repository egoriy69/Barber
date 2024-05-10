package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "price_list")
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private int cost;
}
