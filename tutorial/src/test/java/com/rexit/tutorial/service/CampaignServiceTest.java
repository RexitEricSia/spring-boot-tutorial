package com.rexit.tutorial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rexit.tutorial.enums.Error;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.Campaign;
import com.rexit.tutorial.repository.CampaignRepository;

class CampaignServiceTest {

    private CampaignRepository campaignRepository;
    private CampaignService campaignService;

    @BeforeEach
    void setUp() {
        campaignRepository = mock(CampaignRepository.class);
        campaignService = new CampaignService(campaignRepository);
    }

    @Test
    void testGetAllCampaigns_success() {
        List<Campaign> campaigns = List.of(new Campaign(), new Campaign());
        when(campaignRepository.findAll()).thenReturn(campaigns);

        List<Campaign> result = campaignService.getAllCampaigns();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllCampaigns_failure() {
        when(campaignRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(BusinessException.class, () -> campaignService.getAllCampaigns());
    }

    @Test
    void testGetCampaignById_success() {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        Campaign result = campaignService.getCampaignById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetCampaignById_notFound() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> campaignService.getCampaignById(1L));
        assertEquals(Error.CAMPAIGN_ID_NOT_FOUND.toString(), exception.getBusinessStatusCode());
    }

    @Test
    void testCreateCampaign_success() {
        Campaign newCampaign = new Campaign();
        newCampaign.setCampaignCode("ABC123");

        when(campaignRepository.existsByCampaignCode("ABC123")).thenReturn(false);
        when(campaignRepository.save(newCampaign)).thenReturn(newCampaign);

        Campaign result = campaignService.createCampaign(newCampaign);
        assertEquals("ABC123", result.getCampaignCode());
    }

    @Test
    void testCreateCampaign_duplicateCode() {
        Campaign newCampaign = new Campaign();
        newCampaign.setCampaignCode("ABC123");

        when(campaignRepository.existsByCampaignCode("ABC123")).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> campaignService.createCampaign(newCampaign));
        assertEquals(Error.CAMPAIGN_DUPLICATE_CODE.toString(), exception.getBusinessStatusCode());
    }

    @Test
    void testUpdateCampaign_success() {
        Campaign updated = new Campaign();
        updated.setId(1L);

        when(campaignRepository.existsById(1L)).thenReturn(true);
        when(campaignRepository.save(updated)).thenReturn(updated);

        Campaign result = campaignService.updateCampaign(1L, updated);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateCampaign_idMismatch() {
        Campaign updated = new Campaign();
        updated.setId(2L);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> campaignService.updateCampaign(1L, updated));
        assertEquals(Error.CAMPAIGN_ID_MISMATCH.toString(), exception.getBusinessStatusCode());
    }

    @Test
    void testUpdateCampaign_idNotFound() {
        Campaign updated = new Campaign();
        updated.setId(1L);

        when(campaignRepository.existsById(1L)).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> campaignService.updateCampaign(1L, updated));
        assertEquals(Error.CAMPAIGN_ID_NOT_FOUND_FOR_UPDATE.toString(), exception.getBusinessStatusCode());
    }

    @Test
    void testDeleteCampaign_success() {
        when(campaignRepository.existsById(1L)).thenReturn(true);
        doNothing().when(campaignRepository).deleteById(1L);

        assertTrue(campaignService.deleteCampaign(1L));
    }

    @Test
    void testDeleteCampaign_notFound() {
        when(campaignRepository.existsById(1L)).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class, () -> campaignService.deleteCampaign(1L));
        assertEquals(Error.CAMPAIGN_ID_NOT_FOUND_FOR_DELETE.toString(), exception.getBusinessStatusCode());
    }
}