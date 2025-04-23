package com.rexit.tutorial.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "campaign")
@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
@Builder // Provides a builder pattern for constructing Campaign objects
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Only alphanumeric characters are allowed")
    private String campaignCode;

    @NonNull
    @NotEmpty(message = "Campaign name cannot be empty")
    @Size(min = 3, max = 100, message = "Campaign name must be between 3 and 100 characters")
    private String name;

    @NonNull
    @NotBlank(message = "Campaign description cannot be blank or space only")
    @Size(min = 10, max = 500, message = "Campaign description must be between 10 and 500 characters")
    private String description;

    @Email(message = "Invalid email format")
    private String organiserEmail;

    @Min(value = 18, message = "Campaign should be for people 18 years or older")
    private int age;

    @Max(value = 100, message = "Campaign percentage cannot exceed 100")
    private int discountPercentage;

    @FutureOrPresent(message = "Campaign start date must be in the future")
    private LocalDate startDate;

    @Future(message = "Campaign end date must be in the past")
    private LocalDate endDate;

    // @PastOrPresent(message = "Campaign start date cannot be in the future")
    // private LocalDate startDate;

    // @FutureOrPresent(message = "Campaign end date must be in the future or
    // present")
    // private LocalDate endDate;

    @Digits(integer = 5, fraction = 2, message = "Price must be a valid number with up to 2 decimal places")
    private BigDecimal hallRentalPrice;

    // Lombok will automatically generate the following:
    // - Getters for all fields
    // - Setters for all fields
    // - `toString()`, `equals()`, `hashCode()` methods based on the fields
    // - No-argument constructor
    // - All-argument constructor
    // - A builder pattern for easy object creation

    private LocalDateTime createdDateTime;
    private LocalDateTime lastUpdateDateTime;

    // Method to set createdDateTime before persisting
    @PrePersist
    public void onCreate() {
        this.createdDateTime = LocalDateTime.now(); // Set the creation timestamp
        this.lastUpdateDateTime = LocalDateTime.now(); // Set the update timestamp to created time
    }

    // Method to set lastUpdateDateTime before updating
    @PreUpdate
    public void onUpdate() {
        this.lastUpdateDateTime = LocalDateTime.now(); // Set the update timestamp on update
    }

    @PostPersist
    public void afterCreate() {
        // Actions after persist (e.g., sending notifications, logging)
    }

    @PostUpdate
    public void afterUpdate() {
        // Actions after update (e.g., sending notifications, logging)
    }

    @PreRemove
    public void beforeRemove() {
        // Actions before remove (e.g., clean up related data)
    }

    @PostRemove
    public void afterRemove() {
        // Actions after remove (e.g., clean up resources, logging)
    }

    @PostLoad
    public void afterLoad() {
        // Actions after load (e.g., setting default values, caching)
    }
}
