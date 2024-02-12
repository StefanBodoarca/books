package com.ro.aspects.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
@Order(1)
public class SecurityAspect {
    private final Logger logger = Logger.getLogger(SecurityAspect.class.getName());

    @Around("@annotation(com.ro.annotations.ToLog))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {


        logger.info("Security Aspect: Calling the intercepted method");


        /*
          The proceed() method here delegates further in the aspect execution chain.
          It can either call the next aspect or the intercepted method.
         */
        Object returnedValue = joinPoint.proceed();

        logger.info("Security Aspect: Method executed and returned " + returnedValue);
        return returnedValue;
    }
}
