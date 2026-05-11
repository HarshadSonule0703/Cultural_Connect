package com.culturalconnect.heritage.service;

import java.util.List;

import com.culturalconnect.heritage.dto.PreservationActivityDTO;
import com.culturalconnect.heritage.entity.PreservationActivity;

public interface PreservationActivityService {
 
    PreservationActivity addActivity(PreservationActivityDTO dto);
 
    List<PreservationActivity> getActivitiesBySite(String siteId);
 
    PreservationActivity getActivityBySiteAndId(String siteId, Long activityId);
 
    void deleteActivity(Long id);
    
    
}