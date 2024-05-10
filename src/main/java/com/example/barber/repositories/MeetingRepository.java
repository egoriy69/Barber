package com.example.barber.repositories;

import com.example.barber.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByStartTimeBetween(LocalDateTime atStartOfDay, LocalDateTime atTime);

    List<Meeting> findByEmployeeInfoIdAndStartTimeBetween(Long employeeId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
