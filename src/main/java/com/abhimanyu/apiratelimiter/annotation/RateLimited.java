package com.abhimanyu.apiratelimiter.annotation;

import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface RateLimited {
    RateLimitTimeUnit timeUnit();
    int accessCountPerTimeUnit();
}
