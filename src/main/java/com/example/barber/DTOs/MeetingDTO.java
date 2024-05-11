package com.example.barber.DTOs;

import com.example.barber.models.ClientInfo;
import com.example.barber.models.EmployeeInfo;
import com.example.barber.models.Meeting;
import com.example.barber.models.PriceList;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingDTO {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String comment;

    private String FIO;

    private Long priceListId;

    private Long employeeId;

    private String phoneClient;

    public MeetingDTO(Meeting meeting) {
        this.startTime = meeting.getStartTime();
        this.endTime = this.getStartTime().plusMinutes(meeting.getPriceList().getDuration().toMinutes());
        this.comment = meeting.getComment();
        this.FIO = meeting.getClientInfo().getFIO();
        this.priceListId = meeting.getPriceList().getId();
        this.employeeId = meeting.getEmployeeInfo().getId();
        this.phoneClient = meeting.getClientInfo().getClient().getPhone();
    }

}
