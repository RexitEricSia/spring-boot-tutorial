package com.rexit.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rexit.tutorial.model.Campaign;


public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    
    boolean existsByCampaignCode(String campaignCode);
}
