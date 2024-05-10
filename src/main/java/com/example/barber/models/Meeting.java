package com.example.barber.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "meeting")
@Builder
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_info_id")
    private EmployeeInfo employeeInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;
}
