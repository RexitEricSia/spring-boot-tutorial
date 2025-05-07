package com.rexit.tutorial.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {

    //Campaign
    CAMPAIGN_FETCH_ERROR("CAMPAIGN_FETCH_ERROR", "Error fetching campaigns"),
    CAMPAIGN_FETCH_BY_ID_ERROR("CAMPAIGN_FETCH_BY_ID_ERROR", "Error fetching campaign by ID"),
    CAMPAIGN_ID_NOT_FOUND("CAMPAIGN_ID_NOT_FOUND", "Campaign ID not found"),
    CAMPAIGN_DUPLICATE_CODE("CAMPAIGN_DUPLICATE_CODE", "There is existing campaign with the same code"),
    CAMPAIGN_CREATE_ERROR("CAMPAIGN_CREATE_ERROR", "Error creating campaign"),
    CAMPAIGN_ID_MISMATCH("CAMPAIGN_ID_MISMATCH", "Campaign id and path id do not match"),
    CAMPAIGN_ID_NOT_FOUND_FOR_UPDATE("CAMPAIGN_ID_NOT_FOUND_FOR_UPDATE", "Campaign ID not found for update"),
    CAMPAIGN_UPDATE_ERROR("CAMPAIGN_UPDATE_ERROR", "Error updating campaign"),
    CAMPAIGN_ID_NOT_FOUND_FOR_DELETE("CAMPAIGN_ID_NOT_FOUND_FOR_DELETE", "Campaign ID not found for delete"),
    CAMPAIGN_DELETE_ERROR("CAMPAIGN_DELETE_ERROR", "Error deleting campaign"),
    CAMPAIGN_OPTIMISTIC_LOCK("CAMPAIGN_OPTIMISTIC_LOCK", "Campaign has been modified by another user. Please try again."),
    CAMPAIGN_LOCK_TIMEOUT("CAMPAIGN_LOCK_TIMEOUT", "Campaign is being modified by another user. Please try again later."),

    //User
    USER_FETCH_BY_ID_ERROR("USER_FETCH_BY_ID_ERROR", "Error fetching user by ID"),
    USER_ID_NOT_FOUND("USER_ID_NOT_FOUND", "USER ID not found"),
    USER_LOCK_TIMEOUT("USER_LOCK_TIMEOUT", "User is being modified by another user. Please try again later."),

    //Authentication
    AUTHENTICATION_LOGIN_FAILED("AUTHENTICATION_LOGIN_FAILED", "User ID or Password Incorrect.");

    private final String code;
    private final String message;
}
