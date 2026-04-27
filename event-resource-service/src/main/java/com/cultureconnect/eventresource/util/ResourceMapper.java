package com.cultureconnect.eventresource.util;
 
import com.cultureconnect.eventresource.dto.ResourceRequestDTO;
import com.cultureconnect.eventresource.dto.ResourceResponseDTO;
import com.cultureconnect.eventresource.entity.Resource;
 
public class ResourceMapper {
	private ResourceMapper () {
		throw new UnsupportedOperationException("Utility Class");
	}
 
    // REQUEST → ENTITY
    public static Resource toEntity(ResourceRequestDTO dto) {
        if (dto == null) return null;
 
        Resource resource = new Resource();
        resource.setType(dto.getType());
        resource.setName(dto.getName());
        resource.setQuantity(dto.getQuantity());
        resource.setStatus(dto.getStatus());
 
        return resource;
    }
 
    // ENTITY → RESPONSE
    public static ResourceResponseDTO toResponseDto(Resource resource) {
        if (resource == null) return null;
 
        ResourceResponseDTO dto = new ResourceResponseDTO();
        dto.setResourceId(resource.getResourceId());
        dto.setEventId(
        		resource.getEvent() != null ? resource.getEvent().getEventId() : null
        				);
        dto.setType(resource.getType());
        dto.setName(resource.getName());
        dto.setQuantity(resource.getQuantity());
        dto.setStatus(resource.getStatus());
 
        return dto;
    }
}
 