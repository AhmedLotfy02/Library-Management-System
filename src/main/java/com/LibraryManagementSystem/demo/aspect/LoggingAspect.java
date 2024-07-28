package com.LibraryManagementSystem.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.LibraryManagementSystem.demo.service.*.*(..))")
    public void serviceMethods() {}
    @Pointcut("execution(* com.LibraryManagementSystem.demo.controller.*.*(..))")

    public void controllerMethods() {}

    @Around("serviceMethods() || controllerMethods()")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable ex) {
            logger.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), ex.getCause() != null ? ex.getCause() : "NULL");
            throw ex;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("{} executed in {} ms", joinPoint.getSignature(), elapsedTime);
        return proceed;
    }

    @Before("serviceMethods() || controllerMethods()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Executing: {}.{}() with arguments[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(value = "serviceMethods() || controllerMethods()", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Method {}.{}() returned: {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
    }
}