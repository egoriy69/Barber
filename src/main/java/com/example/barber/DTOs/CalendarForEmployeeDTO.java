package com.example.barber.DTOs;

import com.example.barber.models.Meeting;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CalendarForEmployeeDTO {
    private LocalDate createdAt;
    List<MeetingDTO> meetings;
    private int count;
    private boolean current;
}
