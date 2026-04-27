package com.cultureconnect.eventresource.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.cultureconnect.eventresource.dto.ResourceRequestDTO;
import com.cultureconnect.eventresource.dto.ResourceResponseDTO;
import com.cultureconnect.eventresource.entity.Event;
import com.cultureconnect.eventresource.entity.Resource;
import com.cultureconnect.eventresource.exception.ResourceNotFoundException;
import com.cultureconnect.eventresource.repository.EventRepository;
import com.cultureconnect.eventresource.repository.ResourceRepository;
import com.cultureconnect.eventresource.service.ResourceService;
import com.cultureconnect.eventresource.util.ResourceMapper;

/**
 * Implementation of the {@link ResourceService}.
 * Handles business logic for Resource management, including resource-to-event 
 * association and state persistence.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    
    private final ResourceRepository resourceRepository;
    
     
    private final EventRepository eventRepository;
    public ResourceServiceImpl(ResourceRepository resourceRepository,
                      EventRepository eventRepository) {
        this.resourceRepository = resourceRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Creates and persists a new resource.
     * Links the resource to an existing Event via the eventId provided in the request.
     */
    @Override
    public ResourceResponseDTO createResource(ResourceRequestDTO request) {
        logger.info("Service request: Creating new resource for eventId: {}", request.getEventId());
        
        // 1. Map incoming DTO to Entity
        Resource resource = ResourceMapper.toEntity(request);
        
        // 2. Fetch the parent Event entity to establish the relationship
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> {
                    logger.error("Create failed: Event ID {} not found", request.getEventId());
                    return new RuntimeException("Event not found");
                });
        
        // 3. Link Event to Resource (Foreign Key association)
        resource.setEvent(event);
        
        // 4. Persist and map back to Response DTO
        Resource saved = resourceRepository.save(resource);
        logger.info("Resource successfully created with ID: {}", saved.getEvent());
        
        return ResourceMapper.toResponseDto(saved);
    }

    /**
     * Retrieves all resources from the database and maps them to DTOs.
     */
    @Override
    public List<ResourceResponseDTO> getAllResources() {
        logger.info("Service request: Fetching all resources");

        return resourceRepository.findAll()
                .stream()
                .map(ResourceMapper::toResponseDto)
                .toList();
    }

    /**
     * Retrieves a single resource by its unique ID.
     * @throws ResourceNotFoundException if ID does not exist.
     */
    @Override
    public ResourceResponseDTO getResourceById(Long id) {
        logger.info("Service request: Fetching resource by ID: {}", id);

        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Fetch failed: Resource ID {} not found", id);
                    return new ResourceNotFoundException("Resource not found");
                });

        return ResourceMapper.toResponseDto(resource);
    }

    /**
     * Updates an existing resource and refreshes its Event relationship if necessary.
     */
    @Override
    public ResourceResponseDTO updateResource(Long id, ResourceRequestDTO request) {
        logger.info("Service request: Updating resource ID: {}", id);

        // 1. Verify existence of the resource
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        // 2. Verify existence of the associated event
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> {
                    logger.error("Update failed: Event ID {} not found", request.getEventId());
                    return new RuntimeException("Event not found");
                });
         
        // 3. Update entity fields
        resource.setEvent(event);
        resource.setType(request.getType());
        resource.setQuantity(request.getQuantity());
        resource.setStatus(request.getStatus());

        // 4. Save and return updated data
        Resource updated = resourceRepository.save(resource);
        logger.info("Resource successfully updated for ID: {}", id);
        
        return ResourceMapper.toResponseDto(updated);
    }

    /**
     * Permanently deletes a resource from the database.
     */
    @Override
    public void deleteResource(Long id) {
        // Log at WARN level because deletion is destructive
        logger.warn("Service request: Deleting resource with ID: {}", id);
        
        if (!resourceRepository.existsById(id)) {
            logger.error("Delete failed: Resource ID {} does not exist", id);
            throw new ResourceNotFoundException("Resource not found");
        }
        
        resourceRepository.deleteById(id);
        logger.info("Resource ID: {} deleted successfully", id);
    }
}