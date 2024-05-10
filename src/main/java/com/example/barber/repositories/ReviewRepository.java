package com.example.barber.repositories;

import com.example.barber.models.ClientInfo;
import com.example.barber.models.EmployeeInfo;
import com.example.barber.models.Review;
import com.example.barber.models.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    @Query("SELECT r FROM Review r JOIN FETCH r.employeeInfo e JOIN FETCH e.employee JOIN FETCH r.clientInfo c")
    List<Review> findAllReviewsWithEmployeesAndClients();

    boolean existsByEmployeeInfoAndClientInfo(EmployeeInfo employeeInfo, ClientInfo clientInfo);

    List<Review> findByEmployeeInfo(EmployeeInfo employeeInfo);
}
