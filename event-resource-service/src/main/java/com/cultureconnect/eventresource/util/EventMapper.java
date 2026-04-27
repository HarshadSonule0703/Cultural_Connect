package com.cultureconnect.eventresource.util;

import com.cultureconnect.eventresource.dto.EventRequestDto;
import com.cultureconnect.eventresource.dto.EventResponseDto;
import com.cultureconnect.eventresource.entity.Event;

public final class EventMapper {

    private EventMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    /* ==========================
       REQUEST DTO → ENTITY
       ========================== */
    public static Event toEntity(EventRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setLocation(dto.getLocation());
        event.setDate(dto.getDate());
        event.setStatus(dto.getStatus());
        event.setProgramId(dto.getProgramId()); // ✅ ONLY ID

        return event;
    }

    /* ==========================
       ENTITY → RESPONSE DTO
       ========================== */
    public static EventResponseDto toResponseDto(Event event) {
        if (event == null) {
            return null;
        }

        EventResponseDto dto = new EventResponseDto();
        dto.setEventId(event.getEventId());
        dto.setTitle(event.getTitle());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate());
        dto.setStatus(event.getStatus());
        dto.setProgramId(event.getProgramId()); // ONLY ID

        return dto;
    }
}
