package com.abhimanyu.apiratelimiter.annotation;

import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import com.abhimanyu.apiratelimiter.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@EnableAspectJAutoProxy
public class RateLimitedAdvice {

    private static  final Logger LOGGER = LoggerFactory.getLogger(RateLimitedAdvice.class);

    @Autowired
    UserService userService;


    /**
     * All public methods that are annotated with RateLimited.
     */
    //@Pointcut("@annotation(RateLimited)")
    @Pointcut("execution(@com.abhimanyu.apiratelimiter.annotation.RateLimited * *(..))")
    public void rateLimitedAnnotatedMethods() {
    }

    /**
     * All public methods of a class annotated with RateLimited.
     */
    //@Pointcut("within(@com.abhimanyu.apiratelimiter.annotation.RateLimited *)")
    @Pointcut("execution(* (@com.abhimanyu.apiratelimiter.annotation.RateLimited *).*(..))")
    public void rateLimitedAnnotatedMethodsInAClass() {
    }

    /**
     * Pointcut expression that joins the first two with OR
     */
    @Pointcut("rateLimitedAnnotatedMethods() || rateLimitedAnnotatedMethodsInAClass()")
    public void rateLimitPointCut() {
    }

    @Around("rateLimitPointCut()")
    public void rateLimitedAccess(ProceedingJoinPoint pjp) throws Throwable {

        String currentUser = userService.getCurrentUserName();
        LOGGER.info("Checking API access quota for the user {}",currentUser);

        try {

            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();

            RateLimited rateLimited = method.getAnnotation(RateLimited.class);

            TimeUnit timeUnit = rateLimited.timeUnit();
            int apiAccessCountAllowed = rateLimited.accessCountPerTimeUnit();

            LOGGER.info("Time Unit = {}",TimeUnit.SECONDS.name());
            LOGGER.info("apiAccessCountAllowed = {}",apiAccessCountAllowed);
            boolean isApiAccessAllowed = userService.checkApiAccessLimit(currentUser, apiAccessCountAllowed);
            if (isApiAccessAllowed) {
                LOGGER.info("Access allowed for the user {}",currentUser);
                pjp.proceed();
            }
        } catch (ApiAccessCountExhausted e) {
            throw e;
        }
    }
}
