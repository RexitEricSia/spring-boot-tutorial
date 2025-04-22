package com.rexit.tutorial.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rexit.tutorial.controller.CampaignController;
import com.rexit.tutorial.exception.BusinessException;


//@ControllerAdvice(basePackages = "com.example.controller")
//@ControllerAdvice(annotations = RestController.class)

@RestControllerAdvice(assignableTypes = CampaignController.class)
public class CampaignExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
