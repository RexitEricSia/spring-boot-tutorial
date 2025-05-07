package com.rexit.tutorial.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CampaignPatchDTO {

    @Size(min = 3, max = 100, message = "Campaign name must be between 3 and 100 characters")
    private String name;

    @Size(min = 10, max = 500, message = "Campaign description must be between 10 and 500 characters")
    private String description;

    @Email(message = "Invalid email format")
    private String organiserEmail;

    @Min(value = 18, message = "Campaign should be for people 18 years or older")
    private int age;

    @Max(value = 100, message = "Campaign percentage cannot exceed 100")
    private int discountPercentage;

    @FutureOrPresent(message = "Campaign start date must be in the future or present")
    private LocalDate startDate;

    @Future(message = "Campaign end date must be in the future")
    private LocalDate endDate;

    @Digits(integer = 5, fraction = 2, message = "Price must be a valid number with up to 2 decimal places")
    private BigDecimal hallRentalPrice;
}
