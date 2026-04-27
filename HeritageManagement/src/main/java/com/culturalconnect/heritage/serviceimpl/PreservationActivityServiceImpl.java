package com.culturalconnect.heritage.serviceimpl;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.culturalconnect.heritage.dto.PreservationActivityDTO;
import com.culturalconnect.heritage.entity.HeritageSite;
import com.culturalconnect.heritage.entity.PreservationActivity;
import com.culturalconnect.heritage.exception.ResourceNotFoundException;
import com.culturalconnect.heritage.repository.HeritageSiteRepository;
import com.culturalconnect.heritage.repository.PreservationActivityRepository;
import com.culturalconnect.heritage.service.PreservationActivityService;

@Service
public class PreservationActivityServiceImpl implements PreservationActivityService {

    private static final Logger logger = LoggerFactory.getLogger(PreservationActivityServiceImpl.class);
 
    private final PreservationActivityRepository activityRepository;
    private final HeritageSiteRepository siteRepository;
 
    public PreservationActivityServiceImpl(
            PreservationActivityRepository activityRepository,
            HeritageSiteRepository siteRepository) {
 
        this.activityRepository = activityRepository;
        this.siteRepository = siteRepository;
    }
 
    @Override
    public PreservationActivity addActivity(PreservationActivityDTO dto) {
        logger.info("Adding preservation activity for siteId: {}", dto.getSiteId());

        HeritageSite site = siteRepository.findById(dto.getSiteId())
                .orElseThrow(() -> {
                    logger.error("Heritage site not found with id: {}", dto.getSiteId());
                    return new ResourceNotFoundException("Heritage site not found");
                });
 
        PreservationActivity activity = new PreservationActivity();
        activity.setSiteId(dto.getSiteId());
        activity.setOfficerId(dto.getOfficerId());
        activity.setDescription(dto.getDescription());
        activity.setDate(dto.getDate());
        activity.setStatus(dto.getStatus());
 
        PreservationActivity savedActivity = activityRepository.save(activity);

        logger.info("Preservation activity created with id: {}", savedActivity.getActivityId());
        return savedActivity;
    }
 
    @Override
    public List<PreservationActivity> getActivitiesBySite(Long siteId) {
        logger.info("Fetching preservation activities for siteId: {}", siteId);
        return activityRepository.findBySiteId(siteId);
    }
 
    @Override
    public PreservationActivity getActivityBySiteAndId(Long siteId, Long activityId) {
        logger.info(
            "Fetching preservation activity with activityId: {} for siteId: {}",
            activityId, siteId
        );
 
        return activityRepository
                .findByActivityIdAndSiteId(activityId, siteId)
                .orElseThrow(() -> {
                    logger.error(
                        "Preservation activity not found for activityId: {} and siteId: {}",
                        activityId, siteId
                    );
                    return new ResourceNotFoundException(
                            "Activity not found for this site");
                });
    }
 
    @Override
    public void deleteActivity(Long id) {
        logger.info("Deleting preservation activity with id: {}", id);
        activityRepository.deleteById(id);
        logger.info("Preservation activity deleted with id: {}", id);
    }
    
    
}