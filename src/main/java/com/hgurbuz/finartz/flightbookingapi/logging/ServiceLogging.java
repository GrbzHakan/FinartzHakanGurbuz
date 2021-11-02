package com.hgurbuz.finartz.flightbookingapi.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class ServiceLogging {
    @Pointcut("execution(* com.hgurbuz.finartz.flightbookingapi.service.*.*(..))")
    private void forServiceMethods() {
    }

    @AfterReturning(pointcut = "forServiceMethods()", returning = "result")
    public void log4(JoinPoint joinPoint, Object result) {
        var method = joinPoint.getSignature().getName();
        log.info(method + " is executed.");
        if (Objects.nonNull(result)) {
            log.info("Result:{}", result.toString());
        }
    }
}
