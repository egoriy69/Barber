package com.example.barber.DTOs;

import lombok.Data;

@Data
public class MetricDTO {
    private double avgBill;
    private int numberOfOrders;
    private long sum;
    private int numberOfUniqueClients;
}
