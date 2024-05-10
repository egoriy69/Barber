package com.example.barber.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ReviewId.class)
public class Review {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeInfo employeeInfo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;

    @Column(name = "reviewName")
    private String reviewName;

    private int stars;
}
