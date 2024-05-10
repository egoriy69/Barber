package com.example.barber.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeReviewsDTO {
    private EmployeeDTO employeeDTO;
    private List<GetReviewDTO> getReviewDTO;


}
