package com.example.barber.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Table(name = "refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Instant expiryDate;


    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName = "id")
    private Employee employee;
}
