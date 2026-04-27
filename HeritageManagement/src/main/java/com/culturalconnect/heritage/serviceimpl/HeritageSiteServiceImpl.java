package com.culturalconnect.heritage.serviceimpl;

import com.culturalconnect.heritage.dto.HeritageSiteDTO;
import com.culturalconnect.heritage.entity.HeritageSite;
import com.culturalconnect.heritage.exception.ResourceNotFoundException;
import com.culturalconnect.heritage.repository.HeritageSiteRepository;
import com.culturalconnect.heritage.service.HeritageSiteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeritageSiteServiceImpl implements HeritageSiteService {

    private static final Logger logger =
            LoggerFactory.getLogger(HeritageSiteServiceImpl.class);

    private final HeritageSiteRepository repository;

    public HeritageSiteServiceImpl(HeritageSiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public HeritageSite createSite(HeritageSiteDTO dto) {
        logger.info("Creating heritage site with name: {}", dto.getName());

        HeritageSite site = new HeritageSite();
        site.setName(dto.getName());
        site.setLocation(dto.getLocation());
        site.setDescription(dto.getDescription());
        site.setStatus(dto.getStatus());

        HeritageSite savedSite = repository.save(site);
        logger.info("Heritage site created with id: {}", savedSite.getSiteId());

        return savedSite;
    }

    @Override
    public List<HeritageSite> getAllSites() {
        logger.info("Fetching all heritage sites");
        return repository.findAll();
    }

    @Override
    public HeritageSite getSiteById(Long id) {
        logger.info("Fetching heritage site with id: {}", id);

        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Heritage site not found with id: {}", id);
                    return new ResourceNotFoundException("Site not found");
                });
    }

    @Override
    public HeritageSite updateSite(Long id, HeritageSiteDTO dto) {
        logger.info("Updating heritage site with id: {}", id);

        HeritageSite site = getSiteById(id);

        site.setName(dto.getName());
        site.setLocation(dto.getLocation());
        site.setDescription(dto.getDescription());
        site.setStatus(dto.getStatus());

        HeritageSite updatedSite = repository.save(site);
        logger.info("Heritage site updated with id: {}", id);

        return updatedSite;
    }

    @Override
    public void deleteSite(Long id) {
        logger.info("Deleting heritage site with id: {}", id);

        HeritageSite site = getSiteById(id);
        repository.delete(site);

        logger.info("Heritage site deleted with id: {}", id);
    }
}