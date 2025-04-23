package com.rexit.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rexit.tutorial.model.Campaign;
import com.rexit.tutorial.service.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(CampaignController.class)
class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CampaignService campaignService;

    private Campaign campaign1;
    private Campaign campaign2;

    @BeforeEach
    void setUp() {
        campaign1 = Campaign.builder()
                .campaignCode("CAMP123")
                .name("Summer Sale")
                .description("A summer campaign with amazing discounts")
                .organiserEmail("organizer@example.com")
                .age(20)
                .discountPercentage(50)
                .startDate(LocalDate.of(2025, 6, 1)) // Start date should be in the future
                .endDate(LocalDate.of(2025, 8, 31)) // End date should be in the past
                .hallRentalPrice(new BigDecimal("1500.00"))
                .build();

        campaign2 = Campaign.builder()
                .campaignCode("CAMP456")
                .name("Black Friday Deals")
                .description("Huge discounts for Black Friday")
                .organiserEmail("deals@example.com")
                .age(18)
                .discountPercentage(70)
                .startDate(LocalDate.of(2025, 11, 20)) // Start date should be in the future
                .endDate(LocalDate.of(2025, 11, 27)) // End date should be in the past
                .hallRentalPrice(new BigDecimal("2500.00"))
                .build();
    }

    @Test
    void shouldReturnAllCampaigns() throws Exception {
        List<Campaign> campaigns = Arrays.asList(campaign1, campaign2);
        when(campaignService.getAllCampaigns()).thenReturn(campaigns);

        mockMvc.perform(get("/campaign"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnCampaignById() throws Exception {
        when(campaignService.getCampaignById(1L)).thenReturn(campaign1);

        mockMvc.perform(get("/campaign/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Summer Sale"));
    }

    @Test
    void shouldCreateCampaign() throws Exception {
        when(campaignService.createCampaign(any(Campaign.class))).thenReturn(campaign1);

        mockMvc.perform(post("/campaign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campaign1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Summer Sale"));
    }

    @Test
    void shouldUpdateCampaign() throws Exception {
        Campaign updatedCampaign = Campaign.builder()
                .id(1L)
                .campaignCode("CAMP123")
                .name("Updated")
                .description("A summer campaign with amazing discounts")
                .organiserEmail("organizer@example.com")
                .age(20)
                .discountPercentage(50)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2025, 8, 31))
                .hallRentalPrice(new BigDecimal("1500.00"))
                .build();
        when(campaignService.updateCampaign(eq(1L), any(Campaign.class))).thenReturn(updatedCampaign);

        mockMvc.perform(put("/campaign/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCampaign)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void shouldDeleteCampaign() throws Exception {
        when(campaignService.deleteCampaign(1L)).thenReturn(true);

        mockMvc.perform(delete("/campaign/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}