package com.rexit.tutorial.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.rexit.tutorial.exception.BusinessException;

@Aspect
@Component
@Profile({ "dev", "uat" })
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.rexit.tutorial.controller.*(..)) || execution(* com.rexit.tutorial.service.*(..))")
    public void controllerAndServiceMethods() {
    }

    @Before("controllerAndServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering: {} with args: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "controllerAndServiceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting: {} with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "controllerAndServiceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        if (error instanceof BusinessException) {
            BusinessException businessError = (BusinessException) error;
            logger.error("Exception in: {} with message: {}", joinPoint.getSignature(),
                    businessError.getFormattedMessage());
        } else {
            logger.error("Exception in: {} with message: {}", joinPoint.getSignature(), error.getMessage());
        }
    }
}
