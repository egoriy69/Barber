package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee_info")
public class EmployeeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String FIO;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "review_id")
//    private Review review;
}
