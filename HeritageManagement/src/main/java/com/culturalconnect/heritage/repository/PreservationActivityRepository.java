package com.culturalconnect.heritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.culturalconnect.heritage.entity.PreservationActivity;
 
import java.util.List;
import java.util.Optional;
 
public interface PreservationActivityRepository extends JpaRepository<PreservationActivity, Long> {
 
	List<PreservationActivity> findBySiteId(Long siteId);

 
    Optional<PreservationActivity> findByActivityIdAndSiteId(
            Long activityId,
            Long siteId
    );
    
}