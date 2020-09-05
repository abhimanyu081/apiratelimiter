package com.abhimanyu.apiratelimiter.service;

import com.abhimanyu.apiratelimiter.entity.TableUser;
import com.abhimanyu.apiratelimiter.util.RateLimitTimeUnit;

public interface RateLimitingService {

    /***
     *
     * @param timeUnit
     * @param apiLimitCountPerTimeUnit
     * @param tableUser
     * @param methodName
     * @param apiUrl
     * @return
     */
    public boolean isApiAccessAllowed(RateLimitTimeUnit timeUnit, int apiLimitCountPerTimeUnit, TableUser tableUser, String methodName, String apiUrl);

}
