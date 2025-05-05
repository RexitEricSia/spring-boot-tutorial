package com.rexit.tutorial.exception;

import com.rexit.tutorial.enums.Error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String businessStatusCode;

    public BusinessException(Error error) {
        super(error.getMessage());
        this.businessStatusCode = error.getCode();
    }

    public String getFormattedMessage() {
        return String.format("Status Code: %s | Business Error: %s" , businessStatusCode, super.getMessage() );
    }
}