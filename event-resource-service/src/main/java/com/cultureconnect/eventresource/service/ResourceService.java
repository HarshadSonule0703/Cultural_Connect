package com.cultureconnect.eventresource.service;
 
import java.util.List;

import com.cultureconnect.eventresource.dto.ResourceRequestDTO;
import com.cultureconnect.eventresource.dto.ResourceResponseDTO;
 
public interface ResourceService {
 
    ResourceResponseDTO createResource(ResourceRequestDTO request);
 
    List<ResourceResponseDTO> getAllResources();
 
    ResourceResponseDTO getResourceById(Long id);
 
    ResourceResponseDTO updateResource(Long id, ResourceRequestDTO request);
 
    void deleteResource(Long id);
}
 