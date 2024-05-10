package com.example.barber.services;

import com.example.barber.DTOs.MeetingDTO;
import com.example.barber.models.Client;
import com.example.barber.models.ClientInfo;
import com.example.barber.models.Meeting;
import com.example.barber.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
