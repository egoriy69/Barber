package com.example.barber.DTOs;

import com.example.barber.models.ClientInfo;
import com.example.barber.models.EmployeeInfo;
import com.example.barber.models.PriceList;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MeetingDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String comment;

    private String FIO;

    private int priceListId;

    private int employeeId;

    private String phoneClient;
}
