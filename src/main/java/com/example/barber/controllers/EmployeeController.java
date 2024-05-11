package com.example.barber.controllers;

import com.example.barber.DTOs.*;
import com.example.barber.Exception.PasswordConfirmationException;
import com.example.barber.models.Employee;
import com.example.barber.models.RefreshToken;
import com.example.barber.security.JwtUtil;
import com.example.barber.services.EmployeeService;
import com.example.barber.services.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    @GetMapping("/calendarForEmployee/{year}/{month}")
    public List<CalendarForEmployeeDTO> getCalendarForEmployee(Principal principal, @PathVariable int month, @PathVariable int year){
       return employeeService.getCalendarForEmployee(principal, month, year);
    }

    @GetMapping("/metrics/{year}/{month}")
    public MetricDTO getMetrics(Principal principal, @PathVariable int month, @PathVariable int year){
        return employeeService.getMetrics(principal, month, year);
    }

    @PostMapping("/delete/meeting")
    public void deleteMeeting(@RequestBody MeetingForDelete meeting){
        employeeService.deleteMeeting(meeting);
    }
}
