package com.example.barber.controllers;

import com.example.barber.DTOs.EmployeeDTO;
import com.example.barber.DTOs.GetReviewDTO;
import com.example.barber.DTOs.MeetingDTO;
import com.example.barber.DTOs.ReviewDTO;
import com.example.barber.models.PriceList;
import com.example.barber.models.Review;
import com.example.barber.services.MeetingService;
import com.example.barber.services.PriceListService;
import com.example.barber.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
    public HashMap<EmployeeDTO, List<GetReviewDTO>> getReview(){
        return reviewService.getReview();
    }
}
