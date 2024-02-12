package com.ro.aspects.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Component
@Aspect
@Order(2)
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    @Around("@annotation(com.ro.annotations.ToLog))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {


        logger.info("Logging Ascpect: Calling the intercepted method");


        /*
          The proceed() method here delegates further in the aspect execution chain.
          It can either call the next aspect or the intercepted method.
         */
        Object returnedValue = joinPoint.proceed();

        logger.info("Logging Aspect: Method executed and returned " + returnedValue);
        return returnedValue;
    }
}
