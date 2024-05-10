package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;

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

    @Column(name = "duration", length = 10)
    private String duration;

    public Duration getDuration() {
        return Duration.ofMinutes(Long.parseLong(duration));
    }

    public void setDuration(Duration duration) {
        this.duration = String.valueOf(duration.toMinutes());
    }
}
