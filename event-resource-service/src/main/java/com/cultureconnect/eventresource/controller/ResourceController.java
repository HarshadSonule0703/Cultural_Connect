	package com.cultureconnect.eventresource.controller;

	import jakarta.validation.Valid;

	import lombok.RequiredArgsConstructor;

	import org.slf4j.Logger;

	import org.slf4j.LoggerFactory;

	import org.springframework.web.bind.annotation.*;

	import com.cultureconnect.eventresource.dto.*;

	import com.cultureconnect.eventresource.service.ResourceService;

	import java.util.List;

	/**

	 * REST Controller for managing Resource-related operations.

	 * Provides endpoints for Creating, Reading, Updating, and Deleting (CRUD) resources.

	 */

	@RequiredArgsConstructor

	@RestController

	@RequestMapping("/api/resources")

	public class ResourceController {

	    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);


	    private final ResourceService resourceService;

	    /**

	     * Creates a new resource based on the provided request data.

	     * * @param request The data transfer object containing resource details.

	     * @return The created ResourceResponseDTO.

	     */

	    @PostMapping("/addresource")

	    public ResourceResponseDTO createResource(@Valid @RequestBody ResourceRequestDTO request) {

	        logger.info("REST request to create resource for eventId: {}", request.getEventId());

	        ResourceResponseDTO response = resourceService.createResource(request);

	        logger.info("Successfully created resource with ID: {}", response.getEventId());

	        return response;

	    }

	    /**

	     * Retrieves a list of all existing resources.

	     * * @return A list of ResourceResponseDTOs.

	     */

	    @GetMapping("/getallresource")

	    public List<ResourceResponseDTO> getAllResources() {

	        logger.info("REST request to fetch all resources");

	        List<ResourceResponseDTO> resources = resourceService.getAllResources();

	        logger.debug("Retrieved {} resources", resources.size());

	        return resources;

	    }

	    /**

	     * Retrieves a specific resource by its unique identifier.

	     * * @param id The ID of the resource to retrieve.

	     * @return The requested ResourceResponseDTO.

	     */

	    @GetMapping("/getresourcebyid/{id}")

	    public ResourceResponseDTO getResourceById(@PathVariable Long id) {

	        logger.info("REST request to fetch resource with id: {}", id);

	        ResourceResponseDTO response = resourceService.getResourceById(id);

	        logger.debug("Found resource for id: {}", id);

	        return response;

	    }

	    /**

	     * Updates an existing resource identified by ID.

	     * * @param id      The ID of the resource to update.

	     * @param request The updated data.

	     * @return The updated ResourceResponseDTO.

	     */

	    @PutMapping("/updateresource/{id}")

	    public ResourceResponseDTO updateResource(@PathVariable Long id,

	                                              @Valid @RequestBody ResourceRequestDTO request) {

	        logger.info("REST request to update resource with id: {}", id);

	        ResourceResponseDTO response = resourceService.updateResource(id, request);

	        logger.info("Successfully updated resource with id: {}", id);

	        return response;

	    }

	    /**

	     * Deletes a resource from the system.

	     * * @param id The ID of the resource to be removed.

	     * @return A confirmation message.

	     */

	    @DeleteMapping("/deleteresource/{id}")

	    public String deleteResource(@PathVariable Long id) {

	        logger.warn("REST request to delete resource with id: {}", id);

	        resourceService.deleteResource(id);

	        logger.info("Successfully deleted resource with id: {}", id);

	        return "Resource deleted successfully";

	    }

	}
 