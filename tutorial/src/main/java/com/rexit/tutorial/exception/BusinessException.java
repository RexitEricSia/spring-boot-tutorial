package com.rexit.tutorial.exception;

import com.rexit.tutorial.enums.Error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String message;
    private final String businessStatusCode;

    public BusinessException(Error campaignError) {
        this.message = campaignError.getMessage();
        this.businessStatusCode = campaignError.getCode();
    }

    @Override
    public String getMessage() {
        return String.format("Status Code: %s | Business Error: %s" , businessStatusCode, message );
    }
}