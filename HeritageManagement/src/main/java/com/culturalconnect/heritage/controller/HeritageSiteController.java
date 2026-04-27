package com.culturalconnect.heritage.controller;

import com.culturalconnect.heritage.dto.HeritageSiteDTO;
import com.culturalconnect.heritage.entity.HeritageSite;
import com.culturalconnect.heritage.service.HeritageSiteService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heritage-sites")
public class HeritageSiteController {

    private static final Logger logger =
            LoggerFactory.getLogger(HeritageSiteController.class);

    private final HeritageSiteService service;

    public HeritageSiteController(HeritageSiteService service) {
        this.service = service;
    }

    @PostMapping("/addSite")
    public HeritageSite createSite(@Valid @RequestBody HeritageSiteDTO dto) {
        logger.info("Received request to create heritage site");
        return service.createSite(dto);
    }

    @GetMapping("/getAllSites")
    public List<HeritageSite> getAllSites() {
        logger.info("Received request to fetch all heritage sites");
        return service.getAllSites();
    }

    @GetMapping("/getSitebyId/{id}")
    public HeritageSite getSite(@PathVariable Long id) {
        logger.info("Received request to fetch heritage site with id: {}", id);
        return service.getSiteById(id);
    }

    @PutMapping("/updateSiteDetails/{id}")
    public HeritageSite updateSite(@PathVariable Long id,
                                   @RequestBody HeritageSiteDTO dto) {
        logger.info("Received request to update heritage site with id: {}", id);
        return service.updateSite(id, dto);
    }

    @DeleteMapping("/deleteSite/{id}")
    public void deleteSite(@PathVariable Long id) {
        logger.info("Received request to delete heritage site with id: {}", id);
        service.deleteSite(id);
    }
}