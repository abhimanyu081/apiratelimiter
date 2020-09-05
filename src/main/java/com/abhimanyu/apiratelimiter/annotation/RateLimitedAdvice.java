package com.abhimanyu.apiratelimiter.annotation;

import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.exception.ApiAccessCountExhausted;
import com.abhimanyu.apiratelimiter.service.RateLimitingService;
import com.abhimanyu.apiratelimiter.service.UserService;
import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@EnableAspectJAutoProxy
public class RateLimitedAdvice {

    private static  final Logger LOGGER = LoggerFactory.getLogger(RateLimitedAdvice.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RateLimitingService rateLimitingService;


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
    @Pointcut("within(@com.abhimanyu.apiratelimiter.annotation.RateLimited *)")
    //@Pointcut("execution(* (@com.abhimanyu.apiratelimiter.annotation.RateLimited *).*(..))")
    public void rateLimitedAnnotatedMethodsInAClass() {
    }

    /**
     * Pointcut expression that joins the first two with OR
     */
    @Pointcut("rateLimitedAnnotatedMethods() || rateLimitedAnnotatedMethodsInAClass()")
    public void rateLimitPointCut() {
    }

    /**
     * controller layer
     */
    @Pointcut("within(com.abhimanyu.apiratelimiter.controller..*)")
    public void inWebLayer() {}

    @Around("rateLimitPointCut()")
    public void rateLimitedAccess(ProceedingJoinPoint pjp) throws Throwable {
        TableUser tableUser = userService.getLoggedInUser();
        LOGGER.info("Checking API access quota for the user {}",tableUser.getName());

        try {

            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();

            RateLimited rateLimited = method.getAnnotation(RateLimited.class);


            if (rateLimited == null) {
                rateLimited = pjp.getTarget().getClass()
                        .getAnnotation(RateLimited.class);
            }

            RateLimitTimeUnit timeUnit = rateLimited.timeUnit();
            int apiAccessCountAllowed = rateLimited.accessCountPerTimeUnit();

            LOGGER.info("Time Unit = {}",TimeUnit.SECONDS.name());
            LOGGER.info("apiAccessCountAllowed = {}",apiAccessCountAllowed);

            String apiUrl=null;//TODO

            boolean isApiAccessAllowed = rateLimitingService.isApiAccessAllowed(timeUnit,
                    apiAccessCountAllowed, tableUser, method.getName(), apiUrl);
            if (isApiAccessAllowed) {
                LOGGER.info("Access allowed for the user {}",tableUser.getName());
                pjp.proceed();
            }
        } catch (ApiAccessCountExhausted e) {
            throw e;
        }
    }
}
