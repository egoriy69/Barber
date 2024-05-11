package com.example.barber.DTOs;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class GetReviewDTO {

    private String FIOClient;

    private int stars;

    private String reviewName;
}
