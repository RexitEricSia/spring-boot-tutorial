package com.rexit.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class CampaignPatchDTO {

    private String name;
    private String description;
    private String organiserEmail;
    private Integer age;
    private Integer discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal hallRentalPrice;
}
