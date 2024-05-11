package com.example.barber.controllers;

import com.example.barber.DTOs.*;
import com.example.barber.models.Meeting;
import com.example.barber.models.PriceList;
import com.example.barber.models.Review;
import com.example.barber.services.MeetingService;
import com.example.barber.services.PriceListService;
import com.example.barber.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@AllArgsConstructor
@RequestMapping("/landing")
public class LandingControllers {
    private final ReviewService reviewService;
    private final MeetingService meetingService;

    private final PriceListService priceListService;

    @PostMapping("/review/create")
    public void createReview(@RequestBody ReviewDTO reviewDTO){
        reviewService.createReview(reviewDTO);
    }


    @GetMapping("/priceList")
    public List<PriceList> getPriceList(){
        return priceListService.getPriceList();
    }

    @GetMapping("/review")
    public List<EmployeeReviewsDTO> getReview(){
        return reviewService.getReview();
    }

    @GetMapping("/availableSlotsOnDay")
    public ResponseEntity<List<LocalDateTime>> getAvailableSlots(
            @RequestParam Long employeeId,
            @RequestParam LocalDate date,
            @RequestParam Long serviceId) {
        List<LocalDateTime> slots = meetingService.getAvailableSlotsOnDay(employeeId, date, serviceId);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/meeting/create")
    public ResponseEntity<Meeting> createMeeting2(
            @RequestBody MeetingDTO meetingDTO) {
        Meeting meeting = meetingService.createMeeting2(meetingDTO);
        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/{year}/{month}")
    public List<CalendarOnMonthDTO> getCalendar(@PathVariable int month, @PathVariable int year,
                                                @RequestParam Long serviceId, @RequestParam Long employeeId){
        return meetingService.getCalendar(month, year, serviceId, employeeId);
    }
}
