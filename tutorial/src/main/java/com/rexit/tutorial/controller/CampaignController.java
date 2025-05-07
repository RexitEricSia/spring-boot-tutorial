package com.rexit.tutorial.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rexit.tutorial.dto.CampaignPatchDTO;
import com.rexit.tutorial.model.Campaign;
import com.rexit.tutorial.service.CampaignService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }

    // @PostMapping(consumes = "application/x-www-form-urlencoded")
    // @PostMapping(consumes = "text/plain")
    // @PostMapping(consumes = "application/xml")
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody @Valid Campaign newCampaign) {
        return ResponseEntity.ok(campaignService.createCampaign(newCampaign));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id,
            @RequestBody @Valid Campaign updatedCampaign) {
        return ResponseEntity.ok(campaignService.updateCampaign(id, updatedCampaign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.deleteCampaign(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Campaign> patchCampaign(@PathVariable Long id, @RequestBody @Valid CampaignPatchDTO dto) {
        return ResponseEntity.ok(campaignService.patchCampaign(id, dto));
    }
}
