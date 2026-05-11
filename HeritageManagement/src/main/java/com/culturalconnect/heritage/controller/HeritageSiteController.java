package com.culturalconnect.heritage.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.culturalconnect.heritage.dto.HeritageSiteDTO;
import com.culturalconnect.heritage.entity.HeritageSite;
import com.culturalconnect.heritage.service.HeritageSiteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/heritage-sites")
public class HeritageSiteController {

    private static final Logger logger =
            LoggerFactory.getLogger(HeritageSiteController.class);

    private final HeritageSiteService service;
    
    private static final String UPLOAD_DIR = "uploads/";

    public HeritageSiteController(HeritageSiteService service) {
        this.service = service;
    }
    

    @PostMapping("/uploadImage/{siteId}")
    public Map<String, String> uploadImage(
            @PathVariable String siteId,
            @RequestParam("file") MultipartFile file) throws IOException {

        // ✅ Base upload directory (e.g., uploads/1/)
        File uploadDir = new File(UPLOAD_DIR + siteId + File.separator);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // ✅ MUST create directory
        }

        // ✅ Save file INSIDE siteId folder
        String filePath = uploadDir.getAbsolutePath() 
                            + File.separator 
                            + file.getOriginalFilename();

        file.transferTo(new File(filePath));

        // ✅ Logical file URI (safe to store in DB)
        String fileUri = siteId + "/" + file.getOriginalFilename();

        Map<String, String> response = new HashMap<>();
        response.put("fileUri", fileUri);

        return response;
    }


    @PostMapping("/addSite")
    public HeritageSite createSite(@Valid @RequestBody HeritageSiteDTO dto) {
        logger.info("Received request to create heritage site");
        System.out.println("Siteid "+dto.getSiteId()+",url="+dto.getFileUri());
        return service.createSite(dto);
    }

    @GetMapping("/getAllSites")
    public List<HeritageSite> getAllSites() {
        logger.info("Received request to fetch all heritage sites");
        return service.getAllSites();
    }

    @GetMapping("/getSitebyId/{id}")
    public HeritageSite getSite(@PathVariable String id) {
        logger.info("Received request to fetch heritage site with id: {}", id);
        return service.getSiteById(id);
    }

    @PutMapping("/updateSiteDetails/{id}")
    public HeritageSite updateSite(@PathVariable String id,
                                   @RequestBody HeritageSiteDTO dto) {
        logger.info("Received request to update heritage site with id: {}", id);
        return service.updateSite(id, dto);
    }

    @DeleteMapping("/deleteSite/{id}")
    public void deleteSite(@PathVariable String id) {
        logger.info("Received request to delete heritage site with id: {}", id);
        service.deleteSite(id);
    }
}