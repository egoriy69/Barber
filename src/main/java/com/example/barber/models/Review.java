package com.example.barber.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ReviewId.class)
@EqualsAndHashCode
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
