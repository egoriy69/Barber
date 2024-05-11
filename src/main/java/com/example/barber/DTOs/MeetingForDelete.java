package com.example.barber.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class MeetingForDelete {
    private ZonedDateTime startTime;
    private Long employeeId;
}
