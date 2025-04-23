package com.rexit.tutorial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rexit.tutorial.enums.Error;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.Campaign;
import com.rexit.tutorial.repository.CampaignRepository;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> getAllCampaigns() {

        try {
            return campaignRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_FETCH_ERROR);
        }
    }

    public Campaign getCampaignById(Long id) {

        Optional<Campaign> campaign = null;

        try {
            campaign = campaignRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(Error.CAMPAIGN_FETCH_BY_ID_ERROR);
        }

        if (campaign.isPresent()) {
            return campaign.get();
        } else {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND);
        }
    }

    public Campaign createCampaign(Campaign newCampaign) {

        if (campaignRepository.existsByCampaignCode(newCampaign.getCampaignCode())) {
            throw new BusinessException(Error.CAMPAIGN_DUPLICATE_CODE);
        }

        try {
            return campaignRepository.save(newCampaign);
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_CREATE_ERROR);
        }
    }

    public Campaign updateCampaign(Long id, Campaign updatedCampaign) {

        if (!id.equals(updatedCampaign.getId())) {
            throw new BusinessException(Error.CAMPAIGN_ID_MISMATCH);
        }

        if (!campaignRepository.existsById(id)) {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND_FOR_UPDATE);
        }

        try {
            return campaignRepository.save(updatedCampaign);
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_UPDATE_ERROR);
        }
    }

    public boolean deleteCampaign(Long id) {

        if (!campaignRepository.existsById(id)) {
            throw new BusinessException(Error.CAMPAIGN_ID_NOT_FOUND_FOR_DELETE);
        }

        try {
            campaignRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new BusinessException(Error.CAMPAIGN_DELETE_ERROR);
        }
    }
}
