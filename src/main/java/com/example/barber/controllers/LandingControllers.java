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
//
    private final MeetingService meetingService;

    private final PriceListService priceListService;

    @PostMapping("/review/create")
    public void createReview(@RequestBody ReviewDTO reviewDTO){
        reviewService.createReview(reviewDTO);
    }

    @PostMapping("/meeting/create")
    public void createMeeting(@RequestBody MeetingDTO meetingDTO){
        meetingService.createMeeting(meetingDTO);
    }

    @GetMapping("/priceList")
    public List<PriceList> getPriceList(){
        return priceListService.getPriceList();
    }

    @GetMapping("/review")
    public List<EmployeeReviewsDTO> getReview(){
        return reviewService.getReview();
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<LocalDateTime>> getAvailableSlots(
            @RequestParam Long employeeId,
            @RequestParam LocalDate date,
            @RequestParam Long serviceId) {
        List<LocalDateTime> slots = meetingService.getAvailableSlots(employeeId, date, serviceId);
        return ResponseEntity.ok(slots);
    }
    @PostMapping
    public ResponseEntity<Meeting> createMeeting2(
            @RequestBody MeetingDTO meetingDTO) {
        Meeting meeting = meetingService.createMeeting2(meetingDTO);
        return ResponseEntity.ok(meeting);
    }
//    @PostMapping
//    public ResponseEntity<Meeting> createMeeting(
//            @RequestParam Long employeeId,
//            @RequestParam Long clientId,
//            @RequestParam Long serviceId,
//            @RequestParam LocalDateTime startTime) {
//        Meeting meeting = meetingService.createMeeting2(employeeId, clientId, serviceId, startTime);
//        return ResponseEntity.ok(meeting);
//    }
//
////    @PostMapping("/meeting/create2")
//    public void createMeeting2(@RequestBody MeetingDTO meetingDTO){
//        meetingService.createMeeting2(meetingDTO);
//    }

//    @GetMapping("/available-slots")
//    public ResponseEntity<List<LocalDateTime>> getAvailableSlots(
//            @RequestParam int serviceId,
//            @RequestParam int employeeInfoId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<LocalDateTime> availableSlots = meetingService.meetingGet(serviceId,employeeInfoId, date);
//
//        return ResponseEntity.ok(availableSlots);
//    }


}
