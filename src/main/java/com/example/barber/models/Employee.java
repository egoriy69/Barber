package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmployeeInfo employeeInfo;
}
