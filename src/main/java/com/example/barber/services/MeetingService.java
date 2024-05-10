package com.example.barber.services;

import com.example.barber.DTOs.MeetingDTO;
import com.example.barber.models.Client;
import com.example.barber.models.ClientInfo;
import com.example.barber.models.Meeting;
import com.example.barber.models.PriceList;
import com.example.barber.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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
                .endTime(meetingDTO.getEndTime())
                .priceList(priceListRepository.findById(meetingDTO.getPriceListId()).get())
                .build());
    }

    @Transactional
    public Client createClientAndClientInfo(MeetingDTO meetingDTO) {
        ClientInfo clientInfo = new ClientInfo();
        Client client = new Client();
        client.setPhone(meetingDTO.getPhoneClient());
        clientInfo.setFIO(meetingDTO.getFIO());

        // Установка ссылки с обеих сторон
        client.setClientInfo(clientInfo);
        clientInfo.setClient(client);

        // Сначала сохраняем владельца отношения, если нет каскадирования на обе стороны
        clientInfoRepository.save(clientInfo);
//        clientRepository.save(client); // Если есть каскадирование с Client на ClientInfo, это необязательно
        return client;
    }

    public List<LocalDateTime> getAvailableSlots(Long employeeId, LocalDate date, Long serviceId) {
        PriceList service = priceListRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));

        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.of(8, 0)); // Начало рабочего дня
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.of(18, 0)); // Конец рабочего дня

        List<Meeting> meetings = meetingRepository.findByEmployeeInfoIdAndStartTimeBetween(employeeId, startOfDay, endOfDay);

        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime currentTime = startOfDay;
        while (currentTime.plus(service.getDuration()).isBefore(endOfDay)) {
            boolean isAvailable = true;
            for (Meeting meeting : meetings) {
                System.out.println(service.getDuration());
                if (currentTime.plus(service.getDuration()).isAfter(meeting.getStartTime()) &&
                        currentTime.isBefore(meeting.getStartTime().plus(service.getDuration()))) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableSlots.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(30); // Проверяем каждые 30 минут
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
                .endTime(meetingDTO.getStartTime().plus(priceListRepository.findById(meetingDTO.getPriceListId()).get().getDuration()))
                .build();
        return meetingRepository.save(meeting);
    }

//    @Transactional
//    public Meeting createMeeting2(Long employeeId, Long clientId, Long serviceId, LocalDateTime startTime) {
//        Meeting meeting = Meeting.builder()
//                .employeeInfo(employeeRepository.findById(employeeId).get().getEmployeeInfo())
//                .clientInfo(clientRepository.findById(clientId).get().getClientInfo())
//                .priceList(priceListRepository.findById(serviceId).get())
//                .startTime(startTime)
//                .endTime(startTime.plus(priceListRepository.findById(serviceId).get().getDuration()))
//                .build();
//        return meetingRepository.save(meeting);
//    }
}
