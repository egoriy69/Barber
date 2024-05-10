package com.example.barber.DTOs;

import lombok.Data;

@Data
public class JwtRequest {
    private String phone;
    private String password;
}
