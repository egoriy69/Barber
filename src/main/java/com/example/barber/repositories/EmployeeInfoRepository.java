package com.example.barber.repositories;

import com.example.barber.models.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Integer> {
}
