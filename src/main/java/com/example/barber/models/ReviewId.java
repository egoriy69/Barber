package com.example.barber.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewId implements Serializable {
    private Long employeeInfo;
    private Long clientInfo;
}