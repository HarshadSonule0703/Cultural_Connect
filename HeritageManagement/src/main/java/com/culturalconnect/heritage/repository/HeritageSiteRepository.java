package com.culturalconnect.heritage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.culturalconnect.heritage.entity.HeritageSite;
 
@Repository
public interface HeritageSiteRepository extends JpaRepository<HeritageSite, Long> {
}
