package com.cultureconnect.eventresource.dto;

import java.time.LocalDate;

import com.cultureconnect.eventresource.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {

    @NotBlank(message = "Event title is required")
    private String title;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Event date is required")
    private LocalDate date;

    private Status status;

    @NotNull(message = "Program ID is required to link this event")
    private Long programId;
}
