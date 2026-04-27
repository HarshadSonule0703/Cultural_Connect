package com.cultureconnect.ComplianceAudit.dto;

import java.time.LocalDate;
import java.util.List;

import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long eventId;
    private String title;
    private String location;
    private LocalDate date;
    private Status status;
    private Long programId;
}
