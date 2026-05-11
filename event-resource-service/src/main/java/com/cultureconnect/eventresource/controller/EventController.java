package com.cultureconnect.eventresource.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.eventresource.dto.EventRequestDto;
import com.cultureconnect.eventresource.dto.EventResponseDto;
import com.cultureconnect.eventresource.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller for managing Event entities.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    /**
     * Creates a new event entry.
     */
    @PostMapping("/addevent")
    public EventResponseDto createEvent(@Valid @RequestBody EventRequestDto request) {
        logger.info("REST request to create new event: {}", request.getTitle());
        EventResponseDto createdEvent = eventService.createEvent(request);
        logger.info("Event successfully created with ID: {}", createdEvent.getEventId());
        return createdEvent;
    }

    /**
     * Updates an existing event.
     */
    @PutMapping("/{id}")
    public EventResponseDto updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequestDto request) {
        logger.info("REST request to update event with id: {}", id);
        EventResponseDto updatedEvent = eventService.updateEvent(id, request);
        logger.info("Successfully updated event id: {}", id);
        return updatedEvent;
    }

    /**
     * Deletes an event.
     */
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id) {
        logger.warn("REST request to delete event with id: {}", id);
        eventService.deleteEvent(id);
        logger.info("Event with id: {} successfully deleted", id);
        return "Event deleted successfully";
    }

    /**
     * Retrieves all events stored in the database.
     */
    @GetMapping("/getallevents")
    public List<EventResponseDto> getAllEvents() {
        logger.info("REST request to fetch all events");
        return eventService.getAllEvents();
    }

    /**
     * Retrieves details of a specific event by its ID.
     */
    @GetMapping("/{id}")
    public EventResponseDto getEventById(@PathVariable Long id) {
        logger.info("REST request to fetch event with id: {}", id);
        return eventService.getEventById(id);
    }

    /**
     * Retrieves all events with status APPROVED.
     */
    @GetMapping("/approved")
    public List<EventResponseDto> getApprovedEvents() {
        logger.info("REST request to fetch all approved events");
        return eventService.getAllApprovedEvents();
    }

    /**
     * Fetches associated Event IDs for a specific Program.
     */
    @GetMapping("/program/{programId}")
    public List<Long> getEventsIdByProgramId(@PathVariable Long programId) {
        return eventService.getEventsIdByProgramId(programId);
    }
}