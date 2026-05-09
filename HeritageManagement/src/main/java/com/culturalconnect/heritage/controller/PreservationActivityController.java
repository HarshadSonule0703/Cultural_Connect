	package com.culturalconnect.heritage.controller;
	
	import com.culturalconnect.heritage.dto.PreservationActivityDTO;
	import com.culturalconnect.heritage.entity.PreservationActivity;
	import com.culturalconnect.heritage.service.PreservationActivityService;
	
	import jakarta.validation.Valid;
	
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.web.bind.annotation.*;
	
	import java.util.List;
	
	@RestController
	@RequestMapping("/api/activities")
	public class PreservationActivityController {
	
	    private static final Logger logger =
	            LoggerFactory.getLogger(PreservationActivityController.class);
	
	    private final PreservationActivityService service;
	
	    public PreservationActivityController(PreservationActivityService service) {
	        this.service = service;
	    }
	
	    @PostMapping("/addActivity")
	    public PreservationActivity addActivity(
	            @Valid @RequestBody PreservationActivityDTO dto) {
	        logger.info("Received request to add preservation activity");
	        return service.addActivity(dto);
	    }
	
	    @GetMapping("/getActivitiesBySite/{siteId}")
	    public List<PreservationActivity> getActivitiesBySite(@PathVariable Long siteId) {
	        logger.info("Received request to fetch activities for siteId: {}", siteId);
	        return service.getActivitiesBySite(siteId);
	    }
	
	    @GetMapping("/site/{siteId}/activity/{activityId}")
	    public PreservationActivity getActivityBySiteAndId(
	            @PathVariable Long siteId,
	            @PathVariable Long activityId) {
	        logger.info(
	            "Received request to fetch activity with activityId: {} for siteId: {}",
	            activityId, siteId
	        );
	        return service.getActivityBySiteAndId(siteId, activityId);
	    }
	
	    @DeleteMapping("/deleteActivity/{id}")
	    public void deleteActivity(@PathVariable Long id) {
	        logger.info("Received request to delete activity with id: {}", id);
	        service.deleteActivity(id);
	    }
	    
	    
	}