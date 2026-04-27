package com.culturalconnect.heritage.service;

import java.util.List;
import com.culturalconnect.heritage.dto.HeritageSiteDTO;
import com.culturalconnect.heritage.entity.HeritageSite;

public interface HeritageSiteService {
	HeritageSite createSite(HeritageSiteDTO dto);

	List<HeritageSite> getAllSites();

	HeritageSite getSiteById(Long id);

	HeritageSite updateSite(Long id, HeritageSiteDTO dto);

	void deleteSite(Long id);
}
