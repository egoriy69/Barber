package com.example.barber.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CalendarOnMonthDTO {

    private LocalDate localDate;

    private boolean current;

    private boolean available;
}
