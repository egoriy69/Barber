package com.example.barber.services;

import com.example.barber.DTOs.EmployeeDTO;
import com.example.barber.DTOs.EmployeeReviewsDTO;
import com.example.barber.DTOs.GetReviewDTO;
import com.example.barber.DTOs.ReviewDTO;
import com.example.barber.Exception.PasswordConfirmationException;
import com.example.barber.models.*;
import com.example.barber.repositories.ClientRepository;
import com.example.barber.repositories.EmployeeRepository;
import com.example.barber.repositories.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    public void createReview(ReviewDTO reviewDTO) {
        ClientInfo clientInfo = clientRepository.findByPhone(reviewDTO.getPhoneClient())
                .orElseThrow(() -> new NoSuchElementException("Клиент с номером: " + reviewDTO.getPhoneClient() + " не найден")).getClientInfo();

        EmployeeInfo employeeInfo = employeeRepository.findById(reviewDTO.getEmployeeId()).get().getEmployeeInfo();
        boolean exists = reviewRepository.existsByEmployeeInfoAndClientInfo(employeeInfo, clientInfo);

        if (exists) {
            throw new IllegalStateException("Отзыв от этого клиента для данного сотрудника уже существует");
        }

        reviewRepository.save(Review.builder()
                .reviewName(reviewDTO.getReviewName())
                .clientInfo(clientInfo)
                .employeeInfo(employeeInfo)
                .stars(reviewDTO.getStars())
                .build());
    }


    public List<EmployeeReviewsDTO> getReview() {
        List<Review> reviews = reviewRepository.findAllReviewsWithEmployeesAndClients();

        Map<EmployeeInfo, List<Review>> groupedByEmployee = reviews.stream()
                .collect(Collectors.groupingBy(Review::getEmployeeInfo));
        
        return groupedByEmployee.entrySet().stream().map(entry -> {
            EmployeeInfo employeeInfo = entry.getKey();
            List<Review> employeeReviews = entry.getValue();

            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setFIO(employeeInfo.getFIO());
            employeeDTO.setAvgStars(employeeReviews.stream().mapToInt(Review::getStars).average().orElse(0));

            List<GetReviewDTO> reviewDTOs = employeeReviews.stream().map(review -> {
                GetReviewDTO dto = new GetReviewDTO();
                dto.setFIOClient(review.getClientInfo().getFIO());
                dto.setStars(review.getStars());
                dto.setReviewName(review.getReviewName());
                return dto;
            }).collect(Collectors.toList());

            return new EmployeeReviewsDTO(employeeDTO, reviewDTOs);
        }).collect(Collectors.toList());
    }
}