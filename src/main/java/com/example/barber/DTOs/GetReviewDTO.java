package com.example.barber.DTOs;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class GetReviewDTO {

//    HashMap<Integer, String> reviewMap;

//    List<Review> dsd;

    private String FIOClient;

    private int stars;

    private String reviewName;
}
