package com.culturalconnect.heritage.service;

import java.util.List;
import com.culturalconnect.heritage.dto.HeritageSiteDTO;
import com.culturalconnect.heritage.entity.HeritageSite;

public interface HeritageSiteService {
	HeritageSite createSite(HeritageSiteDTO dto);

	List<HeritageSite> getAllSites();

	HeritageSite getSiteById(String id);

	HeritageSite updateSite(String id, HeritageSiteDTO dto);

	void deleteSite(String id);
}
