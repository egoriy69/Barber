package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
//@Builder
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private ClientInfo clientInfo;
}
