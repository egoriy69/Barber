package com.example.barber.services;

import com.example.barber.DTOs.CalendarOnMonthDTO;
import com.example.barber.DTOs.MeetingDTO;
import com.example.barber.models.Client;
import com.example.barber.models.ClientInfo;
import com.example.barber.models.Meeting;
import com.example.barber.models.PriceList;
import com.example.barber.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.*;
import java.util.*;

@Service
@AllArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final PriceListRepository priceListRepository;
    private final ClientInfoRepository clientInfoRepository;


    @Transactional
    public void createMeeting(MeetingDTO meetingDTO) {
        Client client = clientRepository.findByPhone(meetingDTO.getPhoneClient()).orElse(createClientAndClientInfo(meetingDTO));

        meetingRepository.save(Meeting.builder()
                .clientInfo(client.getClientInfo())
                .comment(meetingDTO.getComment())
                .employeeInfo(employeeRepository.findById(meetingDTO.getEmployeeId()).get().getEmployeeInfo())
                .startTime(meetingDTO.getStartTime())
//                .endTime(meetingDTO.getEndTime())
                .priceList(priceListRepository.findById(meetingDTO.getPriceListId()).get())
                .build());
    }

    @Transactional
    public Client createClientAndClientInfo(MeetingDTO meetingDTO) {
        ClientInfo clientInfo = new ClientInfo();
        Client client = new Client();
        client.setPhone(meetingDTO.getPhoneClient());
        clientInfo.setFIO(meetingDTO.getFIO());

        client.setClientInfo(clientInfo);
        clientInfo.setClient(client);

        clientInfoRepository.save(clientInfo);
        return client;
    }

    public List<LocalDateTime> getAvailableSlotsOnDay(Long employeeId, LocalDate date, Long serviceId) {
        PriceList service = priceListRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.of(8, 0));
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.of(18, 0));
        List<Meeting> meetings = meetingRepository.findByEmployeeInfoIdAndStartTimeBetween(employeeId, startOfDay, endOfDay);
        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime currentTime = startOfDay;

        while (currentTime.plus(service.getDuration()).isBefore(endOfDay)) {
            boolean isAvailable = true;
            for (Meeting meeting : meetings) {
                Duration meetingDuration = meeting.getPriceList().getDuration();
                LocalDateTime meetingEndTime = meeting.getStartTime().plus(meetingDuration);
                if (currentTime.plus(service.getDuration()).isAfter(meeting.getStartTime()) && currentTime.isBefore(meetingEndTime)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableSlots.add(currentTime);
            }

            currentTime = currentTime.plusMinutes(30);

        }
        return availableSlots;
    }

    @Transactional
    public Meeting createMeeting2(MeetingDTO meetingDTO) {
        Client client = clientRepository.findByPhone(meetingDTO.getPhoneClient()).orElse(createClientAndClientInfo(meetingDTO));
        Meeting meeting = Meeting.builder()
                .employeeInfo(employeeRepository.findById(meetingDTO.getEmployeeId()).get().getEmployeeInfo())
                .clientInfo(client.getClientInfo())
                .priceList(priceListRepository.findById(meetingDTO.getPriceListId()).get())
                .startTime(meetingDTO.getStartTime())
                .build();
        return meetingRepository.save(meeting);
    }


    public List<CalendarOnMonthDTO> getCalendar(int month, int year, Long serviceId, Long employeeId) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        final LocalDate currentDate = startDate;
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        DayOfWeek dayOfWeek = LocalDate.of(year, month, 1).getDayOfWeek();
        startDate = startDate.minusDays(dayOfWeek.getValue() - 1);
        int countDays = dayOfWeek.getValue() + endDate.getDayOfMonth() - 1;
        endDate = startDate.plusDays(countDays > 35 ? 41 : 34);
        List<CalendarOnMonthDTO> calendarData = new ArrayList<>();

        for (LocalDate localDate = startDate; localDate.isBefore(endDate.plusDays(1)); localDate = localDate.plusDays(1)) {
            CalendarOnMonthDTO dto = new CalendarOnMonthDTO();
            dto.setLocalDate(localDate);
            dto.setCurrent(month == localDate.getMonth().getValue());
//            System.out.println(getAvailableSlotsOnDay(employeeId, startDate, serviceId));
            if (getAvailableSlotsOnDay(employeeId, localDate, serviceId).isEmpty()){
                dto.setAvailable(false);
            } else {
                dto.setAvailable(true);
            }
            calendarData.add(dto);
        }

        return calendarData;
    }
}
