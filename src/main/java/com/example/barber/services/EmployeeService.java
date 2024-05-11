package com.example.barber.services;

import com.example.barber.DTOs.CalendarForEmployeeDTO;
import com.example.barber.DTOs.MeetingDTO;
import com.example.barber.DTOs.MetricDTO;
import com.example.barber.DTOs.RegistrationEmployeeDTO;
import com.example.barber.Exception.PasswordConfirmationException;
import com.example.barber.models.Employee;
import com.example.barber.models.EmployeeInfo;
import com.example.barber.models.Meeting;
import com.example.barber.repositories.EmployeeRepository;
import com.example.barber.repositories.MeetingRepository;
import com.example.barber.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MeetingRepository meetingRepository;

    @Transactional
    public void createUser(RegistrationEmployeeDTO employeeDTO) {

        if (!employeeDTO.getPassword().equals(employeeDTO.getConfirmPassword())) {
            throw new PasswordConfirmationException("Пароли не совпадают");

        }

        Employee employee = new Employee();

        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setEmployee(employee);
        employeeInfo.setFIO(employeeDTO.getFIO());

        employee.setPhone(employeeDTO.getPhone());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setEmployeeInfo(employeeInfo);


        employeeRepository.save(employee);
    }

    @Transactional
    public void logout(Principal principal) {
        Employee user = employeeRepository.findByPhone(principal.getName()).get();
        refreshTokenRepository.delete(refreshTokenRepository.findByEmployee(user).get());
    }

    public List<CalendarForEmployeeDTO> getCalendarForEmployee(int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        DayOfWeek dayOfWeek = LocalDate.of(year, month, 1).getDayOfWeek();
        startDate = startDate.minusDays(dayOfWeek.getValue() - 1);
        int countDays = dayOfWeek.getValue() + endDate.getDayOfMonth() - 1;
        endDate = startDate.plusDays(countDays > 35 ? 41 : 34);

       List<Meeting> meetings = meetingRepository.findByStartTimeBetween(startDate.atTime(0,0,0), endDate.atTime(23, 59, 59));

        List<CalendarForEmployeeDTO> calendarForEmployeeDTOS = new ArrayList<>();

        for (LocalDate localDate = startDate; localDate.isBefore(endDate.plusDays(1)); localDate = localDate.plusDays(1)) {
            final LocalDate currentDate = localDate;
            CalendarForEmployeeDTO dto = new CalendarForEmployeeDTO();
            List<MeetingDTO> meetingDTOs = meetings.stream()
                    .filter(meeting -> meeting.getStartTime().toLocalDate().isEqual(currentDate))
                    .map(MeetingDTO::new).toList();
            dto.setMeetings(meetingDTOs);
            dto.setCount(meetingDTOs.size());
            dto.setCreatedAt(currentDate);
            dto.setCurrent(month==currentDate.getMonth().getValue());
            calendarForEmployeeDTOS.add(dto);
        }
        return calendarForEmployeeDTOS;
    }

    public MetricDTO getMetrics(Principal principal, int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Meeting> meetings = meetingRepository.findByEmployeeInfoIdAndStartTimeBetween(employeeRepository.findByPhone(principal.getName()).get().getId(),startDate.atTime(0,0,0), endDate.atTime(23, 59, 59));

        MetricDTO dto = new MetricDTO();
        double totalSum = meetings.stream().mapToDouble(meeting -> meeting.getPriceList().getCost()).sum();
        double averageCheck = meetings.isEmpty() ? 0 : totalSum / meetings.size();
        dto.setSum((long) totalSum);
        dto.setAvgBill(averageCheck);
        dto.setNumberOfOrders(meetings.size());
        Set<Long> uniqueClient = meetings.stream().map(meeting -> meeting.getClientInfo().getId()).collect(Collectors.toSet());
        int uniqueCustomers = uniqueClient.size();
        dto.setNumberOfUniqueClients(uniqueCustomers);
        return dto;
    }
}
