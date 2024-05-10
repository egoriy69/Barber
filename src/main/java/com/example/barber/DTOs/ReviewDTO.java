package com.example.barber.DTOs;

import com.example.barber.models.ClientInfo;
import com.example.barber.models.EmployeeInfo;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ReviewDTO {

    private Long employeeId;

    //    private ClientInfo clientId;
    private String FIO;

    private String reviewName;

    private int stars;

    private String phoneClient;
}
