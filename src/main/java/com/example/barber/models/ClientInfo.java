package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "client_info")
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String FIO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
